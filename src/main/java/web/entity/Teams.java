package web.entity;

import javax.persistence.*;

/**
 * Created by Zhao Qing on 2018/1/8.
 */
@Entity
@Table(name = "teams")
public class Teams {
    @Id
    @GeneratedValue
    private long id;

    @Column
    public String team;

    @Column
    public String name;

    public Teams() {
    }

    @Override
    public String toString() {
        return "Teams{" +
                "id=" + id +
                ", team='" + team + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
