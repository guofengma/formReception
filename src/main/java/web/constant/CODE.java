package web.constant;

import com.sun.org.apache.regexp.internal.RE;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qaa on 2017/9/12.
 * * @author zhaoqing
 * 返回codec常量
 */
public class CODE {
    public static final int SUCCESS = 0;//成功插入数据库
    public static final int DEPARTMENT_ERROR = -1;//部门填写有误
    public static final int DATE_ERROR = -2;//日期填写有误
    public static final int NAME_ERROR = -3;//姓名填写有误
    public static final int REASON_ERROR = -4;//加班缘由填写有误
    public static final int DURATION_ERROR = -5;//加班时长填写有误
    public static final int PLACE_ERROR = -6;//加班地点填写有误
    public static final int SYSTEM_ERROR = -7;//系统异常

    public static final Map<String, Integer> CODEMAP = new HashMap<String, Integer>();

    static {
        CODEMAP.put("department", DEPARTMENT_ERROR);
        CODEMAP.put("date", DATE_ERROR);
        CODEMAP.put("name", NAME_ERROR);
        CODEMAP.put("reason", REASON_ERROR);
        CODEMAP.put("duration", DURATION_ERROR);
        CODEMAP.put("place", PLACE_ERROR);
    }

}
