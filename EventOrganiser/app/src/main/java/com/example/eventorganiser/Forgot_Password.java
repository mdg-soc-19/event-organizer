package com.example.eventorganiser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {

    private EditText passwordEmail;
    private Button resetPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);

        passwordEmail = (EditText) findViewById(R.id.forgot_pass_email);
        resetPassword = (Button) findViewById(R.id.Reset_password);
        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userMail = passwordEmail.getText().toString().trim();

                if(userMail.equals("")){
                    Toast.makeText(Forgot_Password.this,"Please enter registered Email ID.",Toast.LENGTH_SHORT);
                }
                else{
                    firebaseAuth.sendPasswordResetEmail(userMail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                              Toast.makeText(Forgot_Password.this,"Password reset Email sent!",Toast.LENGTH_SHORT);
                              finish();
                              startActivity(new Intent(Forgot_Password.this,Login.class));
                            }
                            else {
                                Toast.makeText(Forgot_Password.this,"Error in sending password reset mail!",Toast.LENGTH_SHORT);
                            }
                        }
                    });
                }
            }
        });
    }
}
