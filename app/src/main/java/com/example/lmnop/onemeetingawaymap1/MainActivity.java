package com.example.lmnop.onemeetingawaymap1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lmnop.onemeetingawaymap1.Adapters.ListMeetingsAdapter;
import com.example.lmnop.onemeetingawaymap1.model.DataItemMeetings;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private RecyclerView listRecyclerView;
    private ListMeetingsAdapter listAdapter;
    public ArrayList<DataItemMeetings> meetingList;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_meetings_list);
        meetingList= new ArrayList<>();


        listRecyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        listRecyclerView.setHasFixedSize(true);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new ListMeetingsAdapter(this, meetingList);
        listRecyclerView.setAdapter(listAdapter);


    }

}
