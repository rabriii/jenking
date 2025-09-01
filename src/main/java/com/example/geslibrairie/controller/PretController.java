package com.example.geslibrairie.controller;

import com.example.geslibrairie.model.Pret;
import com.example.geslibrairie.model.PretView;
import com.example.geslibrairie.model.Livre;
import com.example.geslibrairie.model.Membre;
import com.example.geslibrairie.model.Rendre;
import com.example.geslibrairie.service.EmailService;
import com.example.geslibrairie.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.example.geslibrairie.repository.PretRepository;
import com.example.geslibrairie.repository.LivreRepository;
import com.example.geslibrairie.repository.MembreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Comparator;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pret")
public class PretController {

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private MembreRepository membreRepository;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/gestionPret")
    public String showGestion(Model model,
                              @RequestParam(name = "message", required = false) String message,
                              @RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                              @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        model.addAttribute("header", "Gestion des Prêts");

        List<Pret> prets;
        if (startDate != null && endDate != null) {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

            prets = pretRepository.findAll().stream()
                    .filter(pret -> pret.getDatepret().isAfter(startDateTime) && pret.getDatepret().isBefore(endDateTime))
                    .sorted(Comparator.comparing(Pret::getIdpret))
                    .collect(Collectors.toList());
        } else {
            prets = pretRepository.findAll().stream()
                    .sorted(Comparator.comparing(Pret::getIdpret))
                    .collect(Collectors.toList());
        }

        List<PretView> pretsView = prets.stream().map(this::convertToPretView).collect(Collectors.toList());

        model.addAttribute("prets", pretsView);
        model.addAttribute("content", "/WEB-INF/jsp/pret/gestionPret.jsp");
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "layout/main";
    }

    @GetMapping("/receipt/{id}")
    public ResponseEntity<byte[]> getPretReceipt(@PathVariable Long id) {
        Optional<Pret> pretOpt = pretRepository.findById(id);
        if (!pretOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Pret pret = pretOpt.get();
        byte[] pdfBytes = new byte[0];
        try {
            pdfBytes = pdfService.generatePretReceipt(pret);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "reçu_pret.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("header", "Ajouter un Pret");
        model.addAttribute("pret", new Pret());
        model.addAttribute("livres", livreRepository.findAll());
        model.addAttribute("membres", membreRepository.findAll());
        model.addAttribute("action", "add");
        model.addAttribute("content", "/WEB-INF/jsp/pret/form.jsp");
        return "layout/main";
    }

    @PostMapping("/add")
    public String addPret(@ModelAttribute Pret pret, @RequestParam Long idlivre, @RequestParam Long idpers, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datepret, Model model) {
        pret.setDatepret(datepret);

        LocalDateTime daterendu = datepret.plusDays(14);
        pret.setDaterendu(daterendu);

        Long newPretId = generatePretId();
        pret.setIdpret(newPretId);

        Optional<Membre> membreOpt = membreRepository.findById(idpers);
        if (membreOpt.isPresent()) {
            Membre membre = membreOpt.get();
            pret.setMembre(membre);
        } else {
            return "redirect:/pret/gestionPret?message=Membre non trouvé";
        }

        Optional<Livre> livreOpt = livreRepository.findById(idlivre);
        if (livreOpt.isPresent()) {
            Livre livre = livreOpt.get();
            if (livre.getExemplaire() > 0) {
                livre.setExemplaire(livre.getExemplaire() - 1);
                pret.setLivre(livre);
                livreRepository.save(livre);
            } else {
                return "redirect:/pret/gestionPret?message=Plus d'exemplaires disponibles";
            }
        } else {
            return "redirect:/pret/gestionPret?message=Livre non trouvé";
        }

        pretRepository.save(pret);
        return "redirect:/pret/gestionPret?message=Prêt ajouté avec succès";
    }


    @GetMapping("/edit/{idpret}")
    public String showEditForm(@PathVariable Long idpret, Model model) {
        Optional<Pret> pret = pretRepository.findById(idpret);
        if (pret.isPresent()) {
            model.addAttribute("header", "Modifier un Pret");
            model.addAttribute("pret", pret.get());
            model.addAttribute("livres", livreRepository.findAll());
            model.addAttribute("membres", membreRepository.findAll());
            model.addAttribute("action", "edit");
            model.addAttribute("content", "/WEB-INF/jsp/pret/form.jsp");
            return "layout/main";
        } else {
            return "redirect:/pret/gestionPret?message=Prêt non trouvé";
        }
    }

    @PostMapping("/edit")
    public String editPret(@ModelAttribute Pret pret, @RequestParam Long idlivre, @RequestParam Long idpers, Model model) {
        Optional<Pret> existingPretOpt = pretRepository.findById(pret.getIdpret());
        if (existingPretOpt.isPresent()) {
            Pret existingPret = existingPretOpt.get();
            Livre oldLivre = existingPret.getLivre();

            if (!oldLivre.getIdlivre().equals(idlivre)) {
                oldLivre.setExemplaire(oldLivre.getExemplaire() + 1);
                livreRepository.save(oldLivre);

                Optional<Livre> newLivreOpt = livreRepository.findById(idlivre);
                if (newLivreOpt.isPresent()) {
                    Livre newLivre = newLivreOpt.get();
                    if (newLivre.getExemplaire() > 0) {
                        newLivre.setExemplaire(newLivre.getExemplaire() - 1);
                        pret.setLivre(newLivre);
                        livreRepository.save(newLivre);
                    } else {
                        return "redirect:/pret/gestionPret?message=Plus d'exemplaires disponibles pour le nouveau livre";
                    }
                } else {
                    return "redirect:/pret/gestionPret?message=Nouveau livre non trouvé";
                }
            }

            Optional<Membre> membreOpt = membreRepository.findById(idpers);
            if (membreOpt.isPresent()) {
                Membre membre = membreOpt.get();
                pret.setMembre(membre);
            } else {
                return "redirect:/pret/gestionPret?message=Membre non trouvé";
            }

            if (!existingPret.getDatepret().equals(pret.getDatepret())) {
                pret.setDaterendu(pret.getDatepret().plusDays(14));
            } else {
                pret.setDaterendu(existingPret.getDaterendu());
            }

            pretRepository.save(pret);
            return "redirect:/pret/gestionPret?message=Prêt modifié avec succès";
        } else {
            return "redirect:/pret/gestionPret?message=Prêt non trouvé";
        }
    }


    @GetMapping("/delete/{idpret}")
    public String deletePret(@PathVariable Long idpret) {
        if (pretRepository.existsById(idpret)) {
            Pret pret = pretRepository.findById(idpret).get();
            Livre livre = pret.getLivre();
            livre.setExemplaire(livre.getExemplaire() + 1);
            livreRepository.save(livre);
            pretRepository.deleteById(idpret);
            return "redirect:/pret/gestionPret?message=Prêt supprimé avec succès";
        } else {
            return "redirect:/pret/gestionPret?message=Prêt non trouvé";
        }
    }

    private Long generatePretId() {
        Optional<Pret> lastPret = pretRepository.findAll().stream()
                .max(Comparator.comparing(Pret::getIdpret));

        return lastPret.map(pret -> pret.getIdpret() + 1).orElse(1L);
    }

    private PretView convertToPretView(Pret pret) {
        return new PretView(
                pret.getIdpret(),
                pret.getMembre(),
                pret.getLivre(),
                convertToDate(pret.getDatepret()),
                convertToDate(pret.getDaterendu())
        );
    }

    private Date convertToDate(LocalDateTime dateToConvert) {
        if (dateToConvert == null) {
            return null;
        }
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }

    @PostMapping("/sendReminder")
    @ResponseBody
    public ResponseEntity<String> sendReminderEmail(
            @RequestParam String email,
            @RequestParam String nomMembre,
            @RequestParam Long idPret) {

        Optional<Pret> pretOpt = pretRepository.findById(idPret);
        if (pretOpt.isPresent()) {
            Pret pret = pretOpt.get();
            long daysRemaining = daysBetween(LocalDateTime.now(), pret.getDaterendu());

            String subject = "Rappel: Retour du livre dans " + daysRemaining + " jour(s)";
            String message = "Cher " + nomMembre + ",\n\n" +
                    "Ceci est un rappel que vous devez retourner le livre \"" +
                    pret.getLivre().getDesignation() + "\" avant le " + pret.getDaterendu().toLocalDate() + ".\n\n" +
                    "Il vous reste " + daysRemaining + " jour(s) pour le retourner.\n\n" +
                    "Merci de le retourner à temps.\n\n" +
                    "Cordialement,\nHamaky";

            try {
                emailService.sendEmail(email, subject, message);
                return ResponseEntity.ok("Email de rappel envoyé avec succès !");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi de l'email de rappel : " + e.getMessage());
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/sendRetard")
    @ResponseBody
    public ResponseEntity<String> sendRetardEmail(
            @RequestParam String email,
            @RequestParam String nomMembre,
            @RequestParam Long idPret) {

        Optional<Pret> pretOpt = pretRepository.findById(idPret);
        if (pretOpt.isPresent()) {
            Pret pret = pretOpt.get();
            long daysOverdue = daysBetween(pret.getDaterendu(), LocalDateTime.now());

            String subject = "Rappel: Retard de retour du livre de " + daysOverdue + " jour(s)";
            String message = "Cher " + nomMembre + ",\n\n" +
                    "Vous avez un retard de " + daysOverdue + " jour(s) pour le retour du livre \"" +
                    pret.getLivre().getDesignation() + "\". La date de retour prévue était le " + pret.getDaterendu().toLocalDate() + ".\n\n" +
                    "Merci de retourner le livre dès que possible pour éviter toute pénalité.\n\n" +
                    "Cordialement,\nHamaky";

            try {
                emailService.sendEmail(email, subject, message);
                return ResponseEntity.ok("Email de retard envoyé avec succès !");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi de l'email de retard : " + e.getMessage());
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    private long daysBetween(LocalDateTime start, LocalDateTime end) {
        return Math.abs(start.toLocalDate().toEpochDay() - end.toLocalDate().toEpochDay());
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void checkOverduePrets() {
        List<Pret> overduePrets = pretRepository.findAll().stream()
                .filter(pret -> pret.getDaterendu().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @GetMapping("/retardataire")
    public String showOverduePrets(Model model) {
        model.addAttribute("header", "Prêts en Retard");

        List<Pret> overduePrets = pretRepository.findAll().stream()
                .filter(pret -> pret.getDaterendu().isBefore(LocalDateTime.now()))
                .sorted(Comparator.comparing(Pret::getDaterendu))
                .collect(Collectors.toList());

        List<PretView> overduePretsView = overduePrets.stream().map(this::convertToPretView).collect(Collectors.toList());

        model.addAttribute("prets", overduePretsView);
        model.addAttribute("content", "/WEB-INF/jsp/pret/retardataire.jsp");
        return "layout/main";
    }

}
