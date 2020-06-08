package com.example.journal_de_bord;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.journal_de_bord.adapters.DefiAdapter;
import com.example.journal_de_bord.adapters.EspaceAdapter;
import com.example.journal_de_bord.api.DefiHelper;
import com.example.journal_de_bord.api.EspaceHelper;
import com.example.journal_de_bord.items.ItemDefi;
import com.example.journal_de_bord.items.ItemEspace;
import com.example.journal_de_bord.models.Defi;
import com.example.journal_de_bord.models.Espace;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import androidx.core.util.Consumer;

public class MesEspaces extends Fragment {

    public static MesEspaces newInstance(){return new MesEspaces();}

    private ListView mListView;
    private List<ItemEspace> itemEspaces = new ArrayList<>();
    private List<ItemDefi> itemDefis = new ArrayList<>();

    private MesEspaces instanceMesEspaces;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mes_espaces, container, false);

        instanceMesEspaces = this;

        mListView = (ListView) rootView.findViewById(R.id.listEspaces);
        EspaceHelper.getMesEspaces(getCurrentUserId(), this, this.itemEspaces, new Consumer<List<ItemEspace>>() {
            @Override
            public void accept(final List<ItemEspace> itemEspaces) {
                EspaceAdapter adapter = new EspaceAdapter(getActivity(), itemEspaces);
                mListView.setAdapter(adapter);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // si on appuie sur un ItemEspace on affiche la listes des Itemdefi
                if(mListView.getItemAtPosition(position).getClass().equals(ItemEspace.class)) {
                    System.out.println("get index :" + itemEspaces.get(position).getIndex());
                DefiHelper.getMesDefisByIdEspace(getCurrentUserId(),itemEspaces.get(position).getIndex(),
                        instanceMesEspaces , null, new Consumer<List<ItemDefi>>() {
                            @Override
                            public void accept(final List<ItemDefi> itemEspaces) {
                                System.out.println("après recup :" + itemEspaces);
                                DefiAdapter adapter = new DefiAdapter(getActivity(),itemDefis);
                                mListView.setAdapter(adapter);
                            }
                        });
                } else if(mListView.getItemAtPosition(position).getClass().equals(ItemDefi.class)){
                    startTransactionFragment(new MonDefi(itemDefis.get(position).getIndex()));
                }
            }
        });

        return rootView ;
    }


    /**
     * Méthode qui retourne une liste d'ItemDefi depuis une liste de défis
     * @param espaces la liste des espaces
     * @return la liste des ItemEspace
     */
    public List<ItemEspace> addAllEspaces(List<Espace> espaces) {
        if(!espaces.isEmpty()) {
            for(Espace espace : espaces) {
                this.itemEspaces.add(new ItemEspace(espace.getDatecreation(), espace.getNom(), espace.getId()));
            }
        }
        return this.itemEspaces;
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

    // Generic method that will replace and show a fragment inside the AccueilActivity Frame Layout
    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            // on remplace l'ancien fragment par le nouveau
            getFragmentManager().beginTransaction().addToBackStack(null)
                    .replace(R.id.activity_accueil_frame_layout, fragment).commit();
        }
    }

    protected String getCurrentUserId(){ return FirebaseAuth.getInstance().getCurrentUser().getUid(); }

}
