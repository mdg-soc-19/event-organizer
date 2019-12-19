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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView Name,Email,Mobile,EnrollNo,Password,CPassword;
    Button SignUp;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = findViewById(R.id.Name1);
        Email = findViewById(R.id.Email1);
        Mobile = findViewById(R.id.Mobile1);
        EnrollNo = findViewById(R.id.Enroll_No1);
        Password = findViewById(R.id.Password1);
        CPassword = findViewById(R.id.Cpassword1);
        SignUp = findViewById(R.id.Signup1);
        fAuth = FirebaseAuth.getInstance();

        /*if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }*/

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name =  Name.getText().toString().trim();
                String email = Email.getText().toString().trim();
                String mobile = Mobile.getText().toString().trim();
                String enrollno = EnrollNo.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String cpassword = CPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Email.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(name)){
                    Name.setError("Name is required");
                    return;
                }

                if(TextUtils.isEmpty(mobile)){
                    Mobile.setError("Mobile number is required");
                    return;
                }

                if(mobile.length() != 10){
                    Mobile.setError("Invalid mobile number");
                    return;
                }

                if(TextUtils.isEmpty(enrollno)){
                    EnrollNo.setError("Enrollment Number is required");
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
                            Toast.makeText(MainActivity.this,"User Created",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),Login.class));
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Error !"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
