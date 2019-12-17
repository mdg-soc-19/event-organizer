package com.example.eventorganiser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private EventsAdapter mEventsAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Event> events,List<String> keys){
        mContext = context;
        mEventsAdapter = new EventsAdapter(events,keys);
        recyclerView.setAdapter(mEventsAdapter);
    }
    class EventItemView extends RecyclerView.ViewHolder{
        private TextView mGrpName;
        private TextView mEventName;
        private TextView mDescription;
        private TextView mDateTime;
        private TextView mVenue;

        private String key;

        public EventItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.material_card_view,parent,false));

            mGrpName = (TextView) itemView.findViewById(R.id.GrpName);
            mEventName = (TextView) itemView.findViewById(R.id.EventName);
            mDescription = (TextView) itemView.findViewById(R.id.Description);
            mDateTime = (TextView) itemView.findViewById(R.id.DateandTime);
            mVenue = (TextView) itemView.findViewById(R.id.Venue);
        }
        public void bind(Event event,String key){
            mGrpName.setText(event.getName_of_grp());
            mEventName.setText(event.getName_of_event());
            mDescription.setText(event.getSpecifications());
            mDateTime.setText(event.getDate_and_time());
            mVenue.setText(event.getVenue());
            this.key = key;
        }
    }
    class EventsAdapter extends RecyclerView.Adapter<RecyclerView_Config.EventItemView>{
        private List<Event> mEventList;
        private List<String> mKeys;

        public EventsAdapter(List<Event> mEventList, List<String> mKeys){
            this.mEventList = mEventList;
            this.mKeys = mKeys;
        }
        @NonNull
        @Override
        public EventItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            return new EventItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView_Config.EventItemView holder, int position){
            holder.bind(mEventList.get(position),mKeys.get(position));
        }
        @Override
        public int getItemCount(){return mEventList.size();}
    }
}


