package com.example.lmnop.onemeetingawaymap1.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lmnop.onemeetingawaymap1.R;
import com.example.lmnop.onemeetingawaymap1.model.DataItemMeetings;

import java.util.ArrayList;

public class ListMeetingsAdapter extends RecyclerView.Adapter<ListMeetingsAdapter.ListMeetingsViewHolder> {

    private Context context;
    private ArrayList<DataItemMeetings> meetingsList;

    //Constructor
    public ListMeetingsAdapter(Context context, ArrayList<DataItemMeetings> meetingsList) {
        this.context = context;
        this.meetingsList = meetingsList;
    }

    @NonNull
    @Override
    public ListMeetingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Creates ViewHolder Instance
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meetings_display, null);
        ListMeetingsViewHolder holder = new ListMeetingsViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ListMeetingsViewHolder holder, int position) {
        DataItemMeetings meeting = meetingsList.get(position);

        holder.dayView.setText(meeting.getDay());
        holder.startTimeView.setText(meeting.getStartTime());
        holder.endTimeView.setText(meeting.getEndTime());
        holder.ocView.setText(meeting.getOc());
        holder.locationView.setText(meeting.getLocation());
        holder.addressView.setText(meeting.getAddress());
        holder.codesView.setText(meeting.getCodes());
    }

    @Override
    public int getItemCount() {
        return meetingsList.size();
    }

    class ListMeetingsViewHolder extends RecyclerView.ViewHolder{

        TextView dayView,
                startTimeView,
                endTimeView,
                ocView,
                meetingNameView,
                locationView,
                addressView,
                codesView;

        public ListMeetingsViewHolder(View meetingView) {
            super(meetingView);

            dayView = meetingView.findViewById(R.id.day);
            startTimeView= meetingView.findViewById(R.id.startTime);
            endTimeView= meetingView.findViewById(R.id.endtime);
            ocView=meetingView.findViewById(R.id.OC);
            meetingNameView= meetingView.findViewById(R.id.meetingName);
            locationView= meetingView.findViewById(R.id.Location);
            addressView=meetingView.findViewById(R.id.address);
            codesView=meetingView.findViewById(R.id.codes);

        }
    }

}