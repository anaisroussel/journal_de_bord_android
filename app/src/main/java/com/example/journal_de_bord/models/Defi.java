package com.example.journal_de_bord.models;

import androidx.annotation.Nullable;

import java.util.Date;

public class Defi {
    private String id;
    private String iduser;
    private String title;
    private String espace;
    @Nullable private String description;
    private boolean indicateurSwitch;
    private float indicateurStars;
    private String indicateurSpinner;
    private Date date;
    @Nullable private String nomIndicateurSwitch;
    @Nullable private String nomIndicateurStars;
    @Nullable private String nomIndicateurSpinner;

    public Defi() {}

    public Defi(String id, String iduser, String title, String espace, @Nullable String description, boolean indicateurSwitch, float indicateurStars, String indicateurSpinner, Date date, @Nullable String nomIndicateurSwitch, @Nullable String nomIndicateurStars, @Nullable String nomIndicateurSpinner) {
        this.id = id;
        this.iduser = iduser;
        this.title = title;
        this.espace = espace;
        this.description = description;
        this.indicateurSwitch = indicateurSwitch;
        this.indicateurStars = indicateurStars;
        this.indicateurSpinner = indicateurSpinner;
        this.date = date;
        this.nomIndicateurSwitch = nomIndicateurSwitch;
        this.nomIndicateurStars = nomIndicateurStars;
        this.nomIndicateurSpinner = nomIndicateurSpinner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIduser() {
        return iduser;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public boolean isIndicateurSwitch() {
        return indicateurSwitch;
    }

    public float getIndicateurStars() {
        return indicateurStars;
    }

    public String getIndicateurSpinner() {
        return indicateurSpinner;
    }

    @Nullable
    public String getNomIndicateurSwitch() {
        return nomIndicateurSwitch;
    }

    @Nullable
    public String getNomIndicateurStars() {
        return nomIndicateurStars;
    }

    @Nullable
    public String getNomIndicateurSpinner() {
        return nomIndicateurSpinner;
    }

    public String getEspace() {
        return espace;
    }

    public void setEspace(String espace) {
        this.espace = espace;
    }
}
