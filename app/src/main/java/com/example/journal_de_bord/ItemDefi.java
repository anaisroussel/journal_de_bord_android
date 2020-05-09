package com.example.journal_de_bord;

import androidx.fragment.app.Fragment;

public class ItemDefi extends Fragment {
    private String date;
    private String titre;
    private int index;

    public ItemDefi(String date, String titre, int index) {
        this.date = date;
        this.titre = titre;
        this.index = index;
    }

    public String getDate() {
        return date;
    }

    public String getTitre() {
        return titre;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
