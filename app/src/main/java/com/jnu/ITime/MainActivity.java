package com.jnu.ITime;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //定义各类事件响应码进行跳转
    public static final int REQUEST_CODE_ADD = 999;
    public static final int REQUEST_CODE_DELETE = 998;
    public static final int REQUEST_CODE_EDIT = 997;
    public static final int REQUEST_CODE_UPDATE = 996;
    public static final int RESULT_DELETE = 0;
    public static final int RESULT_UPDATE = 1;

    ListView listEvent;
    private List<Event> events = new ArrayList<>();
    private EventAdapter adapter;
    EventSaver eventSaver;

    //用一个Calendar来初始化当前日期
    Calendar calendar = Calendar.getInstance();
    Date currentdate = calendar.getTime();

    //定义默认的位置
    private int position = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventSaver.save();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //用来绑定toolbar
        Toolbar toolbar = findViewById(R.id.Tool_Bar);
        setSupportActionBar(toolbar);


        eventSaver=new EventSaver(this);
        events=eventSaver.load();
        //下面是绑定listView
        if(events.size()==0)
            init();
        listEvent = findViewById(R.id.List_View_Event);

        adapter = new EventAdapter(MainActivity.this, R.layout.list_view_item, events);
        listEvent.setAdapter(adapter);


        //给listView添加加点事件，然后通过intent传数据给详情页面
        listEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Event item = adapter.getItem(position);
                intent.putExtra("position", position);
                intent.putExtra("content", item.getContent());
                intent.putExtra("enddate", CalculateTime.dateToString(item.getendDate()));   //这里要将date型转string型
                intent.putExtra("daycounts", Long.toString(item.getDateCounts()));  //这里要将long型转string型
                startActivityForResult(intent, REQUEST_CODE_UPDATE);
            }
        });


        //下面是绑定添加事件的按钮
        FloatingActionButton addEventButton = (FloatingActionButton) findViewById(R.id.Add_Event_Button);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
                //设置点击事件后跳转添加事件页面
            }
        });
    }

    //初始化listView
    private void init() {
        events.add(new Event(0, "sleep ", currentdate, currentdate, R.drawable.clock3));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ADD:
                //获取addActivity传过来的数据
                if (data != null) {
                    Calendar calendar = Calendar.getInstance();
                    Date currentdate = calendar.getTime();
                    String content = data.getStringExtra("content");
                    Long enddate = data.getLongExtra("enddate", 0);
                    Long datetime = data.getLongExtra("datetime", 0);
                    Event event = new Event(2, content, CalculateTime.StrToDate(CalculateTime.longTostring(enddate)), currentdate, R.drawable.clock2);
                    events.add(event);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.notifyDataSetChanged();
                }
                break;

                case REQUEST_CODE_UPDATE:
                //获取detailsActivity，有两种状态，删除和保存编辑内容
                if (resultCode == RESULT_DELETE) {
                    if (data != null) {
                        position = data.getIntExtra("position", 1);
                        adapter.remove(adapter.getItem(position));
                        adapter.notifyDataSetChanged();
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    break;
                }

                else if(resultCode==RESULT_UPDATE){
                    if(data!=null){
                        position=data.getIntExtra("position",0);
                        //要用这种方式才能使adapter中的内容更新
                        Event eventAtPosition=events.get(position);
                        eventAtPosition.setContent(data.getStringExtra("content"));
                        eventAtPosition.setEndDate(CalculateTime.StrToDate(data.getStringExtra("endtime")));
                        eventAtPosition.setDateCounts(Long.parseLong(data.getStringExtra("dayscount")));
                        adapter.notifyDataSetChanged();
                    }else{
                        adapter.notifyDataSetChanged();
                    }
                    break;
                }
        }

    }
}