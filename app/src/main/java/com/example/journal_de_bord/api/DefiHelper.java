package com.example.journal_de_bord.api;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import com.example.journal_de_bord.ItemDefi;
import com.example.journal_de_bord.MesDefis;
import com.example.journal_de_bord.MesEspaces;
import com.example.journal_de_bord.models.Defi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.List;

public class DefiHelper {

    private static final String COLLECTION_NAME = "defi";

    public static DatabaseReference getRef() {
        return FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
    }

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getDefisCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<DocumentReference> createDefi(Date date, String description, String nom, String espace, boolean indic1, float indic2, String indic3, String nomIndic1, String nomIndic2, String nomIndic3, String idUser) {
        String key = DefiHelper.getRef().push().getKey();
        Defi defiToCreate = new Defi(key, idUser, nom, espace, description, indic1, indic2, indic3, date, nomIndic1, nomIndic2, nomIndic3);
        return DefiHelper.getDefisCollection().add(defiToCreate);
    }

    public static String createDefiReturnKey(Date date, String description, String espace,
                                             String nom, boolean indic1, float indic2, String indic3,
                                             String nomIndic1, String nomIndic2, String nomIndic3, String idUser) {
        String key = DefiHelper.getRef().push().getKey();
        Defi defiToCreate = new Defi(key, idUser, nom, espace, description, indic1, indic2, indic3, date, nomIndic1, nomIndic2, nomIndic3);
        DefiHelper.getDefisCollection().add(defiToCreate);
        return key;
    }


    // --- GET ---

    public static void getDefi(String id, final Consumer<Defi> callback) {
        DefiHelper.getDefisCollection().whereEqualTo("id",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    callback.accept(task.getResult().toObjects(Defi.class).get(0));
                } else {
                    System.out.println("Error");
                }
            }
        });
    }

    public static Query getAllDefisByIdUser(String idUser) {
        return DefiHelper.getDefisCollection().whereEqualTo("iduser", idUser);
    }


    // --- UPDATE ---

    public static Task<Void> updateNom(String nom, String id) {
        return DefiHelper.getDefisCollection().document(id).update("nom", nom);
    }

    // --- DELETE ---

    public static Task<Void> deleteDefi(String id) {
        return DefiHelper.getDefisCollection().document(id).delete();
    }

    public static void getMesDefis(String idUser, final MesDefis mesDefis, final List<ItemDefi> itemDefis, final Consumer<List<ItemDefi>> callback) {
        DefiHelper.getDefisCollection().whereEqualTo("iduser",idUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Convert the whole snapshot to a POJO list
                            mesDefis.addAllDefis(task.getResult().toObjects(Defi.class));
                            System.out.println(mesDefis.getItemDefis());
                            callback.accept(itemDefis);
                        } else {
                            System.out.println("Error");
                        }
                    }
                });
    }

    public static void getMesDefisByEspace(String idUser, String espace, final MesEspaces mesEspaces, final List<ItemDefi> itemDefis, final Consumer<List<ItemDefi>> callback) {
        DefiHelper.getDefisCollection().whereEqualTo("iduser",idUser).whereEqualTo("espace",espace)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Convert the whole snapshot to a POJO list
                            mesEspaces.addAllDefis(task.getResult().toObjects(Defi.class));
                            callback.accept(itemDefis);
                        } else {
                            System.out.println("Error");
                        }
                    }
                });
    }
}