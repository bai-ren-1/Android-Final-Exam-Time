package com.jnu.ITime;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EventSaver {
    public EventSaver(Context context) {
        this.context = context;
    }

    Context context;

    public ArrayList<Event> getEvents() {
        return events;
    }

    ArrayList<Event> events=new ArrayList<Event>();

    public void save()
    {
        ObjectOutputStream outputStream= null;
        try {
            outputStream = new ObjectOutputStream(
                    context.openFileOutput("serializable.txt", Context.MODE_PRIVATE)
            );
            outputStream.writeObject(events);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Event> load(){
        try {
        ObjectInputStream inputStream=new ObjectInputStream(
                context.openFileInput("serializable.txt"));
        events=(ArrayList<Event>) inputStream.readObject();
        inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }
}
