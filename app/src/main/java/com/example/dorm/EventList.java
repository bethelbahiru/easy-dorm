package com.example.dorm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EventList extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewData;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Event");
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        textViewData = findViewById(R.id.textView7);
        button = findViewById(R.id.button8);
        button.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        notebookRef.orderBy("startDate").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                String data = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Event note = documentSnapshot.toObject(Event.class);
                    String startDate = note.getStartDate();
                    String time = note.getTime();
                    String address = note.getAddress();
                    String eventName = note.getEventName();
                    String phone = note.getPhoneNumber();

                    data += "Event name: " + eventName
                            + "\nDate of Event: " + startDate + "\nTime of event: " + time + "\nPlace of event: " + address +
                             "\nContact information: " + phone + "\n\n";
                }
                textViewData.setText(data);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == button){
            startActivity(new Intent(getApplicationContext(), AddEvent.class));

        }
    }
}