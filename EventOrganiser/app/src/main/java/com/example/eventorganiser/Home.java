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

public class Home extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase , pDatabase;
    String date , userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = (RecyclerView) findViewById(R.id.HomeRecyclerView);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        mDatabase.keepSynced(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pDatabase = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        pDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userType = String.valueOf(dataSnapshot.child("userType").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       date = getIntent().getStringExtra("date");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Event,EventViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Event,EventViewHolder>
                (Event.class,R.layout.material_card_view,EventViewHolder.class,mDatabase){
            protected void populateViewHolder(EventViewHolder viewHolder,Event model,int position){
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
    public static class EventViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public EventViewHolder(View itemView){
            super(itemView);
            mView=itemView;
        }
        public void setName_of_Grp(String name_of_grp){
            TextView GrpName = (TextView)mView.findViewById(R.id.GrpName);
            GrpName.setText(name_of_grp);
        }
        public void setName_of_Event(String name_of_event){
            TextView EventName = (TextView)mView.findViewById(R.id.EventName);
            EventName.setText(name_of_event);
        }
        public void setDescription(String description){
            TextView Description = (TextView)mView.findViewById(R.id.Description);
            Description.setText(description);
        }
        public void setPrerequisite(String prerequisite){
            TextView Prerequisite = (TextView)mView.findViewById(R.id.CardPrerequisite);
            Prerequisite.setText(prerequisite);
        }
        public void setDate(String date){
            TextView Date = (TextView)mView.findViewById(R.id.CardDate);
            Date.setText(date);
        }
        public void setTime(String time){
            TextView Time = (TextView)mView.findViewById(R.id.CardTime);
            Time.setText(time);
        }
        public void setVenue(String venue){
            TextView Venue = (TextView)mView.findViewById(R.id.Venue);
            Venue.setText(venue);
        }

    }




    @Override
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
            if(userType.equals("student")){
                startActivity(new Intent(getApplicationContext(),AccountDetails_Student.class));
            }
            else if(userType.equals("group")){
                startActivity(new Intent(getApplicationContext(),AccountDetails.class));
            }
        }

        else if(id == R.id.MyEvents){
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
