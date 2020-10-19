package com.example.dorm.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dorm.R;
import com.example.dorm.editProfile;
import com.example.dorm.forgotPassword;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;

public class ProfileFragment extends Fragment {

    Button buttons, profileButton;
    TextView name, email, phone, school, place;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String studentID;
    String surnames;
    String names;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        name = root.findViewById(R.id.profileName);
        email = root.findViewById(R.id.email);
        phone = root.findViewById(R.id.phone);
        school = root.findViewById(R.id.school);
        place = root.findViewById(R.id.place);
        buttons = root.findViewById(R.id.button4);
        profileButton = root.findViewById(R.id.button3);

       Intent data = getActivity().getIntent();

      //  Bundle b = getArguments();

       String phones = data.getStringExtra("phone");
       String cities = data.getStringExtra("placeOfBirth");
       String schools = data.getStringExtra("school");

     //   String phones = b.getString("phone");
     //   String cities = b.getString("placeOfBirth");
     //   String schools = b.getString("school");



        phone.setText(phones);
        place.setText(cities);
        school.setText(schools);

        studentID = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firebaseFirestore.collection("students").document(studentID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()) {

                    surnames = value.getString("surname");
                    names = value.getString("name");

                    name.setText(value.getString("name"));
                    name.append(" ");
                    name.append(value.getString("surname"));
                    email.setText(value.getString("email"));

                    phone.setText(value.getString("phone"));
                    school.setText(value.getString("school"));
                    place.setText(value.getString("placeOfBirth"));
                }
            }
        });

        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), forgotPassword.class);
                startActivity(intent);
            }
        });


        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), editProfile.class);
                i.putExtra("name", names);
                i.putExtra("email", email.getText().toString());
                i.putExtra("surname", surnames);   // surname setting
                i.putExtra("phone", phone.getText().toString());
                i.putExtra("place", place.getText().toString());
                i.putExtra("school", school.getText().toString());

                startActivity(i);
            }
        });


        return root;
    }
}