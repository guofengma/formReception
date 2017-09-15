package web.entity;

import jdk.nashorn.internal.parser.JSONParser;

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

    public Records(){
    }

    public Records(String department, String name, String reason,
                   float duration, String date, String place){
        this.department = department;
        this.name = name;
        this.reason = reason;
        this.duration = duration;
        this.date = date;
        this.place = place;
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
                '}';
    }
}
