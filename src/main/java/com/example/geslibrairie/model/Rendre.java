package com.example.geslibrairie.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rendre")
public class Rendre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idrendu;

    @ManyToOne
    @JoinColumn(name = "idpers")
    private Membre membre;

    @ManyToOne
    @JoinColumn(name = "idlivre")
    private Livre livre;

    private LocalDateTime daterendu;

    @OneToOne
    @JoinColumn(name = "idpret")
    private Pret pret;

    // Getters and Setters

    public Long getIdrendu() {
        return idrendu;
    }

    public void setIdrendu(Long idrendu) {
        this.idrendu = idrendu;
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

    public LocalDateTime getDaterendu() {
        return daterendu;
    }

    public void setDaterendu(LocalDateTime daterendu) {
        this.daterendu = daterendu;
    }
    public Pret getPret() {
        return pret;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }
}
