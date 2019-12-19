package com.example.eventorganiser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MyEvents extends AppCompatActivity {

    Button addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        addEvent = (Button) findViewById(R.id.AddEvent);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MyEvents.this, AddEvent.class));
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
