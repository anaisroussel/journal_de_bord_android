package com.example.journal_de_bord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;


import com.example.journal_de_bord.api.DefiHelper;
import com.example.journal_de_bord.models.Defi;

import java.util.HashMap;
import java.util.Map;

public class MonDefi extends Fragment {

    private String id ;
    private Defi defi;
    static private Map<String,MonDefi> listeDefisMap = new HashMap<>();


    public MonDefi (String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mon_defi, container, false);

        /**
         * VARIABLES
         */
        final TextView textViewTitre = rootView.findViewById(R.id.titreMonDefi);
        final TextView textViewDate = rootView.findViewById(R.id.dateMondefi);
        final TextView switchTextView = rootView.findViewById(R.id.etSwitchMonDefi);
        final TextView spinnerTextView = rootView.findViewById(R.id.etSpinnerMonDefi);
        final TextView ratingBarTextView = rootView.findViewById(R.id.etRatingBarMonDefi);
        final RatingBar ratingBar = rootView.findViewById(R.id.ratingBarMonDefi);
        final Switch switchMonDefi = rootView.findViewById(R.id.switchMonDefi);
        final Spinner spinnerMonDefi = rootView.findViewById(R.id.spinnerMonDefi);
        //Bundle bundle = this.getArguments();

        textViewTitre.setVisibility(View.GONE);
        textViewDate.setVisibility(View.GONE);
        switchTextView.setVisibility(View.GONE);
        spinnerTextView.setVisibility(View.GONE);
        ratingBarTextView.setVisibility(View.GONE);
        switchMonDefi.setVisibility(View.GONE);
        spinnerMonDefi.setVisibility(View.GONE);
        ratingBar.setVisibility(View.GONE);

        /**
         * Récupération du Defi en BDD
         */
        DefiHelper.getDefi(id, new Consumer<Defi>() {
            @Override
            public void accept(Defi myDefi) {
                System.out.println("dans le accept, defi vaut"+myDefi);
                defi = myDefi;
                textViewTitre.setText(defi.getTitle());
                textViewDate.setText(defi.getDate().toString());

                textViewTitre.setVisibility(View.VISIBLE);
                textViewDate.setVisibility(View.VISIBLE);

                if(defi.getNomIndicateurSwitch() != null && !defi.getNomIndicateurSwitch().isEmpty()) {
                    // rendre les champs visibles
                    switchTextView.setVisibility(View.VISIBLE);
                    switchMonDefi.setVisibility(View.VISIBLE);
                    // remplir les valeurs
                    switchTextView.setText(defi.getNomIndicateurSwitch());
                    switchMonDefi.setChecked(defi.isIndicateurSwitch());
                }
                if(defi.getNomIndicateurSpinner() != null && !defi.getNomIndicateurSpinner().isEmpty()) {
                    spinnerTextView.setVisibility(View.VISIBLE);
                    spinnerMonDefi.setVisibility(View.VISIBLE);
                    // remplir les valeurs
                    spinnerTextView.setText(defi.getNomIndicateurSpinner());
                    fillSpinnerValue(spinnerMonDefi, defi.getIndicateurSpinner());
                }
                if(defi.getNomIndicateurStars() != null && !defi.getNomIndicateurStars().isEmpty()) {
                    ratingBarTextView.setVisibility(View.VISIBLE);
                    ratingBar.setVisibility(View.VISIBLE);
                    // remplir les valeurs
                    ratingBarTextView.setText(defi.getNomIndicateurStars());
                    ratingBar.setRating(defi.getIndicateurStars());
                }
            }
        });

        return rootView;
    }


    private void fillSpinnerValue(Spinner spinner, String value) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.note, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (value != null) {
            int spinnerPosition = adapter.getPosition(value);
            spinner.setSelection(spinnerPosition);
        }
    }

}
