package web.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by qaa on 2017/10/30.
 * 用于常见的时间操作
 */
public class TimeUtil {

    public static String lastMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);//上个月
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String nowTime(String dateFormat){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(calendar.getTime());
    }
}
