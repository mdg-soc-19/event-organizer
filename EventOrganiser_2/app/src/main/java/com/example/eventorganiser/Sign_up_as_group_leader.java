package com.example.eventorganiser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_up_as_group_leader extends AppCompatActivity {

    TextView GroupName,Email,Password,CPassword;
    Button SignUp;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_group_leader);

        GroupName = findViewById(R.id.GroupName2);
        Email = findViewById(R.id.Email2);
        Password = findViewById(R.id.Password2);
        CPassword = findViewById(R.id.Cpassword2);
        SignUp = findViewById(R.id.Signup2);

        fAuth = FirebaseAuth.getInstance();
       /* if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }*/

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String groupname = GroupName.getText().toString().trim();
                String cpassword = CPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Email.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(groupname)){
                    GroupName.setError("Group Name is required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Password.setError("Password is required");
                    return;
                }

                if(password.length()<6){
                    Password.setError("Password must be greater than or equal to 6 Characters");
                    return;
                }
                if(!cpassword.equals(password)){
                    CPassword.setError("Password and Confirm Password should be equal");
                }

                //register the user in Firebase.

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Sign_up_as_group_leader.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Login.class));
                        }
                        else{
                            Toast.makeText(Sign_up_as_group_leader.this,"Error !"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
