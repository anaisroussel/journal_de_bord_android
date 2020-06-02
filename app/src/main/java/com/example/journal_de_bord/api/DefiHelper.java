package com.example.journal_de_bord.api;

import com.example.journal_de_bord.models.Defi;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;

public class DefiHelper {

    private static final String COLLECTION_NAME = "defi";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getDefisCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createDefi(String id, Date date, String description, String nom, boolean indic1, boolean indic2, boolean indic3, String nomIndic1, String nomIndic2, String nomIndic3, String idUser) {
        Defi defiToCreate = new Defi(id,idUser,date,description,indic1,indic2,indic3,nomIndic1,nomIndic2,nomIndic3);
        return DefiHelper.getDefisCollection().document(id).set(defiToCreate);
    }


    // --- GET ---

    public static Task<DocumentSnapshot> getDefi(String id){
        return DefiHelper.getDefisCollection().document(id).get();
    }

    public static Query getAllDefisByIdUser(String idUser) {
        return DefiHelper.getDefisCollection().whereEqualTo("iduser",idUser);
    }

    // --- UPDATE ---

    public static Task<Void> updateNom(String nom, String id) {
        return DefiHelper.getDefisCollection().document(id).update("nom", nom);
    }

    // --- DELETE ---

    public static Task<Void> deleteDefi(String id) {
        return DefiHelper.getDefisCollection().document(id).delete();
    }
}
