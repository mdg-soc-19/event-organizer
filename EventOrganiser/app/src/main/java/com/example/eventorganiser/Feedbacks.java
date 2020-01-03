package com.example.eventorganiser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Feedbacks extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase, pDatabase;
    String key, userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbacks);

        recyclerView = (RecyclerView) findViewById(R.id.FeedbackRecyclerView);
        mDatabase = db.getReference("Events").child(key).child("Feedback");
        mDatabase.keepSynced(true);
        pDatabase = db.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        pDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userType = String.valueOf(dataSnapshot.child("userType").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView username, feedback;

        public FeedbackViewHolder(View view) {
            super(view);
            mView = view;
            username = (TextView) mView.findViewById(R.id.feedback_username);
            feedback = (TextView) mView.findViewById(R.id.feedback_feedback);
        }

        public void setUsername(String userName) {
            username.setText(userName);
        }

        public void setFeedback(String feedBack) {
            feedback.setText(feedBack);
        }
    }

    protected void onStart() {
        super.onStart();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerAdapter<Feedback, FeedbackViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Feedback, FeedbackViewHolder>
                (Feedback.class, R.layout.feedbackcards, FeedbackViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(FeedbackViewHolder feedbackViewHolder, Feedback feedback, int i) {
                feedbackViewHolder.setUsername(feedback.getUsername());
                feedbackViewHolder.setFeedback(feedback.getFeedback());
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_at_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Home) {
            finish();
            startActivity(new Intent(getApplicationContext(), Home.class));
        } else if (id == R.id.AccountDetails) {
            finish();
            if (userType.equals("group")) {
                startActivity(new Intent(getApplicationContext(), AccountDetails.class));
            } else if (userType.equals("student")) {
                startActivity(new Intent(getApplicationContext(), AccountDetails_Student.class));
            }
        }
        else if (id == R.id.MyEvents) {
            finish();
            startActivity(new Intent(getApplicationContext(), MyEvents.class));
        }
        else if (id == R.id.Logout) {
            finish();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }
        return super.onOptionsItemSelected(item);
    }
}



