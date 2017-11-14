package web.utils;

import web.entity.Records;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by qaa on 2017/10/30.
 * 用于常见的时间操作
 */
public class TimeUtil {
    private static Calendar calendar = Calendar.getInstance();

    public static void main(String args[]){
//        System.out.println(calendar.getTime());
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
//        System.out.println(simpleDateFormat.format(calendar.getTime()));
//        System.out.println(TimeUtil.nowTime("YYYY-MM-dd HH:mm:ss"));
        Records records = new Records();
        System.out.println(records.getSubmitTime());
    }

    public static String nowTime(String dateFormat){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(calendar.getTime());
    }
}
