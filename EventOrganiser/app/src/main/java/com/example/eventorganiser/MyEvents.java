package com.example.eventorganiser;

import android.content.Context;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyEvents extends AppCompatActivity {

    FloatingActionButton addEvent;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase,pDatabase;
    private String grpName,userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        addEvent = (FloatingActionButton) findViewById(R.id.AddEvent);
        mRecyclerView = (RecyclerView)findViewById(R.id.MyEventsGLRecyclerView);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        mDatabase.keepSynced(true);
        pDatabase = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        pDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                grpName = String.valueOf(dataSnapshot.child("groupName").getValue());
                userType = String.valueOf(dataSnapshot.child("userType").getValue());
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
        FirebaseRecyclerAdapter<Event,EventViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Event, EventViewHolder>
                (Event.class,R.layout.cards_my_events_gl, EventViewHolder.class,mDatabase.orderByChild("name_of_grp").equalTo(grpName)){
            protected void populateViewHolder(EventViewHolder viewHolder, final Event model, int position) {
                viewHolder.setName_of_Grp(model.getName_of_grp());
                viewHolder.setName_of_Event(model.getName_of_event());
                viewHolder.setDescription("Specifications: "+model.getSpecifications());
                viewHolder.setPrerequisite("Prerequisite: "+model.getPrerequisite());
                viewHolder.setDate("Date: "+model.getDate());
                viewHolder.setTime("Time: "+model.getTime());
                viewHolder.setVenue(model.getVenue());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String NameofEvent = model.getName_of_event();
                        String description = model.getSpecifications();
                        String prerequisite = model.getPrerequisite();
                        String date = model.getDate();
                        String time = model.getTime();
                        String venue = model.getVenue();

                        Intent intent = new Intent(getApplicationContext(),Edit_Event_Details.class);
                        intent.putExtra("EventName",NameofEvent);
                        intent.putExtra("Description",description);
                        intent.putExtra("Prerequisite",prerequisite);
                        intent.putExtra("Date",date);
                        intent.putExtra("Time",time);
                        intent.putExtra("Venue",venue);
                        startActivity(intent);
                    }
                });
            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public static class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View mView;
        private final Context context;
        String key;
        TextView EventName,Description,Prerequisite,Date,Time,Venue;
        ItemClickListener itemClickListener;

        public EventViewHolder(View itemView){
            super(itemView);
            mView=itemView;
            mView.setClickable(true);
            context = itemView.getContext();
            itemView.setOnClickListener(this);

            EventName = (TextView)mView.findViewById(R.id.gl_EventName);
            Description = (TextView)mView.findViewById(R.id.gl_Description);
            Prerequisite = (TextView)mView.findViewById(R.id.gl_CardPrerequisite);
            Date = (TextView)mView.findViewById(R.id.gl_CardDate);
            Time = (TextView)mView.findViewById(R.id.gl_CardTime);
            Venue = (TextView)mView.findViewById(R.id.gl_Venue);

        }

        public void setName_of_Grp(String name_of_grp){
            TextView GrpName = (TextView)mView.findViewById(R.id.gl_GrpName);
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

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view,getAdapterPosition());
        }

        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener = ic;
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
