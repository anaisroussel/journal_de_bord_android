package com.example.journal_de_bord.api;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import com.example.journal_de_bord.ItemEspace;
import com.example.journal_de_bord.MesEspaces;
import com.example.journal_de_bord.models.Espace;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.List;

public class EspaceHelper {

    private static final String COLLECTION_NAME = "espace";

    public static DatabaseReference getRef() {
        return FirebaseDatabase.getInstance().getReference(COLLECTION_NAME);
    }

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getEspacesCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static String createEspaceReturnKey(String name, String idUser) {
        String key = EspaceHelper.getRef().push().getKey();
        Espace espaceToCreate = new Espace(key, name, idUser, new Date());
        EspaceHelper.getEspacesCollection().add(espaceToCreate);
        return key;
    }

    public static void getMesEspaces(String idUser, final MesEspaces mesEspaces, final List<ItemEspace> itemEspaces, final Consumer<List<ItemEspace>> callback) {
        EspaceHelper.getEspacesCollection().whereEqualTo("idUser",idUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Convert the whole snapshot to a POJO list
                            if(mesEspaces != null) mesEspaces.addAllEspaces(task.getResult().toObjects(Espace.class));
                            callback.accept(itemEspaces);
                            System.out.println("les espaces récupérés:"+task.getResult().toObjects(Espace.class));
                        } else {
                            System.out.println("Error");
                        }
                    }
                });
    }

    public static void fillSpinnerWithEspaces(String idUser, final Consumer<List<Espace>> callback) {
        EspaceHelper.getEspacesCollection().whereEqualTo("idUser",idUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Convert the whole snapshot to a POJO list
                            System.out.println("les espaces récupérés:"+task.getResult().toObjects(Espace.class));
                            callback.accept(task.getResult().toObjects(Espace.class));
                        } else {
                            System.out.println("Error");
                        }
                    }
                });
    }
}
