package com.example.eventorganiser;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Edit_Event_Details extends AppCompatActivity {

    private TextView eventName,description,prerequisite,date,time,venue;
    private String EventName,Description,Prerequisite,Date,Time,Venue,key;
    private Button Save_changes,delete,back,SeeFeedback;
    DatabaseReference mDatabase;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    String nameOfGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__event__details);
        eventName = (TextView)findViewById(R.id.EditNameOfEvent);
        description = (TextView)findViewById(R.id.EditSpecifications);
        prerequisite = (TextView)findViewById(R.id.EditPrerequisite);
        date = (TextView)findViewById(R.id.EditDate);
        time = (TextView)findViewById(R.id.EditTime);
        venue = (TextView)findViewById(R.id.EditVenueOfEvent);
        Save_changes = (Button)findViewById(R.id.EditSaveChanges_btn);
        delete = (Button)findViewById(R.id.EditDelete_btn);
        back = (Button)findViewById(R.id.EditBack_btn);
        SeeFeedback = (Button)findViewById(R.id.see_feedbacks_btn);

        EventName = getIntent().getStringExtra("EventName");
        Description = getIntent().getStringExtra("Description");
        Prerequisite = getIntent().getStringExtra("Prerequisite");
        Date = getIntent().getStringExtra("Date");
        Time = getIntent().getStringExtra("Time");
        Venue = getIntent().getStringExtra("Venue");
        key = getIntent().getStringExtra("key");

        eventName.setText(EventName);
        description.setText(Description);
        prerequisite.setText(Prerequisite);
        date.setText(Date);
        time.setText(Time);
        venue.setText(Venue);

        mDatabase = FirebaseDatabase.getInstance().getReference("Events").child(key);
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameOfGroup = String.valueOf(dataSnapshot.child("groupName").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Edit_Event_Details.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        Save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event event = new Event();
                event.setName_of_grp(nameOfGroup);
                event.setName_of_event(eventName.getText().toString().trim());
                event.setSpecifications(description.getText().toString().trim());
                event.setPrerequisite(prerequisite.getText().toString().trim());
                event.setDate(date.getText().toString().trim());
                event.setTime(time.getText().toString().trim());
                event.setVenue(venue.getText().toString().trim());

                if(TextUtils.isEmpty(eventName.toString())){
                    eventName.setError("This field is required");
                }

                if(TextUtils.isEmpty(prerequisite.toString())){
                    prerequisite.setError("This field is required.If there are no prerequisites then enter none.");
                }
                if(TextUtils.isEmpty(date.toString())){
                    date.setError("This field is required");
                }
                if(TextUtils.isEmpty(description.toString())){
                    description.setError("This field is required.If there are no specifications then enter none.");
                }
                if(TextUtils.isEmpty(venue.toString())){
                    venue.setError("This field is required");
                }
                if (TextUtils.isEmpty(time.toString())){
                    time.setError("This field is required");
                }
                mDatabase.setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Event Details updated successfully.",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MyEvents.class));
                        finish();
                    }
                });
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Event deleted successfully.",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MyEvents.class));
                        finish();
                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MyEvents.class));
                finish();
            }
        });

        SeeFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),Edit_Event_Details.class);
                in.putExtra("key",mDatabase.getKey());
                startActivity(in);
                finish();
            }
        });
    }
}
