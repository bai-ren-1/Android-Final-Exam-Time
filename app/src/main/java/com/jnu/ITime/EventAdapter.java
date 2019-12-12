package com.jnu.ITime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;



public class EventAdapter extends ArrayAdapter<Event> {
    private int resourceId;

    public EventAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    //获取具体listView的视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event event = getItem(position);//获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ((ImageView) view.findViewById(R.id.Image)).setImageResource(event.getImageId());
        ((TextView) view.findViewById(R.id.Content)).setText("事件内容:\n"+event.getContent());
        ((TextView) view.findViewById(R.id.Target_Date)).setText("Deadline:\n"+event.getEndDate());
        ((TextView) view.findViewById(R.id.Count_Days)).setText("  还剩  "+Long.toString(event.getDateCounts())+"  天\n");
        return view;
    }
}

