package web.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.UserDao;
import web.entity.User;

/**
 * Created by qaa on 2017/10/25.
 */
@Service("UserService")
public class UserService {
    private Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    /**
     * 更新user表
     * 检查user表中有没有对应的openid和name，没有则添加新纪录；
     * @param openId
     * @param name
     */
    public void updateUser(String openId, String name){
        String curMethod = "updateUser";
        User userFrom = new User(openId,name);
        User user = null;
        try {
            user =  userDao.getByOpenIdAndName(openId, name);
            if (null == user){
                logger.info("新用户>>openid:" + openId + ",name:" + name);
                userDao.save(userFrom);
            }else {
                logger.info("用户已存在");
                logger.info("[from mysql] openid:" + user.getOpenId() + ".name:" + user.getName());
            }
        }catch (Exception exc){
            logger.error(curMethod + "sql exception:" + exc.getMessage());
        }
    }

    public String getName(String openId){
        String curMethod = "getName";
        try {
            User user = userDao.getNameByOpenId(openId);
            return user.getName();
        }catch (Exception exc){
            logger.error(curMethod + "sql Exception:" + exc.getMessage());
            return null;
        }
    }
}
