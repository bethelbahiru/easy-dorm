package com.example.dorm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dorm.ui.gallery.ProfileFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class editProfile extends AppCompatActivity {

    EditText name, email, surname, phone, city, school;
    Button button, changeProfile;
    ImageView profilePicture;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String studentID;
    FirebaseUser student;
    StorageReference storageReference, profileRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilelayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent data = getIntent();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        student = firebaseAuth.getCurrentUser();

        profileRef = storageReference.child("students/" + firebaseAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePicture);
            }
        });

        final String names = data.getStringExtra("name");
        String emails = data.getStringExtra("email");
        final String surnames = data.getStringExtra("surname");
        String phones = data.getStringExtra("phone");
        String places = data.getStringExtra("place");
        String schools = data.getStringExtra("school");


        name = findViewById(R.id.PersonName);
        email = findViewById(R.id.email);
        surname = findViewById(R.id.SurName);
        phone = findViewById(R.id.phone);
        city = findViewById(R.id.HomeTown);
        school = findViewById(R.id.School);
        button = findViewById(R.id.button2);
        changeProfile = findViewById(R.id.button5);
        profilePicture = findViewById(R.id.imageView2);

        name.setText(names);
        email.setText(emails);
        surname.setText(surnames);
        phone.setText(phones);
        city.setText(places);
        school.setText(schools);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                /***
                 if(name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || surname.getText().toString().isEmpty()
                 || phone.getText().toString().isEmpty() || city.getText().toString().isEmpty() || school.getText().toString().isEmpty()){
                 Toast.makeText(editProfile.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                 return;
                 }
                 ***/
                final String emailUpdate = email.getText().toString();
                student.updateEmail(emailUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference documentReference = firebaseFirestore.collection("students").document(student.getUid());
                        Map<String, Object> edited = new HashMap();
                        edited.put("email", emailUpdate);
                        edited.put("name", name.getText().toString());
                        edited.put("surname", surname.getText().toString());
                        edited.put("phone", phone.getText().toString());
                        edited.put("placeOfBirth", city.getText().toString());
                        edited.put("school", school.getText().toString());

                        documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(editProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(view.getContext(), MainActivity.class);
                                i.putExtra("phone", phone.getText().toString());
                                i.putExtra("placeOfBirth", city.getText().toString());
                                i.putExtra("school", school.getText().toString());
                                startActivity(i);

                                /**
                                 Bundle bundle = new Bundle();
                                 bundle.putString("phone", phone.getText().toString());
                                 bundle.putString("placeOfBirth", city.getText().toString());
                                 bundle.putString("school", school.getText().toString());

                                 ProfileFragment pf = new ProfileFragment();
                                 pf.setArguments(bundle);
                                 getSupportFragmentManager().beginTransaction().replace(R.id.editProfile,pf).commit();
                                 **/
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(editProfile.this, "Email change failed", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                //   profilePicture.setImageURI(imageUri);
                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        final StorageReference storage = storageReference.child("students/" + firebaseAuth.getCurrentUser().getUid() + "/profile.jpg");
        storage.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(editProfile.this, "Profile Picture Uploaded Successfully.", Toast.LENGTH_SHORT).show();
                storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profilePicture);
                        Toast.makeText(editProfile.this, "Profile Picture Uploaded.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(editProfile.this, "Profile Picture Upload Failed.", Toast.LENGTH_SHORT).show();

            }
        });
    }
}