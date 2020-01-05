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

public class MyEventsStudent extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference pDatabase;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events_student);

        recyclerView = findViewById(R.id.StudentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pDatabase = db.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        pDatabase.keepSynced(true);

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

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public EventViewHolder(View itemView){
            super(itemView);
            mView=itemView;
            mView.setClickable(true);
        }
        void setName_of_Grp(String name_of_grp){
            TextView GrpName = mView.findViewById(R.id.GrpName);
            GrpName.setText(name_of_grp);
        }
        void setName_of_Event(String name_of_event){
            TextView EventName = mView.findViewById(R.id.EventName);
            EventName.setText(name_of_event);
        }
        void setDescription(String description){
            TextView Description = mView.findViewById(R.id.Description);
            Description.setText(description);
        }
        void setPrerequisite(String prerequisite){
            TextView Prerequisite = mView.findViewById(R.id.CardPrerequisite);
            Prerequisite.setText(prerequisite);
        }
        public void setDate(String date){
            TextView Date = mView.findViewById(R.id.CardDate);
            Date.setText(date);
        }
        public void setTime(String time){
            TextView Time = mView.findViewById(R.id.CardTime);
            Time.setText(time);
        }
        public void setVenue(String venue){
            TextView Venue = mView.findViewById(R.id.Venue);
            Venue.setText(venue);
        }

    }

    protected void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<Event,EventViewHolder> firebaseRecyclerAdapter;
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event,EventViewHolder>
                    (Event.class, R.layout.material_card_view, EventViewHolder.class,pDatabase.child("MyEvents")) {
                @Override
                protected void populateViewHolder(EventViewHolder eventViewHolder, Event event, int i) {
                    eventViewHolder.setName_of_Grp(event.getName_of_grp());
                    eventViewHolder.setName_of_Event(event.getName_of_event());
                    eventViewHolder.setDescription(event.getSpecifications());
                    eventViewHolder.setDate(event.getDate());
                    eventViewHolder.setPrerequisite(event.getPrerequisite());
                    eventViewHolder.setTime(event.getTime());
                    eventViewHolder.setVenue(event.getVenue());
                }
            };
            recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_at_home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.Home){
            startActivity(new Intent(getApplicationContext(),Home.class));
            finish();
        }
        else if(id == R.id.AccountDetails){
            if(userType.equals("group")) {
                startActivity(new Intent(getApplicationContext(), AccountDetails.class));
                finish();
            }
            else if(userType.equals("student")) {
                startActivity(new Intent(getApplicationContext(),AccountDetails_Student.class));
                finish();
            }
        }

        else if(id == R.id.MyEvents){
            if(userType.equals("group")) {
                startActivity(new Intent(getApplicationContext(), MyEvents.class));
                finish();
            }
            else if(userType.equals("student")) {
                startActivity(new Intent(getApplicationContext(),MyEventsStudent.class));
                finish();
            }
        }
        else if(id == R.id.Logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
