package com.example.dorm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DisplayFoodProgram extends AppCompatActivity implements View.OnClickListener {

    TextView course, date, appetizer, mainDish, dessert, calories;
    FirebaseFirestore firebaseFirestore;
    Button button, updateButton;
    String foodID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_food_program);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseFirestore = FirebaseFirestore.getInstance();
        Intent intents = getIntent();
        CollectionReference foodFirebase = firebaseFirestore.collection("Food");
        button = findViewById(R.id.button3);
        updateButton = findViewById(R.id.button4);

        String dates = intents.getStringExtra("date");
        String courses = intents.getStringExtra("course");


        date = findViewById(R.id.date);
        appetizer = findViewById(R.id.appetizer);
        mainDish = findViewById(R.id.main);
        dessert = findViewById(R.id.dessert);
        calories = findViewById(R.id.calory);
        course  = findViewById(R.id.textView5);

        date.setText(dates);
        course.setText(courses);
        button.setOnClickListener(this);
        updateButton.setOnClickListener(this);


        foodFirebase.whereEqualTo("date", dates).whereEqualTo("course", courses).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String mains = "";
                String des = "";
                String calory = "";
                String app = "";
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    Food foods = documentSnapshot.toObject(Food.class);
                    foodID = documentSnapshot.getId();



                    mains = foods.getMainDish();
                    des = foods.getDessert();
                    calory = foods.getCalory();
                    app = foods.getAppetizer();
                }

                mainDish.setText(mains);
                appetizer.setText(app);
                dessert.setText(des);
                calories.setText(calory);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == button){
            CollectionReference foodFirebase = firebaseFirestore.collection("Food");
            foodFirebase.document(foodID).delete();
            Toast.makeText(DisplayFoodProgram.this, "Food Program Deleted", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(view.getContext(),FoodList.class));

        }

        if(view == updateButton){
            Intent i = new Intent(view.getContext(), UpdateFood.class);
            i.putExtra("course", course.getText().toString());
            i.putExtra("date", date.getText().toString());
            i.putExtra("main", mainDish.getText().toString());
            i.putExtra("app", appetizer.getText().toString());
            i.putExtra("des", dessert.getText().toString());
            i.putExtra("calories", calories.getText().toString());
            startActivity(i);
        }
    }
}