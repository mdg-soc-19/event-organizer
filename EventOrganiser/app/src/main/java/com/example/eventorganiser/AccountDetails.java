package com.example.eventorganiser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class AccountDetails extends AppCompatActivity {

    private DatabaseReference mDatabase;
    FirebaseAuth fAuth;
    private TextView username,groupName,email;
    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mDatabase.keepSynced(true);

        username = (TextView)findViewById(R.id.accountDetails_Username);
        groupName = (TextView)findViewById(R.id.accountDetails_GroupName);
        email = (TextView)findViewById(R.id.accountDetails_Email);
        edit = (Button)findViewById(R.id.AccountDetails_Edit);
    }

    protected void onStart(){
        super.onStart();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    GroupLeaderDetail groupLeaderDetail = userSnapshot.getValue(GroupLeaderDetail.class);
                    username.setText(groupLeaderDetail.getUsername());
                    groupName.setText(groupLeaderDetail.getGroupName());
                    email.setText(groupLeaderDetail.getEmailId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
