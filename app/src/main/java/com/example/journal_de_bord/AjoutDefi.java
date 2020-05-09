package com.example.journal_de_bord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

public class AjoutDefi extends Fragment {

    private Fragment addDefi2Fragment;

    public static AjoutDefi newInstance() {
        return (new AjoutDefi());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_defi, container, false);
        /**
         * VARIABLES
         */
        final CheckBox checkBox = (CheckBox)rootView.findViewById(R.id.checkbox_1);
        final CheckBox checkBox2 = (CheckBox)rootView.findViewById(R.id.checkbox_2);
        final CheckBox checkBox3 = (CheckBox)rootView.findViewById(R.id.checkbox_3);
        final Switch swicthDynamically = rootView.findViewById(R.id.switchDynamically);
        final Spinner spinnerDynamically = rootView.findViewById(R.id.spinnerDynamically);
        final RatingBar ratingBarDynamically = rootView.findViewById(R.id.ratingBarDynamically);
        final EditText etSwitch = rootView.findViewById(R.id.etSwitch);
        final EditText etSpinner = rootView.findViewById(R.id.etSpinner);
        final EditText etRatingBar = rootView.findViewById(R.id.etRatingBar);
        final Button button = rootView.findViewById(R.id.button2);
        final EditText titre = rootView.findViewById(R.id.editText3);

        /**
         * On cache les éléments pas encore sélectionnés
         */
        swicthDynamically.setVisibility(View.GONE);
        spinnerDynamically.setVisibility(View.GONE);
        ratingBarDynamically.setVisibility(View.GONE);
        etRatingBar.setVisibility(View.GONE);
        etSpinner.setVisibility(View.GONE);
        etSwitch.setVisibility(View.GONE);

        /**
         * Première checkbox
         */
        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //is checkbox checked?
                if (((CheckBox) v).isChecked()) {
                    // Switch + EditText visibles
                    swicthDynamically.setVisibility(View.VISIBLE);
                    etSwitch.setVisibility(View.VISIBLE);
                }else  {
                    swicthDynamically.setVisibility(View.GONE);
                    etSwitch.setVisibility(View.GONE);
                }
            }
        });

        /**
         * Deuxième checkbox
         */
        checkBox2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //is checkbox checked?
                if (((CheckBox) v).isChecked()) {
                    ratingBarDynamically.setVisibility(View.VISIBLE);
                    etRatingBar.setVisibility(View.VISIBLE);
                }else {
                    ratingBarDynamically.setVisibility(View.GONE);
                    etRatingBar.setVisibility(View.GONE);
                }

            }
        });

        /**
         * Troisième checkbox
         */
        checkBox3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((CheckBox) v).isChecked()) {
                    spinnerDynamically.setVisibility(View.VISIBLE);
                    etSpinner.setVisibility(View.VISIBLE);
                } else{
                    spinnerDynamically.setVisibility(View.GONE);
                    etSpinner.setVisibility(View.GONE);
                }
            }
        });

        /**
         * Submit button
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if title is not empty
                if(titre.getText().toString() != null && !titre.getText().toString().isEmpty()) {
                    changeFragmentAddingDatas(titre, etRatingBar, etSpinner, etSwitch);
                } else {
                    Toast.makeText(getActivity(), "Veuillez donner un titre à votre espace", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    private void changeFragmentAddingDatas(EditText titre, EditText etRatingBar, EditText etSpinner, EditText etSwitch) {
        Bundle bundleDefi2 = new Bundle();

        if(etSwitch != null && etSwitch.getVisibility() == View.VISIBLE ) {
            bundleDefi2.putString("etSwitch",etSwitch.getText().toString());
            // pas besoin d'envoyer le switch, on le créera sur la prochaine activité si il existe
        }

        if(etRatingBar != null && etRatingBar.getVisibility() == View.VISIBLE) {
            bundleDefi2.putString("etRatingBar",etRatingBar.getText().toString());
        }

        if(etSpinner != null && etSpinner.getVisibility() == View.VISIBLE) {
            bundleDefi2.putString("etSpinner",etSpinner.getText().toString());
        }
        bundleDefi2.putString("titre",titre.getText().toString());
        if (this.addDefi2Fragment == null) this.addDefi2Fragment = AjoutDefiEtape2.newInstance();
        this.addDefi2Fragment.setArguments(bundleDefi2);
        // On vérifie que chaque nouveau champ a un nom
        if(verificationNomIndicateurs(etSwitch, etRatingBar, etSpinner)) {
            this.startTransactionFragment(this.addDefi2Fragment);
        } else {
            Toast.makeText(getActivity(),"Un de vos indicateurs n'a pas de nom", Toast.LENGTH_SHORT).show();
        }
    }

    // Generic method that will replace and show a fragment inside the AccueilActivity Frame Layout
    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            // on remplace l'ancien fragment par le nouveau
            getFragmentManager().beginTransaction().addToBackStack(null)
                    .replace(R.id.activity_accueil_frame_layout, fragment).commit();
        }
    }

    private boolean verificationNomIndicateurs(EditText et, EditText et2, EditText et3) {
        if(et != null)
        if(et.getVisibility() == View.VISIBLE && et.getText().toString().isEmpty()) {
            return false;
        }
        if(et2 != null)
        if(et2.getVisibility() == View.VISIBLE && et2.getText().toString().isEmpty()) {
            return false;
        }
        if(et3 != null)
        if(et3.getVisibility() == View.VISIBLE && et3.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

}
