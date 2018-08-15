package com.example.lmnop.onemeetingawaymap1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lmnop.onemeetingawaymap1.model.DataItemMeetings;

import java.util.List;

public class DataItemAdapterListView extends ArrayAdapter<DataItemMeetings> {

    List<DataItemMeetings> mDataItems;
    LayoutInflater mInflater;

    public DataItemAdapterListView(Context context,List<DataItemMeetings> objects) {
        super(context, R.layout.activity_maps, objects);

        mDataItems = objects;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_maps, parent, false);
        }

//        TextView tv1name = convertView.findViewById(R.id.meetingNameText);
//        TextView tv2name = convertView.findViewById(R.id.meetingLocationText);
//        TextView tv3name = convertView.findViewById(R.id.ocText);
//
//        DataItemMeetings item = mDataItems.get(position);
//
//        tv1name.setText(item.getMeetingName());
//        tv2name.setText(item.getDay());
//        tv3name.setText(item.getOc());

        return convertView;
    }
}
