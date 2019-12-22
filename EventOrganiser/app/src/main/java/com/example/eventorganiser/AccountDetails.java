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

public class AccountDetails extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        mRecyclerView = (RecyclerView) findViewById(R.id.AccountDetailsRecyclerView);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("GroupLeaderDetails");
        mDatabase.keepSynced(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<GroupLeaderDetail,GRDViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<GroupLeaderDetail, GRDViewHolder>
                (GroupLeaderDetail.class,R.layout.group_leader_details,GRDViewHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(GRDViewHolder grdViewHolder, GroupLeaderDetail groupLeaderDetail, int i) {
                grdViewHolder.setUsername(groupLeaderDetail.getUsername());
                grdViewHolder.setGroupName(groupLeaderDetail.getGroupName());
                grdViewHolder.setEmailId(groupLeaderDetail.getEmailId());
            }
        };
    }
    public static class GRDViewHolder extends RecyclerView.ViewHolder{
        View view;

        public GRDViewHolder(@NonNull View itemView, View view) {
            super(itemView);
            this.view = view;
        }
        public void setUsername(String username){
            TextView Username = (TextView)view.findViewById(R.id.accountDetails_Username);
            Username.setText(username);
        }
        public void setGroupName(String groupName){
            TextView Username = (TextView)view.findViewById(R.id.accountDetails_GroupName);
            Username.setText(groupName);
        }
        public void setEmailId(String emailId){
            TextView Username = (TextView)view.findViewById(R.id.accountDetails_Email);
            Username.setText(emailId);
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
