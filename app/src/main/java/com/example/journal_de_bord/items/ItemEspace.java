package com.example.journal_de_bord.items;

import androidx.fragment.app.Fragment;

import java.util.Date;

public class ItemEspace extends Fragment {
    private Date date;
    private String titre;
    private String index;

    public ItemEspace(Date date, String titre, String index) {
        this.date = date;
        this.titre = titre;
        this.index = index;
    }

    public Date getDate() {
        return date;
    }

    public String getTitre() {
        return titre;
    }

    public String getIndex() {
        return index;
    }
}
