package com.example.eventorganiser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class MyEvents extends AppCompatActivity {

    Button addEvent;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;
    private String grpName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        addEvent = (Button) findViewById(R.id.AddEvent);
        mRecyclerView = (RecyclerView)findViewById(R.id.MyEventsGLRecyclerView);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        mDatabase.keepSynced(true);

        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                grpName = String.valueOf(dataSnapshot.child("groupName").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MyEvents.this, AddEvent.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Event, MyEvents.EventViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Event, MyEvents.EventViewHolder>
                (Event.class,R.layout.cards_my_events_gl, MyEvents.EventViewHolder.class,mDatabase.orderByChild("name_of_grp").equalTo(grpName)){
            protected void populateViewHolder(MyEvents.EventViewHolder viewHolder, Event model, int position) {
                viewHolder.setName_of_Grp(model.getName_of_grp());
                viewHolder.setName_of_Event(model.getName_of_event());
                viewHolder.setDescription(model.getSpecifications());
                viewHolder.setPrerequisite(model.getPrerequisite());
                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime());
                viewHolder.setVenue(model.getVenue());
            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        View mView;
        private final Context context;
        String key;
        private TextView EventName,Description,Prerequisite,Date,Time,Venue;

        public EventViewHolder(View itemView){
            super(itemView);
            mView=itemView;
            mView.setClickable(true);
            context = itemView.getContext();

            EventName = (TextView)mView.findViewById(R.id.EventName);
            Description = (TextView)mView.findViewById(R.id.Description);
            Prerequisite = (TextView)mView.findViewById(R.id.CardPrerequisite);
            Date = (TextView)mView.findViewById(R.id.CardDate);
            Time = (TextView)mView.findViewById(R.id.CardTime);
            Venue = (TextView)mView.findViewById(R.id.Venue);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,Edit_Event_Details.class);
                    intent.putExtra("key",key);
                    intent.putExtra("EventName",EventName.getText().toString());
                    intent.putExtra("Description",Description.getText().toString());
                    intent.putExtra("Prerequisite",Prerequisite.getText().toString());
                    intent.putExtra("Date",Date.getText().toString());
                    intent.putExtra("Time",Time.getText().toString());
                    intent.putExtra("Venue",Venue.getText().toString());
                }
            });
        }

        public void setName_of_Grp(String name_of_grp){
            TextView GrpName = (TextView)mView.findViewById(R.id.GrpName);
            GrpName.setText(name_of_grp);
        }
        public void setName_of_Event(String name_of_event){
            EventName.setText(name_of_event);
        }
        public void setDescription(String description){
            Description.setText(description);
        }
        public void setPrerequisite(String prerequisite){
            Prerequisite.setText(prerequisite);
        }
        public void setDate(String date){
            Date.setText(date);
        }
        public void setTime(String time){
            Time.setText(time);
        }
        public void setVenue(String venue){
            Venue.setText(venue);
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
            if(FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).equals("group")) {
                startActivity(new Intent(getApplicationContext(), AccountDetails.class));
            }
            else if(FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).equals("student")) {
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
        else if(id == R.id.Calender_icon){
            finish();
            startActivity(new Intent(getApplicationContext(),Calender_View.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
