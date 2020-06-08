package com.example.journal_de_bord;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;


import com.example.journal_de_bord.api.DefiHelper;
import com.example.journal_de_bord.api.EspaceHelper;
import com.example.journal_de_bord.models.Defi;
import com.example.journal_de_bord.models.Espace;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MonDefi extends Fragment {

    private String id ;
    private Defi defi;


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
        final TextView desciptionTextView = rootView.findViewById(R.id.descriptionMonDefi);
        final TextView espaceTextView = rootView.findViewById(R.id.espaceMonDefi);
        final Button buttonModif = rootView.findViewById(R.id.buttonModifIndic);

        textViewTitre.setVisibility(View.GONE);
        textViewDate.setVisibility(View.GONE);
        switchTextView.setVisibility(View.GONE);
        spinnerTextView.setVisibility(View.GONE);
        ratingBarTextView.setVisibility(View.GONE);
        switchMonDefi.setVisibility(View.GONE);
        spinnerMonDefi.setVisibility(View.GONE);
        ratingBar.setVisibility(View.GONE);
        desciptionTextView.setVisibility(View.GONE);
        espaceTextView.setVisibility(View.GONE);
        buttonModif.setVisibility(View.GONE);

        /**
         * Récupération du Defi en BDD
         */
        DefiHelper.getDefi(id, new Consumer<Defi>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void accept(Defi myDefi) {
                defi = myDefi;
                // le defi a été chargé : on peut set les champs
                textViewTitre.setText(defi.getTitle());
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                textViewDate.setText(df.format(defi.getDate()));
                desciptionTextView.setText(defi.getDescription());
                desciptionTextView.setEnabled(false);
                // on récupère le nom de l'espace
                EspaceHelper.getEspace(defi.getEspace(), new Consumer<Espace>() {
                    @Override
                    public void accept(Espace espace) {
                        espaceTextView.setText(espace.getNom());
                    }
                });

                // Rendre les composants visibles une fois le chargement terminé
                textViewTitre.setVisibility(View.VISIBLE);
                textViewDate.setVisibility(View.VISIBLE);
                desciptionTextView.setVisibility(View.VISIBLE);
                espaceTextView.setVisibility(View.VISIBLE);
                //buttonModif.setVisibility(View.VISIBLE);

                System.out.println("defi.getNomIndicSwitch = "+defi.getNomIndicateurSwitch());

                if(defi.getNomIndicateurSwitch() != null && !defi.getNomIndicateurSwitch().isEmpty()) {
                    // rendre les champs visibles
                    switchTextView.setVisibility(View.VISIBLE);
                    switchMonDefi.setVisibility(View.VISIBLE);
                    // remplir les valeurs
                    switchTextView.setText(defi.getNomIndicateurSwitch());
                    switchMonDefi.setChecked(defi.isIndicateurSwitch());

                    switchMonDefi.setEnabled(false);
                }
                if(defi.getNomIndicateurSpinner() != null && !defi.getNomIndicateurSpinner().isEmpty()) {
                    spinnerTextView.setVisibility(View.VISIBLE);
                    spinnerMonDefi.setVisibility(View.VISIBLE);
                    // remplir les valeurs
                    spinnerTextView.setText(defi.getNomIndicateurSpinner());
                    fillSpinnerValue(spinnerMonDefi, defi.getIndicateurSpinner());

                    spinnerMonDefi.setEnabled(false);
                }
                if(defi.getNomIndicateurStars() != null && !defi.getNomIndicateurStars().isEmpty()) {
                    ratingBarTextView.setVisibility(View.VISIBLE);
                    ratingBar.setVisibility(View.VISIBLE);
                    // remplir les valeurs
                    ratingBarTextView.setText(defi.getNomIndicateurStars());
                    ratingBar.setRating(defi.getIndicateurStars());

                    ratingBar.setEnabled(false);
                }

                buttonModif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                         * Impossible de faire fonctionner la suppression ou la mise à jour de données
                         */
                        //suppression
                        /*DefiHelper.deleteDefi(defi.getId());
                        startTransactionFragment(AccueilFragment.newInstance());*/
                        // MAJ
                        System.out.println(buttonModif.getText().toString());
                        if(buttonModif.getText().toString().equalsIgnoreCase("Modifier les valeurs des indicateurs")) {
                            ratingBar.setEnabled(true);
                            spinnerMonDefi.setEnabled(true);
                            switchMonDefi.setEnabled(true);
                            buttonModif.setText("Enregistrer");
                            // fillAllValuesSpinner();
                        } else if(buttonModif.getText().toString() == "Enregistrer"){
                            // cas enregistrer
                            ratingBar.setEnabled(false);
                            spinnerMonDefi.setEnabled(false);
                            switchMonDefi.setEnabled(false);
                            // enregistrement en BDD
                            DefiHelper.updateValeursIndicateurs(defi.getId(), spinnerMonDefi.getSelectedItem().toString(),
                                    switchMonDefi.isChecked(), ratingBar.getRating());

                            buttonModif.setText("Modifier les valeurs des indicateurs");
                        }
                    }
                });
            }
        });

        return rootView;
    }

    // Generic method that will replace and show a fragment inside the AccueilActivity Frame Layout
    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            // on remplace l'ancien fragment par le nouveau
            getFragmentManager().beginTransaction().addToBackStack(null)
                    .replace(R.id.activity_accueil_frame_layout, fragment).commit();
        }
    }

    private void fillSpinnerValue(Spinner spinner, String value) {
        // fill the spinner
        List<String> spinnerArray =  new ArrayList<String>();
        if(value != null) {
            spinnerArray.add(value);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
