package com.example.geslibrairie.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "livre")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idlivre;

    private String designation;
    private int exemplaire;

    @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pret> prets = new HashSet<>();

    @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rendre> rendus = new HashSet<>();

    // Getters and Setters

    public Long getIdlivre() {
        return idlivre;
    }

    public void setIdlivre(Long idlivre) {
        this.idlivre = idlivre;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getExemplaire() {
        return exemplaire;
    }

    public void setExemplaire(int exemplaire) {
        this.exemplaire = exemplaire;
    }

    public Set<Pret> getPrets() {
        return prets;
    }

    public void setPrets(Set<Pret> prets) {
        this.prets = prets;
    }

    public Set<Rendre> getRendus() {
        return rendus;
    }

    public void setRendus(Set<Rendre> rendus) {
        this.rendus = rendus;
    }
}
