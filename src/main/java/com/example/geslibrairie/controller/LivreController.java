package com.example.geslibrairie.controller;

import com.example.geslibrairie.model.Livre;
import com.example.geslibrairie.model.Pret;
import com.example.geslibrairie.model.Rendre;
import com.example.geslibrairie.repository.LivreRepository;
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
@RequestMapping("/livre")
public class LivreController {

    @Autowired
    private LivreRepository livreRepository;

    @GetMapping("/gestion")
    public String showGestion(@RequestParam(value = "query", required = false) String query, Model model) {
        List<Livre> livres;
        if (query != null && !query.trim().isEmpty()) {
            String searchQuery = "%" + query.trim().toLowerCase() + "%";
            livres = livreRepository.findAll().stream()
                    .filter(livre ->
                            String.valueOf(livre.getIdlivre()).contains(query.toLowerCase()) ||
                                    livre.getDesignation().toLowerCase().contains(query.toLowerCase()) ||
                                    String.valueOf(livre.getExemplaire()).contains(query.toLowerCase()))
                    .sorted(Comparator.comparing(Livre::getIdlivre))
                    .collect(Collectors.toList());
        } else {
            livres = livreRepository.findAll().stream()
                    .sorted(Comparator.comparing(Livre::getIdlivre))
                    .collect(Collectors.toList());
        }

        model.addAttribute("header", "Gestion des Livres");
        model.addAttribute("livres", livres);
        model.addAttribute("content", "/WEB-INF/jsp/livre/gestion.jsp");
        return "layout/main";
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("header", "Ajouter un Livre");
        model.addAttribute("livre", new Livre());
        model.addAttribute("action", "add");
        model.addAttribute("content", "/WEB-INF/jsp/livre/form.jsp");
        return "layout/main";
    }

    @PostMapping("/add")
    public String addLivre(@ModelAttribute Livre livre) {
        Long newLivreId = generateLivreId();
        livre.setIdlivre(newLivreId);

        livreRepository.save(livre);
        return "redirect:/livre/gestion";
    }

    @GetMapping("/edit/{idlivre}")
    public String showEditForm(@PathVariable Long idlivre, Model model) {
        Optional<Livre> livre = livreRepository.findById(idlivre);
        if (livre.isPresent()) {
            model.addAttribute("header", "Modifier un Livre");
            model.addAttribute("livre", livre.get());
            model.addAttribute("action", "edit");
            model.addAttribute("content", "/WEB-INF/jsp/livre/form.jsp");
            return "layout/main";
        } else {
            return "redirect:/livre/gestion";
        }
    }

    @PostMapping("/edit")
    public String editLivre(@ModelAttribute Livre livre) {
        Optional<Livre> existingLivreOpt = livreRepository.findById(livre.getIdlivre());
        if (existingLivreOpt.isPresent()) {
            Livre existingLivre = existingLivreOpt.get();
            existingLivre.setDesignation(livre.getDesignation());
            existingLivre.setExemplaire(livre.getExemplaire());
            // Mettez à jour les collections existantes
            updatePretCollection(existingLivre.getPrets(), livre.getPrets());
            updateRenduCollection(existingLivre.getRendus(), livre.getRendus());

            livreRepository.save(existingLivre);
        }
        return "redirect:/livre/gestion";
    }

    private void updatePretCollection(Set<Pret> existingPrets, Set<Pret> newPrets) {
        existingPrets.clear();
        existingPrets.addAll(newPrets);
    }

    private void updateRenduCollection(Set<Rendre> existingRendus, Set<Rendre> newRendus) {
        existingRendus.clear();
        existingRendus.addAll(newRendus);
    }

    @GetMapping("/delete/{idlivre}")
    public String deleteLivre(@PathVariable Long idlivre) {
        livreRepository.deleteById(idlivre);
        return "redirect:/livre/gestion";
    }

    private Long generateLivreId() {
        Optional<Livre> lastLivre = livreRepository.findAll().stream()
                .max(Comparator.comparing(Livre::getIdlivre));

        return lastLivre.map(livre -> livre.getIdlivre() + 1).orElse(1L);
    }
}
