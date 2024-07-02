package com.example.geslibrairie.controller;

import com.example.geslibrairie.model.Rendre;
import com.example.geslibrairie.model.Pret;
import com.example.geslibrairie.model.Livre;
import com.example.geslibrairie.model.RendreView;
import com.example.geslibrairie.model.Membre;
import com.example.geslibrairie.repository.RendreRepository;
import com.example.geslibrairie.repository.PretRepository;
import com.example.geslibrairie.repository.LivreRepository;
import com.example.geslibrairie.repository.MembreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/rendre")
public class RendreController {

    @Autowired
    private RendreRepository rendreRepository;

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private MembreRepository membreRepository;

    @Autowired
    private PretRepository pretRepository;

    @GetMapping("/gestionRendu")
    public String showGestion(Model model) {
        model.addAttribute("header", "Gestion des Rendus");
        List<Rendre> rendus = rendreRepository.findAll();
        List<RendreView> rendusView = rendus.stream().map(this::convertToRendreView).collect(Collectors.toList());
        model.addAttribute("rendus", rendusView);
        model.addAttribute("content", "/WEB-INF/jsp/rendre/gestionRendu.jsp");
        return "layout/main";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("header", "Ajouter un Rendu");
        model.addAttribute("rendre", new Rendre());
        model.addAttribute("prets", pretRepository.findAll());
        model.addAttribute("action", "add");
        model.addAttribute("content", "/WEB-INF/jsp/rendre/form.jsp");
        return "layout/main";
    }

    @PostMapping("/add")
    public String addRendre(@ModelAttribute Rendre rendre, @RequestParam Long idpret, Model model) {
        Optional<Pret> pretOpt = pretRepository.findById(idpret);

        if (pretOpt.isPresent()) {
            Pret pret = pretOpt.get();

            // Vérifiez si ce prêt a déjà été rendu
            if (rendreRepository.existsByPretId(idpret)) {
                model.addAttribute("message", "Ce prêt a déjà été rendu.");
                return showAddForm(model);
            }

            Livre livre = pret.getLivre();
            Membre membre = pret.getMembre();

            rendre.setIdrendu(generateRendreId());
            rendre.setPret(pret);
            rendre.setLivre(livre);
            rendre.setMembre(membre);
            rendre.setDaterendu(LocalDateTime.now());

            livre.setExemplaire(livre.getExemplaire() + 1);
            livreRepository.save(livre);
            rendreRepository.save(rendre);

            pret.setDaterendu(LocalDateTime.now());
            pretRepository.save(pret);

            return "redirect:/rendre/gestionRendu";
        } else {
            return "redirect:/rendre/gestionRendu?message=Prêt non trouvé.";
        }
    }

    @GetMapping("/edit/{idrendu}")
    public String showEditForm(@PathVariable Long idrendu, Model model) {
        Optional<Rendre> rendre = rendreRepository.findById(idrendu);
        if (rendre.isPresent()) {
            model.addAttribute("header", "Modifier un Rendu");
            model.addAttribute("rendre", rendre.get());
            model.addAttribute("prets", pretRepository.findAll());
            model.addAttribute("action", "edit");
            model.addAttribute("content", "/WEB-INF/jsp/rendre/form.jsp");
            return "layout/main";
        } else {
            return "redirect:/rendre/gestionRendu";
        }
    }

    @PostMapping("/edit")
    public String editRendre(@ModelAttribute Rendre rendre, @RequestParam Long idpret) {
        Optional<Rendre> existingRendreOpt = rendreRepository.findById(rendre.getIdrendu());
        Optional<Pret> pretOpt = pretRepository.findById(idpret);

        if (existingRendreOpt.isPresent() && pretOpt.isPresent()) {
            Rendre existingRendre = existingRendreOpt.get();
            Pret pret = pretOpt.get();

            Livre livre = pret.getLivre();
            Membre membre = pret.getMembre();

            existingRendre.setPret(pret);
            existingRendre.setLivre(livre);
            existingRendre.setMembre(membre);

            livre.setExemplaire(livre.getExemplaire() + 1);
            livreRepository.save(livre);
            rendreRepository.save(existingRendre);

            return "redirect:/rendre/gestionRendu";
        } else {
            return "redirect:/rendre/gestionRendu?message=Prêt ou rendu non trouvé.";
        }
    }

    @GetMapping("/delete/{idrendu}")
    public String deleteRendre(@PathVariable Long idrendu) {
        Optional<Rendre> rendreOpt = rendreRepository.findById(idrendu);
        if (rendreOpt.isPresent()) {
            Rendre rendre = rendreOpt.get();
            Livre livre = rendre.getLivre();
            livre.setExemplaire(livre.getExemplaire() - 1);
            livreRepository.save(livre);
            rendreRepository.deleteById(idrendu);
        }
        return "redirect:/rendre/gestionRendu";
    }

    private Long generateRendreId() {
        Optional<Rendre> lastRendre = rendreRepository.findAll().stream()
                .max(Comparator.comparing(Rendre::getIdrendu));

        return lastRendre.map(rendre -> rendre.getIdrendu() + 1).orElse(1L);
    }

    private RendreView convertToRendreView(Rendre rendre) {
        return new RendreView(
                rendre.getIdrendu(),
                rendre.getMembre(),
                rendre.getLivre(),
                rendre.getPret(),
                convertToDate(rendre.getDaterendu())
        );
    }

    private Date convertToDate(LocalDateTime dateToConvert) {
        if (dateToConvert == null) {
            return null;
        }
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }

}
