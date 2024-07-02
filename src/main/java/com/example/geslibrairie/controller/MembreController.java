package com.example.geslibrairie.controller;

import com.example.geslibrairie.model.Membre;
import com.example.geslibrairie.model.Rendre;
import com.example.geslibrairie.model.Pret;
import com.example.geslibrairie.repository.MembreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

@Controller
@RequestMapping("/membre")
public class MembreController {

    @Autowired
    private MembreRepository membreRepository;


    @GetMapping("/gestionMembre")
    public String showGestionMembre(@RequestParam(value = "query", required = false) String query, Model model) {
        List<Membre> membres;
        if (query != null && !query.trim().isEmpty()) {
            String searchQuery = "%" + query.trim().toLowerCase() + "%";
            membres = membreRepository.findAll().stream()
                    .filter(membre ->
                            String.valueOf(membre.getIdpers()).contains(query.toLowerCase()) ||
                                    membre.getNom().toLowerCase().contains(query.toLowerCase()) ||
                                    membre.getSexe().toLowerCase().contains(query.toLowerCase()) ||
                                    String.valueOf(membre.getAge()).contains(query.toLowerCase()) ||
                                    membre.getContact().toLowerCase().contains(query.toLowerCase()) ||
                                    membre.getEmail().toLowerCase().contains(query.toLowerCase()))
                    .sorted(Comparator.comparing(Membre::getIdpers))
                    .collect(Collectors.toList());
        } else {
            membres = membreRepository.findAll().stream()
                    .sorted(Comparator.comparing(Membre::getIdpers))
                    .collect(Collectors.toList());
        }

        model.addAttribute("header", "Gestion des Membres");
        model.addAttribute("membres", membres);
        model.addAttribute("content", "/WEB-INF/jsp/membre/gestionMembre.jsp");
        return "layout/main";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("header", "Ajouter un Membre");
        model.addAttribute("membre", new Membre());
        model.addAttribute("action", "add");
        model.addAttribute("content", "/WEB-INF/jsp/membre/form.jsp");
        return "layout/main";
    }

    @PostMapping("/add")
    public String addMembre(@ModelAttribute Membre membre) {
        Long newMembreId = generateMembreId();
        membre.setIdpers(newMembreId);

        membreRepository.save(membre);
        return "redirect:/membre/gestionMembre";
    }

    @GetMapping("/edit/{idpers}")
    public String showEditForm(@PathVariable Long idpers, Model model) {
        Optional<Membre> membre = membreRepository.findById(idpers);
        if (membre.isPresent()) {
            model.addAttribute("header", "Modifier un Membre");
            model.addAttribute("membre", membre.get());
            model.addAttribute("action", "edit");
            model.addAttribute("content", "/WEB-INF/jsp/membre/form.jsp");
            return "layout/main";
        } else {
            return "redirect:/membre/gestionMembre";
        }
    }

    @PostMapping("/edit")
    public String editMembre(@ModelAttribute Membre membre) {
        Optional<Membre> existingMembreOpt = membreRepository.findById(membre.getIdpers());
        if (existingMembreOpt.isPresent()) {
            Membre existingMembre = existingMembreOpt.get();
            existingMembre.setNom(membre.getNom());
            existingMembre.setSexe(membre.getSexe());
            existingMembre.setAge(membre.getAge());
            existingMembre.setContact(membre.getContact());
            existingMembre.setEmail(membre.getEmail());
            // Mettez à jour les collections existantes
            updatePretCollection(existingMembre.getPrets(), membre.getPrets());
            updateRenduCollection(existingMembre.getRendus(), membre.getRendus());

            membreRepository.save(existingMembre);
        }
        return "redirect:/membre/gestionMembre";
    }

    private void updatePretCollection(Set<Pret> existingPrets, Set<Pret> newPrets) {
        existingPrets.clear();
        existingPrets.addAll(newPrets);
    }

    private void updateRenduCollection(Set<Rendre> existingRendus, Set<Rendre> newRendus) {
        existingRendus.clear();
        existingRendus.addAll(newRendus);
    }


    @GetMapping("/delete/{idpers}")
    public String deleteMembre(@PathVariable Long idpers) {
        membreRepository.deleteById(idpers);
        return "redirect:/membre/gestionMembre";
    }

    private Long generateMembreId() {
        Optional<Membre> lastMembre = membreRepository.findAll().stream()
                .max(Comparator.comparing(Membre::getIdpers));

        return lastMembre.map(membre -> membre.getIdpers() + 1).orElse(1L);
    }
    @GetMapping("/search")
    public String searchMembre(@RequestParam("query") String query, Model model) {
        List<Membre> membres = membreRepository.findAll().stream()
                .filter(membre ->
                        membre.getIdpers().toString().contains(query) ||
                                membre.getNom().toLowerCase().contains(query.toLowerCase()) ||
                                membre.getSexe().toLowerCase().contains(query.toLowerCase()) ||
                                String.valueOf(membre.getAge()).contains(query) ||
                                membre.getContact().toLowerCase().contains(query.toLowerCase()) ||
                                membre.getEmail().toLowerCase().contains(query.toLowerCase())
                )
                .sorted(Comparator.comparing(Membre::getIdpers))
                .collect(Collectors.toList());

        model.addAttribute("header", "Gestion des Membres");
        model.addAttribute("membres", membres);
        model.addAttribute("content", "/WEB-INF/jsp/membre/gestionMembre.jsp");
        return "layout/main";
    }

}
