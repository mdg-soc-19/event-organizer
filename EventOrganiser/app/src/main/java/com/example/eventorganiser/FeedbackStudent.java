package com.example.eventorganiser;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FeedbackStudent extends AppCompatActivity {

    Button addToMyEvents, submitFeedback;
    TextView feedback;
    DatabaseReference mDatabase, pDatabase;
    String eventKey, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_student);

        addToMyEvents = (Button) findViewById(R.id.AddToMyEvents);
        submitFeedback = (Button) findViewById(R.id.submit_feedback_btn);
        feedback = (TextView) findViewById(R.id.feedback);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MyEvents");
        pDatabase = FirebaseDatabase.getInstance().getReference("Events").child(eventKey).child("Feedback");

        addToMyEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = mDatabase.push().getKey();
                mDatabase.child(key).setValue(eventKey);
            }
        });

        submitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = pDatabase.push().getKey();
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        username = String.valueOf(dataSnapshot.child("username").getValue());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                pDatabase.child(key).child("username").setValue(username);
                pDatabase.child(key).child("feedback").setValue(feedback);
            }

            ;

        });
    }
}
