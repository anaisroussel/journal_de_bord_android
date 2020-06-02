package com.example.journal_de_bord.models;

import androidx.annotation.Nullable;

import java.util.Date;

public class Defi {
    private String id;
    private String iduser;
    @Nullable private String description;
    private boolean indicateur1;
    private boolean indicateur2;
    private boolean indicateur3;
    private Date date;
    @Nullable private String nomIndicateur1;
    @Nullable private String nomIndicateur2;
    @Nullable private String nomIndicateur3;

    public Defi(String id, String iduser, Date date, @Nullable String description, boolean indicateur1, boolean indicateur2, boolean indicateur3, @Nullable String nomIndicateur1, @Nullable String nomIndicateur2, @Nullable String nomIndicateur3) {
        this.id = id;
        this.iduser = iduser;
        this.date = date;
        this.description = description;
        this.indicateur1 = indicateur1;
        this.indicateur2 = indicateur2;
        this.indicateur3 = indicateur3;
        this.nomIndicateur1 = nomIndicateur1;
        this.nomIndicateur2 = nomIndicateur2;
        this.nomIndicateur3 = nomIndicateur3;
    }

    public String getId() {
        return id;
    }

    public String getIduser() {
        return iduser;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public boolean isIndicateur1() {
        return indicateur1;
    }

    public void setIndicateur1(boolean indicateur1) {
        this.indicateur1 = indicateur1;
    }

    public boolean isIndicateur2() {
        return indicateur2;
    }

    public void setIndicateur2(boolean indicateur2) {
        this.indicateur2 = indicateur2;
    }

    public boolean isIndicateur3() {
        return indicateur3;
    }

    public void setIndicateur3(boolean indicateur3) {
        this.indicateur3 = indicateur3;
    }

    @Nullable
    public String getNomIndicateur1() {
        return nomIndicateur1;
    }

    public void setNomIndicateur1(@Nullable String nomIndicateur1) {
        this.nomIndicateur1 = nomIndicateur1;
    }

    @Nullable
    public String getNomIndicateur2() {
        return nomIndicateur2;
    }

    public void setNomIndicateur2(@Nullable String nomIndicateur2) {
        this.nomIndicateur2 = nomIndicateur2;
    }

    @Nullable
    public String getNomIndicateur3() {
        return nomIndicateur3;
    }

    public void setNomIndicateur3(@Nullable String nomIndicateur3) {
        this.nomIndicateur3 = nomIndicateur3;
    }
}
