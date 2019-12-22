package com.example.eventorganiser;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceEvents;
    private DatabaseReference mReferenceGroupLeader;
    private List<Event> events = new ArrayList<>();
    private List<GroupLeaderDetail> groupLeaderDetails = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Event> events,List<String> keys);
        void DataIsInserted();
        void DataIsDeleted();
    }
    public interface DetailsStatus{
        void DataIsLoaded(List<GroupLeaderDetail> groupLeaderDetails,List<String> keys);
        void DataIsInserted();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceEvents = mDatabase.getReference("Events");
        mReferenceGroupLeader = mDatabase.getReference("GroupLeaderDetails");
    }

    public void readEvents(final DataStatus dataStatus){
        mReferenceEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                events.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Event event = keyNode.getValue(Event.class);
                    events.add(event);
                }
                dataStatus.DataIsLoaded(events,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addEvent (Event event,final DataStatus dataStatus){
        String key = mReferenceEvents.push().getKey();
        mReferenceEvents.child(key).setValue(event).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });
    }

    public void readGroupLeaderDetails(final DetailsStatus detailsStatus){
        mReferenceGroupLeader.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupLeaderDetails.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    GroupLeaderDetail groupLeaderDetail = keyNode.getValue(GroupLeaderDetail.class);
                    groupLeaderDetails.add(groupLeaderDetail);
                }
                detailsStatus.DataIsLoaded(groupLeaderDetails,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addGroupLeaderDetails (GroupLeaderDetail groupLeaderDetail,final DetailsStatus detailsStatus){
        String key = mReferenceGroupLeader.push().getKey();
        mReferenceGroupLeader.child(key).setValue(groupLeaderDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                detailsStatus.DataIsInserted();
            }
        });
    }
}
