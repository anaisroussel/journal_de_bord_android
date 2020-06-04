package com.example.journal_de_bord;

import androidx.fragment.app.Fragment;

import java.util.Date;

public class ItemDefi extends Fragment {
    private Date date;
    private String titre;
    private String index;

    public ItemDefi(Date date, String titre, String index) {
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
