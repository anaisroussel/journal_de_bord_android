package com.example.journal_de_bord;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Profile extends Fragment {

    public static Profile newInstance() {
        return (new Profile());
    }

    //FOR DATA
    // 2 - Identify each Http Request
    private static final int SIGN_OUT_TASK = 10;
    private static final int DELETE_USER_TASK = 20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        final ImageView imageViewProfile = rootView.findViewById(R.id.profile_activity_imageview_profile);
        final EditText editTextUsername = rootView.findViewById(R.id.profile_activity_edit_text_username);
        final TextView textViewEmail = rootView.findViewById(R.id.profile_activity_text_view_email);
        final ProgressBar progressBar = rootView.findViewById(R.id.profile_activity_progress_bar);
        final Button signOutButton = rootView.findViewById(R.id.profile_activity_button_sign_out);
        final Button deleteButton = rootView.findViewById(R.id.profile_activity_button_delete);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutUserFromFirebase();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.popup_message_confirmation_delete_account)
                        .setPositiveButton(R.string.popup_message_choice_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteUserFromFirebase();
                            }
                        })
                        .setNegativeButton(R.string.popup_message_choice_no, null)
                        .show();
            }
        });

        this.updateUIWhenCreating(imageViewProfile, editTextUsername, textViewEmail); // 2 - Update UI
        return rootView;
    }

    // Update UI when activity is creating
    private void updateUIWhenCreating(ImageView imageView, EditText editTextUsername, TextView textViewEmail){

        if (this.getCurrentUser() != null){
            //Get picture URL from Firebase
            if (this.getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this)
                        .load(this.getCurrentUser().getPhotoUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageView);
            }

            //Get email & username from Firebase
            String email = TextUtils.isEmpty(this.getCurrentUser().getEmail()) ? getString(R.string.info_no_email_found) : this.getCurrentUser().getEmail();
            String username = TextUtils.isEmpty(this.getCurrentUser().getDisplayName()) ? getString(R.string.info_no_username_found) : this.getCurrentUser().getDisplayName();

            System.out.println(email);
            System.out.println(username);
            //Update views with data
            editTextUsername.setText(username);
            textViewEmail.setText(email);
        }
    }

    public int getFragmentLayout() { return R.layout.fragment_profile; }

    // --------------------
    // REST REQUESTS
    // --------------------
    // Create http requests (SignOut & Delete)

    private void signOutUserFromFirebase(){
        AuthUI.getInstance()
                .signOut(getActivity())
                .addOnSuccessListener(getActivity(), this.updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK));
    }

    private void deleteUserFromFirebase(){
        if (this.getCurrentUser() != null) {
            AuthUI.getInstance()
                    .delete(getActivity())
                    .addOnSuccessListener(getActivity(), this.updateUIAfterRESTRequestsCompleted(DELETE_USER_TASK));
        }
    }

    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin){
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                switch (origin){
                    case SIGN_OUT_TASK:
                        getActivity().finish();
                        break;
                    case DELETE_USER_TASK:
                        getActivity().finish();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    // --------------------
    // UTILS
    // --------------------

    @Nullable
    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    protected Boolean isCurrentUserLogged(){ return (this.getCurrentUser() != null); }
}
