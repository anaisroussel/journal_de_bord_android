package com.example.journal_de_bord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;

import com.example.journal_de_bord.api.DefiHelper;
import com.example.journal_de_bord.api.EspaceHelper;
import com.example.journal_de_bord.models.Espace;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjoutDefiEtape2 extends Fragment {

    private Fragment monDefiFragment;
    private Map mapEspace = new HashMap<String, String>();

    public static AjoutDefiEtape2 newInstance() {
        return (new AjoutDefiEtape2());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_defi_2, container, false);
        /**
         * VARIABLES
         */
        final TextView espaceTitle = rootView.findViewById(R.id.ajoutEspaceEtape2);
        final EditText editTextDate = rootView.findViewById(R.id.editTextDate);
        final Button createEspaceButton = rootView.findViewById(R.id.buttonCreateDefi);
        final TextView textViewSwitch = rootView.findViewById(R.id.textViewSwitch);
        final TextView textViewSpinner = rootView.findViewById(R.id.textViewSpinner);
        final TextView textViewRatingBar = rootView.findViewById(R.id.textViewRatingBar);
        final RatingBar ratingBar = rootView.findViewById(R.id.ratingBarEtape2);
        final Switch switchEtape2 = rootView.findViewById(R.id.switchEtape2);
        final Spinner spinnerEtape2 = rootView.findViewById(R.id.spinnerEtape2);
        final EditText description = rootView.findViewById(R.id.descriptionAddDefi2);
        final Spinner spinnerChoixEspace = rootView.findViewById(R.id.spinnerChoixEspace);

        final Bundle bundle = this.getArguments();
        if (bundle != null) {
            // Récupération du titre
            String titre = bundle.getString("titre");
            // Afficher le titre de l'espace
            espaceTitle.setText(titre);
            // Mise en page en fonction des éléments choisis dans "AjoutDéfi"
            miseEnPage(bundle, textViewSwitch, textViewSpinner, textViewRatingBar, ratingBar, switchEtape2, spinnerEtape2);

        }

        EspaceHelper.fillSpinnerWithEspaces(getCurrentUserId(), new Consumer<List<Espace>>() {
            @Override
            public void accept(final List<Espace> espaces) {
                System.out.println("après recup :" + espaces);
                // fill the spinner
                List<String> spinnerArray =  new ArrayList<String>();
                for(Espace espace : espaces) {
                    mapEspace.put(espace.getNom(), espace.getId());
                    spinnerArray.add(espace.getNom());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerChoixEspace.setAdapter(adapter);
            }
        });

        createEspaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération de la date
                String date = editTextDate.getText().toString();
                Date dateFormatDate = null;
                try {
                    dateFormatDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // Récupération de la descriiption
                String descriptionText = description.getText().toString();

                if(date != null && !date.isEmpty() && isValidDate(date)) {
                    // insertion en BDD
                    String key = insertDefiInDatabase(dateFormatDate,descriptionText,bundle, switchEtape2, spinnerEtape2, ratingBar, spinnerChoixEspace);
                    // changement de fragment : vers MonDefi créé
                    changeFragment(key);

                } else {
                    Toast.makeText(getActivity(),"Veuillez entrer une date au format JJ/MM/AAAA",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    private void miseEnPage(Bundle bundle, TextView textViewSwitch, TextView textViewSpinner, TextView textViewRatingBar, RatingBar ratingBar, Switch switchEtape2, Spinner spinnerEtape2) {
        if(bundle.getString("etSwitch") != null) {
            textViewSwitch.setText(bundle.getString("etSwitch"));
            textViewSwitch.setVisibility(View.VISIBLE);
            switchEtape2.setVisibility(View.VISIBLE);
        } else {
            textViewSwitch.setVisibility(View.GONE);
            switchEtape2.setVisibility(View.GONE);
        }
        if(bundle.getString("etRatingBar") != null) {
            textViewRatingBar.setText(bundle.getString("etRatingBar"));
            textViewRatingBar.setVisibility(View.VISIBLE);
            ratingBar.setVisibility(View.VISIBLE);
        } else {
            textViewRatingBar.setVisibility(View.GONE);
            ratingBar.setVisibility(View.GONE);
        }
        if(bundle.getString("etSpinner") != null) {
            textViewSpinner.setText(bundle.getString("etSpinner"));
            textViewSpinner.setVisibility(View.VISIBLE);
            spinnerEtape2.setVisibility(View.VISIBLE);
        } else {
            textViewSpinner.setVisibility(View.GONE);
            spinnerEtape2.setVisibility(View.GONE);
        }
    }

    private void changeFragment(String key) {
        this.monDefiFragment = new MonDefi(key);
        this.startTransactionFragment(this.monDefiFragment);
    }

    // Generic method that will replace and show a fragment inside the AccueilActivity Frame Layout
    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            // on remplace l'ancien fragment par le nouveau
            getFragmentManager().beginTransaction().addToBackStack(null)
                    .replace(R.id.activity_accueil_frame_layout, fragment).commit();
        }
    }

    private boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    private String insertDefiInDatabase(Date date, String description, Bundle bundle, Switch switchEtape2,
                                        Spinner spinnerEtape2, RatingBar ratingBar, Spinner spinnerChoixEspace) {
        boolean indicSwitch = false;
        String nomIndicSwitch = "";

        float indicRatingBar = 0;
        String indicSpinner = "";

        String nomIndicRatingBar = "";
        String nomIndicSpinner = "";

        if(bundle.getString("etSwitch") !=null) {
            indicSwitch =  switchEtape2.isChecked();
            nomIndicSwitch = bundle.getString("etSwitch");
        }

        if(bundle.getString("etRatingBar") != null) {
            indicRatingBar = ratingBar.getRating();
            nomIndicRatingBar = bundle.getString("etRatingBar");
        }

        if(bundle.getString("etSpinner") !=null) {
            indicSpinner = spinnerEtape2.getSelectedItem().toString();
            nomIndicSpinner = bundle.getString("etSpinner");
        }

        return DefiHelper.createDefiReturnKey(date,description, mapEspace.get(spinnerChoixEspace.getSelectedItem().toString()).toString(),
                bundle.getString("titre"),indicSwitch,indicRatingBar,indicSpinner,nomIndicSwitch,nomIndicRatingBar,
                nomIndicSpinner, getCurrentUserId());
    }

    protected String getCurrentUserId(){ return FirebaseAuth.getInstance().getCurrentUser().getUid(); }

}
