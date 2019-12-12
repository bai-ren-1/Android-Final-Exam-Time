package com.jnu.ITime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;

import static com.jnu.ITime.MainActivity.REQUEST_CODE_EDIT;
import static com.jnu.ITime.MainActivity.RESULT_DELETE;
import static com.jnu.ITime.MainActivity.RESULT_UPDATE;

public class DetailActivity extends AppCompatActivity {

    private int position;
    private TextView detail_content;
    private TextView detail_enddate;
    private TextView detail_daycounts;
    private Long save_enddate;

    private int edit=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //用来绑定toolbar
        Toolbar toolbar = findViewById(R.id.Tool_Bar);
        setSupportActionBar(toolbar);

        //设置toolbar返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //通过intent接收来自主页面的数据并设置在详情页
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        String content = intent.getStringExtra("content");
        String enddate = intent.getStringExtra("enddate");
        String daycounts = intent.getStringExtra("daycounts");

        detail_content = (TextView) findViewById(R.id.Event_Detail_Content);
        detail_enddate = (TextView) findViewById(R.id.Target_Date);
        detail_daycounts = (TextView) findViewById(R.id.Event_Detail_Days);

        detail_daycounts.setText(daycounts);
        detail_content.setText(content);
        detail_enddate.setText(enddate);
    }

    //工具栏返回键
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //工具栏后面的菜单键
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_toolbar, menu);
        return true;
    }

    //实现edit的toolbar上的菜单功能
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.Delete:
            //删除点击事件
            intent=new Intent(DetailActivity.this,MainActivity.class);
            intent.putExtra("position",position);
            Toast.makeText(DetailActivity.this, "成功删除", Toast.LENGTH_SHORT).show();
            setResult(RESULT_DELETE, intent);
            DetailActivity.this.finish();
            break;
            case R.id.Edit:
                //编辑点击事件
                intent = new Intent(DetailActivity.this, EditActivity.class);
                intent.putExtra("content",detail_content.getText().toString());
                intent.putExtra("enddate",detail_enddate.getText().toString());
                startActivityForResult(intent, REQUEST_CODE_EDIT);
                break;
            case android.R.id.home:
                //jz:返回并保存数据给main
                if(edit!=0){
                    //传数据
                    intent.putExtra("position",position);
                    intent.putExtra("content",detail_content.getText().toString());
                    intent.putExtra("endtime",detail_enddate.getText().toString());
                    intent.putExtra("dayscount",detail_daycounts.getText().toString());
                    Toast.makeText(DetailActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_UPDATE, intent);
                }
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_EDIT:
                if(data!=null){
                    //设置一个全局变量保存是否编辑过的值
                    edit=1;
                    Calendar calendar = Calendar.getInstance();
                    Date currentdate = calendar.getTime();
                    String save_content=data.getStringExtra("content");
                    Long save_enddate=data.getLongExtra("enddate",0);
                    Event event = new Event(2, save_content, CalculateTime.StrToDate(CalculateTime.longTostring(save_enddate)), currentdate, R.drawable.clock2);
                    detail_content.setText(save_content);
                    detail_enddate.setText(CalculateTime.longTostring(save_enddate));
                    detail_daycounts.setText(Long.toString(event.getDateCounts()));
                }
                break;

        }
    }

}
