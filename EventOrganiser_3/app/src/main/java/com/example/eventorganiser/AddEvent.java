package com.example.eventorganiser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AddEvent extends AppCompatActivity {

    private EditText nameOfGroup,nameOfEvent,dateAndTime,venueOfEvent,specifications,prerequisite;
    private Button submitBtn;
    private Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        nameOfGroup = (EditText)findViewById(R.id.NameOfGroup);
        nameOfEvent = (EditText) findViewById(R.id.NameOfEvent);
        dateAndTime = (EditText) findViewById(R.id.DateAndTime);
        venueOfEvent = (EditText)findViewById(R.id.VenueOfEvent);
        specifications = (EditText)findViewById(R.id.Specifications);
        prerequisite = (EditText)findViewById(R.id.Prerequisite);
        submitBtn = (Button)findViewById(R.id.Submit_btn);
        back_btn = (Button)findViewById(R.id.Back_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event event = new Event();
                event.setDate_and_time(dateAndTime.getText().toString());
                event.setName_of_event(nameOfEvent.getText().toString());
                event.setName_of_grp(nameOfGroup.getText().toString());
                event.setPrerequisite(prerequisite.getText().toString());
                event.setSpecifications(specifications.getText().toString());
                event.setVenue(venueOfEvent.getText().toString());
                new FirebaseDatabaseHelper().addEvent(event, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Event> events, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(AddEvent.this,"The event record has been inserted successfully.",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(AddEvent.this,MyEvents.class));
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
            startActivity(new Intent(getApplicationContext(),Home.class));
        }
        else if(id == R.id.AccountDetails){
            startActivity(new Intent(getApplicationContext(),AccountDetails.class));
        }
        else if(id == R.id.PastEvents){
            startActivity(new Intent(getApplicationContext(),PastEvents.class));
        }
        else if(id == R.id.MyEvents){
            startActivity(new Intent(getApplicationContext(),MyEvents.class));
        }
        else if(id == R.id.Logout){
            startActivity(new Intent(getApplicationContext(),Login.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
