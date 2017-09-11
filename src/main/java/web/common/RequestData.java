package web.common;

/**
 * Created by qaa on 2017/9/8.
 * @author zhaoqing
 * 接收的请求参数类
 */
public class RequestData {
    private String name;//姓名
    private String reason;//加班缘由
    private String duration;//加班时长
    private String place;//加班地点

    public RequestData(){}

    public RequestData(String name, String reason, String duration, String place){
        this.name=name;
        this.reason=reason;
        this.duration=duration;
        this.place=place;
    }

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
                "name:" + this.name + "," +
                "reason:" + this.reason + "," +
                "duration:" + this.duration + "," +
                "place:" + this.place +
                "}";
    }
}
