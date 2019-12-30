package com.example.eventorganiser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class AccountDetails extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView username,groupName,email;
    private Button edit , changePassword;
    String userType;

    String Username,GroupName,Email;

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
        changePassword = (Button)findViewById(R.id.AccountDetails_PassChange);



        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountDetails.this,Forgot_Password.class));
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Username = String.valueOf(dataSnapshot.child("username").getValue());
                        GroupName = String.valueOf(dataSnapshot.child("groupName").getValue());
                        Email = String.valueOf(dataSnapshot.child("emailId").getValue());

                        Intent intent = new Intent(AccountDetails.this,UpdateGroupLeaderDetails.class);
                        intent.putExtra("username",Username);
                        intent.putExtra("groupName",GroupName);
                        intent.putExtra("email",Email);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    protected void onStart(){
        super.onStart();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username.setText(String.valueOf(dataSnapshot.child("username").getValue()));
                groupName.setText(String.valueOf(dataSnapshot.child("groupName").getValue()));
                email.setText(String.valueOf(dataSnapshot.child("emailId").getValue()));
                userType = String.valueOf(dataSnapshot.child("userType").getValue());
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
