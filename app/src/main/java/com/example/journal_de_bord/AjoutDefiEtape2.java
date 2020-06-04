package com.example.journal_de_bord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.journal_de_bord.api.DefiHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AjoutDefiEtape2 extends Fragment {

    private Fragment monDefiFragment;

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
        final Bundle bundleToSent = new Bundle();

        final Bundle bundle = this.getArguments();
        if (bundle != null) {
            // Récupération du titre
            String titre = bundle.getString("titre");
            // Afficher le titre de l'espace
            espaceTitle.setText(titre);

            // Mise en page en fonction des éléments choisis dans "AjoutDéfi"
            miseEnPage(bundle, textViewSwitch, textViewSpinner, textViewRatingBar, ratingBar, switchEtape2, spinnerEtape2);

            // ajout titre dans Bundle à envoyer à "Mes défis"
            bundleToSent.putString("titre", titre);
        }

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
                    bundleToSent.putString("date", date);
                    sendDatastoMonDefi(bundle, bundleToSent, switchEtape2, ratingBar, spinnerEtape2, descriptionText);
                    System.out.println("Mon bundle :"+ bundleToSent);
                    // insertion en BDD
                    String key = insertDefiInDatabase(dateFormatDate,description.getText().toString(),bundleToSent);
                    changeFragment(bundleToSent, key);

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

    private void changeFragment(Bundle bundleToSent, String key) {
        // On envoie le bundle à la fois à la liste des défis et à la page "MonDéfi"
        //MesDefis.getInstanceMesDefis().addNewItemDefi(bundleToSent);
        this.monDefiFragment = new MonDefi(key);
        this.monDefiFragment.setArguments(bundleToSent);

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

    private void addStringDataInBundle(Bundle bundle, String key, String value) {
        if(bundle != null) {
            bundle.putString(key, value);
        }
    }

    private void addFloatDataInBundle(Bundle bundle, String key, float value) {
        if(bundle != null) {
            bundle.putFloat(key, value);
        }
    }

    private void addBooleanDataInBundle(Bundle bundle, String key, boolean value) {
        if(bundle != null) {
            bundle.putBoolean(key, value);
        }
    }

    private void sendDatastoMonDefi(Bundle bundle, Bundle bundleToSent, Switch switchEtape2, RatingBar ratingBar, Spinner spinnerEtape2, String description) {

        if(switchEtape2.getVisibility() == View.VISIBLE) {
            addStringDataInBundle(bundleToSent, "etSwitch", bundle.getString("etSwitch"));
            addBooleanDataInBundle(bundleToSent, "switchValue", switchEtape2.isChecked());
        }

        if(ratingBar.getVisibility() == View.VISIBLE) {
            addStringDataInBundle(bundleToSent, "etRatingBar", bundle.getString("etRatingBar"));
            addFloatDataInBundle(bundleToSent,"ratingBar", ratingBar.getRating());
        }

        if(spinnerEtape2.getVisibility() == View.VISIBLE) {
            addStringDataInBundle(bundleToSent, "etSpinner", bundle.getString("etSpinner"));
            addStringDataInBundle(bundleToSent, "spinner", spinnerEtape2.getSelectedItem().toString());
        }

        addStringDataInBundle(bundleToSent, "description", description);
    }

    private String insertDefiInDatabase(Date date, String description, Bundle bundle) {
        boolean indicSwitch = false;
        String nomIndicSwitch = "";

        float indicRatingBar = 0;
        String indicSpinner = "";

        String nomIndicRatingBar = "";
        String nomIndicSpinner = "";

        if(bundle.getString("etSwitch") !=null) {
            indicSwitch =  bundle.getBoolean("switchValue");
            nomIndicSwitch = bundle.getString("etSwitch");
        }

        if(bundle.getString("etRatingbar") != null) {
            indicRatingBar = bundle.getFloat("ratingBar");
            nomIndicRatingBar = bundle.getString("etRatingBar");
        }

        if(bundle.getString("etSpinner") !=null) {
            indicSpinner = bundle.getString("spinner");
            nomIndicSpinner = bundle.getString("etSpinner");
        }

        return DefiHelper.createDefiReturnKey(date,description,bundle.getString("titre"),indicSwitch,indicRatingBar,indicSpinner,nomIndicSwitch,nomIndicRatingBar,nomIndicSpinner, getCurrentUserId());
    }

    protected String getCurrentUserId(){ return FirebaseAuth.getInstance().getCurrentUser().getUid(); }

}
