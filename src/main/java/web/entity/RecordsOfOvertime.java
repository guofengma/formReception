package web.entity;

import javax.persistence.*;

/**
 * Created by qaa on 2017/9/11.
 */
@Entity
@Table(name = "records")
public class RecordsOfOvertime {
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

    public RecordsOfOvertime(){
    }

    public RecordsOfOvertime(String department, String name, String reason,
                             float duration, String date, String place){
        this.department = department;
        this.name = name;
        this.reason = reason;
        this.duration = duration;
        this.date = date;
        this.place = place;
    }

}
