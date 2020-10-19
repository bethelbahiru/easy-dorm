package com.example.dorm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UpdateFood extends AppCompatActivity {

    EditText appetizer, dessert, mainDish, calo;
    FirebaseFirestore firebaseFirestore;
    String foodID;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        appetizer = findViewById(R.id.appetizers);
        dessert = findViewById(R.id.Desserts);
        mainDish = findViewById(R.id.mainDishes);
        calo = findViewById(R.id.calories);
        button = findViewById(R.id.button3);

        firebaseFirestore = FirebaseFirestore.getInstance();
        Intent intents = getIntent();
        final CollectionReference foodFirebase = firebaseFirestore.collection("Food");

        String mains = intents.getStringExtra("main");
        String apps = intents.getStringExtra("app");
        String des = intents.getStringExtra("des");
        String calory  = intents.getStringExtra("calories");
        final String dates = intents.getStringExtra("date");
        final String courses = intents.getStringExtra("course");

        appetizer.setText(apps);
        dessert.setText(des);
        mainDish.setText(mains);
        calo.setText(calory);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                foodFirebase.whereEqualTo("date", dates).whereEqualTo("course", courses).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            foodID = documentSnapshot.getId();

                        }

                        DocumentReference foods = firebaseFirestore.collection("Food").document(foodID);
                        foods.update("appetizer", appetizer.getText().toString());
                        foods.update("mainDish", mainDish.getText().toString());
                        foods.update("dessert", dessert.getText().toString());
                        foods.update("calory", calo.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateFood.this, "Update Food Course", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(view.getContext(),FoodList.class));

                            }
                        });



                    }
                });
            }
        });


    }
}