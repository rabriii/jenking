package com.example.geslibrairie.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pret")
public class Pret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpret;

    @ManyToOne
    @JoinColumn(name = "idpers")
    private Membre membre;

    @ManyToOne
    @JoinColumn(name = "idlivre")
    private Livre livre;

    private LocalDateTime datepret;
    private LocalDateTime daterendu;

    @OneToOne(mappedBy = "pret", cascade = CascadeType.ALL)
    private Rendre rendre;

    // Getters and Setters

    public Long getIdpret() {
        return idpret;
    }

    public void setIdpret(Long idpret) {
        this.idpret = idpret;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public LocalDateTime getDatepret() {
        return datepret;
    }

    public void setDatepret(LocalDateTime datepret) {
        this.datepret = datepret;
    }

    public LocalDateTime getDaterendu() {
        return daterendu;
    }

    public void setDaterendu(LocalDateTime daterendu) {
        this.daterendu = daterendu;
    }
    public Rendre getRendre() {
        return rendre;
    }

    public void setRendre(Rendre rendre) {
        this.rendre = rendre;
    }
}
