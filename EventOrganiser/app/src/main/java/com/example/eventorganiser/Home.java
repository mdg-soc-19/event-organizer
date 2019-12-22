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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = (RecyclerView) findViewById(R.id.HomeRecyclerView);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        mDatabase.keepSynced(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("date");

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
                viewHolder.setDateAndTime(model.getDate());
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
        public void setDateAndTime(String dateAndTime){
            TextView DateandTime = (TextView)mView.findViewById(R.id.DateandTime);
            DateandTime.setText(dateAndTime);
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
            finish();
            startActivity(new Intent(getApplicationContext(),AccountDetails.class));
        }

        else if(id == R.id.MyEvents){
            finish();
            startActivity(new Intent(getApplicationContext(),MyEvents.class));
        }
        else if(id == R.id.Logout){
            finish();
            FirebaseAuth.getInstance().signOut();
        }
        else if(id == R.id.Calender_icon){
            finish();
            startActivity(new Intent(getApplicationContext(),Calender_View.class));
        }

    return super.onOptionsItemSelected(item);
    }
}
