package com.jnu.ITime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddActivity extends AppCompatActivity {

    private static TextView datetime;
    private Button savebutton;
    private EditText inputcontent;

    //用来获取截止的date类日期
    private static Date finaldate=new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //用来绑定toolbar
        Toolbar toolbar = findViewById(R.id.Tool_Bar);
        setSupportActionBar(toolbar);

        //设置toolbar返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 绑定显示日期框
        datetime = (TextView) findViewById(R.id.Datetime);

        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        final Date date = calendar.getTime();
        String dateStr = new SimpleDateFormat("yyy-MM-dd").format(date);
        String weekdayStr = new SimpleDateFormat("EEEE").format(date);
        datetime.setText(dateStr +  " " + weekdayStr);

        datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        //绑定文本框
        inputcontent =(EditText)findViewById(R.id.Input_Content);

        //绑定保存按钮
        savebutton = (Button)findViewById(R.id.Save_Button);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                //保存点击事件
                Event event=new Event(3,"haha",finaldate,date,R.drawable.clock3);

                intent.putExtra("content",inputcontent.getText().toString());
                intent.putExtra("daycounts",event.getDateCounts());
                intent.putExtra("enddate",event.getendDate().getTime());
                setResult(1, intent);
                AddActivity.this.finish();
            }
        });
    }

    //目标日期选择组件
    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String[] dateStrArr = datetime.getText().toString().split(" ")[0].split("-");
            int year = Integer.parseInt(dateStrArr[0]);
            int month = Integer.parseInt(dateStrArr[1]) - 1;
            int day = Integer.parseInt(dateStrArr[2]);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String dateStr = "";
            String weekdayStr = "";
            try {
                dateStr = i + "-" + (i1 + 1) + "-" + i2;
                finaldate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                weekdayStr = new SimpleDateFormat("EEEE").format(finaldate);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            datetime.setText(dateStr + " " + weekdayStr);
        }
    }

    //返回键
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}


