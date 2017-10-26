package web.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by qaa on 2017/10/25.
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private int id;

    private String openId;

    private String name;

    public User(){}

    public User(String openId, String name) {
        this.openId = openId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
