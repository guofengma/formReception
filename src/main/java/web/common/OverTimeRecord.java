package web.common;

/**
 * Created by qaa on 2017/9/8.
 * @author zhaoqing
 * 接收的请求参数类
 */
public class OverTimeRecord {
    private String date;//加班日期
    private String department;//部门
    private String name;//姓名
    private String reason;//加班缘由
    private String duration;//加班时长（单位：小时）
    private String place;//加班地点

    public OverTimeRecord(){}

    public OverTimeRecord(String date, String department, String name, String reason, String duration, String place){
        this.date=date;
        this.department=department;
        this.name=name;
        this.reason=reason;
        this.duration=duration;
        this.place=place;
    }

    public void setDate(String date){this.date=date;}
    public String getDate(){return this.date;}

    public void setDepartment(String department){this.department=department;}
    public String getDepartment(){return this.department;}

    public void setName(String name){this.name=name;}
    public String getName(){return this.name;}

    public void setReason(String reason){this.reason=reason;}
    public String getReason(){return this.reason;}

    public void setDuration(String duration){this.duration=duration;}
    public String getDuration(){return this.duration;}

    public void setPlace(String place){this.place=place;}
    public String getPlace(){return this.place;}


    @Override
    public String toString(){
        return "{" +
                "date:" + this.date + "," +
                "department:" + this.department + "," +
                "name:" + this.name + "," +
                "reason:" + this.reason + "," +
                "duration:" + this.duration + "," +
                "place:" + this.place +
                "}";
    }
}
