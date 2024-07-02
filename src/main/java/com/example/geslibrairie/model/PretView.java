package com.example.geslibrairie.model;

import java.util.Date;

public class PretView {
    private Long idpret;
    private Membre membre;
    private Livre livre;
    private Date datepret;
    private Date daterendu;

    public PretView(Long idpret, Membre membre, Livre livre, Date datepret, Date daterendu) {
        this.idpret = idpret;
        this.membre = membre;
        this.livre = livre;
        this.datepret = datepret;
        this.daterendu = daterendu;
    }

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

    public Date getDatepret() {
        return datepret;
    }

    public void setDatepret(Date datepret) {
        this.datepret = datepret;
    }

    public Date getDaterendu() {
        return daterendu;
    }

    public void setDaterendu(Date daterendu) {
        this.daterendu = daterendu;
    }
}
