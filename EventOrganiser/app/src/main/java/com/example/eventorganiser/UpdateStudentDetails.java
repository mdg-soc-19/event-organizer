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

public class UpdateStudentDetails extends AppCompatActivity {

    String username, enrollNo, email,mobileNo;
    TextView userName, EnrollNo, Email, MobileNo;
    Button update , back;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student_details);
        username = getIntent().getStringExtra("username");
        enrollNo = getIntent().getStringExtra("enrollNo");
        email = getIntent().getStringExtra("email");
        mobileNo = getIntent().getStringExtra("mobileNo");

        userName = (TextView) findViewById(R.id.Edit2_Username);
        EnrollNo = (TextView) findViewById(R.id.Edit2_EnrollNo);
        Email = (TextView) findViewById(R.id.Edit2_Email);
        update = (Button) findViewById(R.id.Edit2_Update);
        back = (Button)findViewById(R.id.Edit2_Back);
        MobileNo =(TextView)findViewById(R.id.Edit2_MobileNo);

        userName.setText(username);
        EnrollNo.setText(enrollNo);
        Email.setText(email);
        MobileNo.setText(mobileNo);

        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Username = userName.getText().toString().trim();
                final String Enroll_no = EnrollNo.getText().toString().trim();
                final String emailId = Email.getText().toString().trim();
                final String Mobile_no = MobileNo.getText().toString().trim();

                if (TextUtils.isEmpty(emailId)) {
                    Email.setError("Email is Required");
                }
                if (TextUtils.isEmpty(Username)) {
                    userName.setError("Username is Required");
                }

                if (TextUtils.isEmpty(Enroll_no)) {
                    EnrollNo.setError("Group Name is required");
                }

                UserDetails userDetails = new UserDetails(Username,emailId,Enroll_no,Mobile_no,"student");
                mDatabase.setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(),AccountDetails.class));
                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(),AccountDetails_Student.class));
            }
        });
    }
}
