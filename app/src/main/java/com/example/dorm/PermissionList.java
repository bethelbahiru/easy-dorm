package com.example.dorm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PermissionList extends AppCompatActivity {

    private TextView textViewData;
    String studentId;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth  firebaseAuth = FirebaseAuth.getInstance();
    private CollectionReference notebookRef = db.collection("Permission");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        studentId = firebaseAuth.getCurrentUser().getUid();
        textViewData = findViewById(R.id.textView7);
    }

    @Override
    protected void onStart() {
        super.onStart();
        notebookRef.whereEqualTo("studentId", studentId).addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                String data = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Permissions note = documentSnapshot.toObject(Permissions.class);
                    String startDate = note.getStartDate();
                    String endDate= note.getEndDate();
                    String address = note.getAddress();
                    String emergency = note.getContactName();
                    String relation = note.getRelation();
                    String phone = note.getPhoneNumber();
                    data += "Start Date: " + startDate
                            + "\nEnd Date: " + endDate + "\nAddress: " + address + "\nEmergency Contact Name: " + emergency +
                            "\nEmergency Contact Relation: " + relation + "\nEmergency Contact : " + phone + "\n\n";
                }
                textViewData.setText(data);
            }
        });
    }
}