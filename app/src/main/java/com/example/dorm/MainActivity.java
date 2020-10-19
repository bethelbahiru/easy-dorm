package com.example.dorm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    Button buttons, profileButton;
    TextView name, email, phone, school, place;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    ImageView profilePicture;
    String studentID, surnames, names;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        name = findViewById(R.id.profileName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        school = findViewById(R.id.school);
        place = findViewById(R.id.place);
        buttons = findViewById(R.id.button4);
        profileButton = findViewById(R.id.button3);
        profilePicture = findViewById(R.id.imageView2);

        Intent data = getIntent();

        String phones = data.getStringExtra("phone");
        String cities = data.getStringExtra("placeOfBirth");
        String schools = data.getStringExtra("school");

            phone.setText(phones);
            place.setText(cities);
            school.setText(schools);

        studentID = firebaseAuth.getCurrentUser().getUid();

        StorageReference profileRef = storageReference.child("students/" + studentID + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePicture);
            }
        });

        DocumentReference documentReference = firebaseFirestore.collection("students").document(studentID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
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
                startActivity(new Intent(getApplicationContext(), forgotPassword.class));

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


    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity2.class));

    }


}