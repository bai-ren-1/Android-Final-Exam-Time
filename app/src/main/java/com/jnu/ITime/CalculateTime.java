package com.jnu.ITime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalculateTime {

    //将date时间转string格式函数
    public static String dateToString(Date date) {
        return new SimpleDateFormat("yyy-MM-dd").format(date);
    }

    //计算间隔时间函数返回long型
    public static long getDateInterval(Date date) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        long duration = date.getTime() - currentDate.getTime();
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        return days;
    }

    //将string转为date类型
    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
    try {
        date = format.parse(str);
    } catch (ParseException e) {
        e.printStackTrace();
    }
        return date;
    }

    //将long型转换为string型
    public static String longTostring(long lo){
        Date date = new Date(lo);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }


}

