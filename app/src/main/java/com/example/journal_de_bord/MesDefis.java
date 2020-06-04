package com.example.journal_de_bord;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;

import com.example.journal_de_bord.api.DefiHelper;
import com.example.journal_de_bord.models.Defi;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MesDefis extends Fragment {

    public static MesDefis newInstance() {
        return (new MesDefis());
    }

    private ListView mListView;
    private List<ItemDefi> itemDefis = new ArrayList<>();
    private Fragment fragmentMonDefi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mes_defis, container, false);

        //sInstance = this;

        Bundle bundle = this.getArguments();
        System.out.println("Voici le bundle passé :"+bundle);

        mListView = (ListView) rootView.findViewById(R.id.listDefis);
        // On vérifie que le bundle n'est ni null ni vide et qu'un défi n'existe pas déja avec l'index reçu par le bundle
        /*if(bundle != null && !bundle.isEmpty()) {
            addNewItemDefi(bundle);
        }*/
        System.out.println("avant recup :" + this.itemDefis);
        DefiHelper.getMesDefis(getCurrentUserId(), this, this.itemDefis, new Consumer<List<ItemDefi>>() {
            @Override
            public void accept(final List<ItemDefi> itemDefis) {
                System.out.println("après recup :" + itemDefis);
                DefiAdapter adapter = new DefiAdapter(getActivity(), itemDefis);
                mListView.setAdapter(adapter);

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        ItemDefi item = (ItemDefi) mListView.getItemAtPosition(position);
                        changeFragment(itemDefis.get(position).getIndex());
                    }
                });
            }
        });

        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("destroyed");
        this.itemDefis = new ArrayList<>();
    }

    private void changeFragment(String id) {
        this.fragmentMonDefi = new MonDefi(id);
        System.out.println("Mon nouveau défi:" + this.fragmentMonDefi + "avec l'index"+ id);
        startTransactionFragment(this.fragmentMonDefi);
    }

    /*public void addNewItemDefi(Bundle bundle){
        // on vérifie que defis ne contient pas déja un ItemDefi avec cet index
        for(ItemDefi itemDefi : itemDefis) {
            if(itemDefi.getIndex() == bundle.getInt("index")) {
                return;
            }
        }
        itemDefis.add(new ItemDefi(bundle.getString("date"), bundle.getString("titre"), bundle.getInt("index")));

    }*/

    // Generic method that will replace and show a fragment inside the AccueilActivity Frame Layout
    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            // on remplace l'ancien fragment par le nouveau
            getFragmentManager().beginTransaction().addToBackStack(null)
                    .replace(R.id.activity_accueil_frame_layout, fragment).commit();
        }
    }

    /**
     * Méthode qui retourne une liste d'ItemDefi depuis une liste de défis
     * @param defis la liste des défis
     * @return la liste des ItemDefis
     */
    public List<ItemDefi> addAllDefis(List<Defi> defis) {
        if(!defis.isEmpty()) {
            for(Defi defi : defis) {
                this.itemDefis.add(new ItemDefi(defi.getDate(),defi.getTitle(),defi.getId()));
            }
        }
        System.out.println("addAllDefis : "+ this.itemDefis);
        return this.itemDefis;
    }

    public List<ItemDefi> getItemDefis() {
        return itemDefis;
    }

    protected String getCurrentUserId(){ return FirebaseAuth.getInstance().getCurrentUser().getUid(); }
}


