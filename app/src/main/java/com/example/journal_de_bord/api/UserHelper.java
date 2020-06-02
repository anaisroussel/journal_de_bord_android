package com.example.journal_de_bord.api;

import com.example.journal_de_bord.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserHelper {

    private static final String COLLECTION_NAME = "user";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createUser(String id, String email) {
        User userToCreate = new User(id, email);
        return UserHelper.getUsersCollection().document(id).set(userToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getUser(String id){
        return UserHelper.getUsersCollection().document(id).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateEmail(String email, String id) {
        return UserHelper.getUsersCollection().document(id).update("email", email);
    }

    // --- DELETE ---

    public static Task<Void> deleteUser(String id) {
        return UserHelper.getUsersCollection().document(id).delete();
    }

}
