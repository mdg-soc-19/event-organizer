package com.example.eventorganiser;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateGroupLeaderDetails extends AppCompatActivity {

    private String username, groupName, email;
    private TextView userName, GroupName, Email;
    private Button update , back;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_group_leader_details);
        username = getIntent().getStringExtra("username");
        groupName = getIntent().getStringExtra("groupName");
        email = getIntent().getStringExtra("email");

        userName = (TextView) findViewById(R.id.Edit_Username);
        GroupName = (TextView) findViewById(R.id.Edit_GroupName);
        Email = (TextView) findViewById(R.id.Edit_Email);
        update = (Button) findViewById(R.id.Update);
        back = (Button)findViewById(R.id.Edit_Back);

        userName.setText(username);
        GroupName.setText(groupName);
        Email.setText(email);

        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Username = userName.getText().toString().trim();
                final String Group_Name = GroupName.getText().toString().trim();
                final String emailId = Email.getText().toString().trim();

                if (TextUtils.isEmpty(emailId)) {
                    Email.setError("Email is Required");
                }
                if (TextUtils.isEmpty(Username)) {
                    userName.setError("Username is Required");
                }

                if (TextUtils.isEmpty(Group_Name)) {
                    GroupName.setError("Group Name is required");
                }

                UserDetails userDetails = new UserDetails(Username,Group_Name,emailId,"group");
                mDatabase.setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),AccountDetails.class));
                        finish();
                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AccountDetails.class));
                finish();
            }
        });
    }
}
