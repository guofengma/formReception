package web.dao;
;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.entity.User;

import javax.validation.OverridesAttribute;
import java.util.List;

/**
 * Created by qaa on 2017/10/25.
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User getByOpenIdAndName(String openId, String name);
    User getNameByOpenId(String openId);
}
