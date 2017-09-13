package web.entity;

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

}
