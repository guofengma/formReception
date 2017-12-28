package web.entity;


import web.utils.TimeUtil;

import javax.persistence.*;

/**
 * Created by qaa on 2017/9/11.
 * @author zhaoqing
 * 对应数据库中的records表的实体类
 */
@Entity
@Table(name = "records")
public class Records {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private float duration;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String place;

    private String openId;

    private String submitTime;//提交时间

    public Records(){
    }

    public Records(String department, String name, String reason,
                   float duration, String date, String place, String openId){
        this.department = department;
        this.name = name;
        this.reason = reason;
        this.duration = duration;
        this.date = date;
        this.place = place;
        this.openId = openId;
        this.submitTime = TimeUtil.nowTime("YYYY-MM-dd HH:mm:ss");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    @Override
    public String toString() {
        return "Records{" +
                "id=" + id +
                ", department='" + department + '\'' +
                ", name='" + name + '\'' +
                ", reason='" + reason + '\'' +
                ", duration=" + duration +
                ", date='" + date + '\'' +
                ", place='" + place + '\'' +
                ", submitTime='" + submitTime + '\'' +
                '}';
    }

}
