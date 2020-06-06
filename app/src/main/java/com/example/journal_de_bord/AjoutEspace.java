package com.example.journal_de_bord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.journal_de_bord.api.EspaceHelper;
import com.google.firebase.auth.FirebaseAuth;

public class AjoutEspace extends Fragment {
    public static AjoutEspace newInstance() {
        return (new AjoutEspace());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_espace, container, false);

        final EditText editTextAjoutEspace = rootView.findViewById(R.id.nameEspace);
        final Button buttonAjoutEspace = rootView.findViewById(R.id.addEspaceButton);

        buttonAjoutEspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextAjoutEspace.getText().toString() != null && !editTextAjoutEspace.getText().toString().isEmpty()) {
                    EspaceHelper.createEspaceReturnKey(editTextAjoutEspace.getText().toString(),getCurrentUserId());
                } else {
                    Toast.makeText(getActivity(),"Veuillez entrer un titre", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    protected String getCurrentUserId(){ return FirebaseAuth.getInstance().getCurrentUser().getUid(); }

}
