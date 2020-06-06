package com.example.journal_de_bord.models;

import java.util.Date;

public class Espace {
    private String id;
    private String nom;
    private String idUser;
    private Date datecreation;

    public Espace() {
    }

    public Espace(String id, String nom,String idUser, Date dateCreation) {
        this.id = id;
        this.nom = nom;
        this.idUser = idUser;
        this.datecreation = dateCreation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(Date datecreation) {
        this.datecreation = datecreation;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
