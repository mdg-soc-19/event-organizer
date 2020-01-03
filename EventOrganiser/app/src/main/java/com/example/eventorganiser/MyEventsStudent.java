package com.example.eventorganiser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

import java.util.ArrayList;
import java.util.List;

public class MyEventsStudent extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase,pDatabase;
    String userType;
    List<String> keys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events_student);

        recyclerView = (RecyclerView)findViewById(R.id.StudentRecyclerView);
        mDatabase = db.getReference("Events");
        pDatabase = db.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mDatabase.keepSynced(true);
        pDatabase.keepSynced(true);

        pDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userType = String.valueOf(dataSnapshot.child("userType").getValue());
                for (DataSnapshot keyNode : dataSnapshot.child("Feedback").getChildren()){
                    keys.add(keyNode.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void onStart(){
        super.onStart();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerAdapter<Event, Home.EventViewHolder> firebaseRecyclerAdapter;
        for(String key : keys) {
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, Home.EventViewHolder>
                    (Event.class, R.layout.material_card_view, Home.EventViewHolder.class, mDatabase.child(key)) {
                @Override
                protected void populateViewHolder(Home.EventViewHolder eventViewHolder, Event event, int i) {
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
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_at_home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.Home){
            finish();
            startActivity(new Intent(getApplicationContext(),Home.class));
        }
        else if(id == R.id.AccountDetails){
            finish();
            if(userType.equals("group")) {
                startActivity(new Intent(getApplicationContext(), AccountDetails.class));
            }
            else if(userType.equals("student")) {
                startActivity(new Intent(getApplicationContext(),AccountDetails_Student.class));
            }        }

        else if(id == R.id.MyEvents){
            finish();
            startActivity(new Intent(getApplicationContext(),MyEvents.class));
        }
        else if(id == R.id.Logout){
            finish();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
