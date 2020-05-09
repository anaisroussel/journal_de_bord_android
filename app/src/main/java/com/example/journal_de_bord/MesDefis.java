package com.example.journal_de_bord;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class MesDefis extends Fragment {

    private ListView mListView;
    private List<ItemDefi> defis = new ArrayList<>();
    // Singleton instance : on la crée pour pouvoir récupérer une instance et éviter de l'écraser par une nouvelle vide
    private static MesDefis sInstance = null;
    private Fragment fragmentMonDefi;

    public static MesDefis getInstanceMesDefis() {
        if(sInstance !=null) {
            return sInstance;
        } else {
            sInstance = new MesDefis();
            return sInstance;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mes_defis, container, false);

        sInstance = this;

        Bundle bundle = this.getArguments();
        System.out.println("Voici le bundle passé :"+bundle);

        mListView = (ListView) rootView.findViewById(R.id.listDefis);
        // On vérifie que le bundle n'est ni null ni vide et qu'un défi n'existe pas déja avec l'index reçu par le bundle
        if(bundle != null && !bundle.isEmpty()) {
            addNewItemDefi(bundle);
        }
        DefiAdapter adapter = new DefiAdapter(getActivity(), this.defis);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                ItemDefi item = (ItemDefi) mListView.getItemAtPosition(position);
                changeFragment(item);
                Toast.makeText(getActivity(),"You selected : " + item.getIndex(),Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
    // addNewDefi(MonDefi)

    private void changeFragment(ItemDefi item) {
        this.fragmentMonDefi = MonDefi.getDefi(item.getIndex());
        System.out.println("Mon nouveau défi:" + this.fragmentMonDefi + "avec l'index"+ item.getIndex());
        startTransactionFragment(this.fragmentMonDefi);
    }

    public void addNewItemDefi(Bundle bundle){
        System.out.println("La date c'est :"+bundle.get("date"));
        // on vérifie que defis ne contient pas déja un ItemDefi avec cet index
        for(ItemDefi itemDefi : defis) {
            if(itemDefi.getIndex() == bundle.getInt("index")) {
                return;
            }
        }
        defis.add(new ItemDefi(bundle.getString("date"), bundle.getString("titre"), bundle.getInt("index")));

    }

    // Generic method that will replace and show a fragment inside the AccueilActivity Frame Layout
    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            // on remplace l'ancien fragment par le nouveau
            getFragmentManager().beginTransaction().addToBackStack(null)
                    .replace(R.id.activity_accueil_frame_layout, fragment).commit();
        }
    }

    // Getter to access Singleton instance
    public static MesDefis getInstance() {
        return sInstance ;
    }
}


