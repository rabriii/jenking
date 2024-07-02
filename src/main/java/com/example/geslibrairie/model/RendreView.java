package com.example.geslibrairie.model;

import java.util.Date;

public class RendreView {
    private Long idrendu;
    private Membre membre;
    private Livre livre;
    private Pret pret;  // Ajout du champ Pret
    private Date daterendu;

    public RendreView(Long idrendu, Membre membre, Livre livre, Pret pret, Date daterendu) {
        this.idrendu = idrendu;
        this.membre = membre;
        this.livre = livre;
        this.pret = pret;  // Initialisation du champ Pret
        this.daterendu = daterendu;
    }

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

    public Pret getPret() {
        return pret;  // Getter pour Pret
    }

    public void setPret(Pret pret) {
        this.pret = pret;  // Setter pour Pret
    }

    public Date getDaterendu() {
        return daterendu;
    }

    public void setDaterendu(Date daterendu) {
        this.daterendu = daterendu;
    }
}
