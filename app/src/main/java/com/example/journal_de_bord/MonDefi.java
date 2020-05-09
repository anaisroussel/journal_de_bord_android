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

import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

public class MonDefi extends Fragment {

    private int index = 0;
    static private Map<Integer,MonDefi> listeDefisMap = new HashMap<>();


    public MonDefi (int index) {
        this.index = index;
    }

    public static MonDefi getDefi(int index) {
        System.out.println("on cherche le défi:"+ index);
        if(listeDefisMap.containsKey(index)) {
            System.out.println("on a tyrouvé ca :"+ listeDefisMap.get(index));
            return listeDefisMap.get(index);
        } else {
            MonDefi monDefi = new MonDefi(index);
            listeDefisMap.put(index, monDefi);
            System.out.println("La liste de mes défis :"+listeDefisMap);
            System.out.println("Mon défi c'est so :"+monDefi);
            return monDefi;
        }
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
        Bundle bundle = this.getArguments();

        switchTextView.setVisibility(View.GONE);
        spinnerTextView.setVisibility(View.GONE);
        ratingBarTextView.setVisibility(View.GONE);
        switchMonDefi.setVisibility(View.GONE);
        spinnerMonDefi.setVisibility(View.GONE);
        ratingBar.setVisibility(View.GONE);

        if(bundle != null) {
            this.setIndex(bundle.getInt("index"));
            textViewTitre.setText(bundle.getString("titre"));
            textViewDate.setText(bundle.getString("date"));
            if(bundle.getString("etSwitch") != null && !bundle.getString("etSwitch").isEmpty()) {
                // rendre les champs visibles
                switchTextView.setVisibility(View.VISIBLE);
                switchMonDefi.setVisibility(View.VISIBLE);
                // remplir les valeurs
                switchTextView.setText(bundle.getString("etSwitch"));
                switchMonDefi.setChecked(bundle.getBoolean("switchValue"));
            }
            if(bundle.getString("etSpinner") != null && !bundle.getString("etSpinner").isEmpty()) {
                spinnerTextView.setVisibility(View.VISIBLE);
                spinnerMonDefi.setVisibility(View.VISIBLE);
                // remplir les valeurs
                spinnerTextView.setText(bundle.getString("etSpinner"));
                fillSpinnerValue(spinnerMonDefi, bundle.getString("spinner"));
            }
            if(bundle.getString("etRatingBar") != null && !bundle.getString("etRatingBar").isEmpty()) {
                ratingBarTextView.setVisibility(View.VISIBLE);
                ratingBar.setVisibility(View.VISIBLE);
                // remplir les valeurs
                ratingBarTextView.setText(bundle.getString("etRatingBar"));
                ratingBar.setRating(bundle.getFloat("ratingBar"));
            }
        }
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
