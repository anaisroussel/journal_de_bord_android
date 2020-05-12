package com.example.journal_de_bord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AccueilFragment extends Fragment {

    public static AccueilFragment newInstance() {
        return (new AccueilFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_accueil, container, false);
        final FloatingActionButton floatingActionButtonAdd = rootView.findViewById(R.id.floatingActionButton2);

        floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTransactionFragment(AjoutDefi.newInstance());
            }
        });

        return rootView;
    }
    // Generic method that will replace and show a fragment inside the AccueilActivity Frame Layout
    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.activity_accueil_frame_layout, fragment).commit();
        }
    }
}
