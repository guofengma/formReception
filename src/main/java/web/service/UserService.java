package web.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.constant.CODE;
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
        User userForm = new User(openId,name);
        User user = null;
        try {
            user =  userDao.getByOpenIdAndName(openId, name);
            if (null == user){
                logger.info("新用户>>openid:" + openId + ",name:" + name);
                userDao.save(userForm);
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
            if (null == user){
                return null;
            }
            return user.getName();
        }catch (Exception exc){
            logger.error(curMethod + "sql Exception:" + exc.getMessage());
            return null;
        }
    }

    /**
     * 修改姓名
     * 如果传入的openId在数据库中有记录，则更新对应的name;否则，将传入的openId和name作为新纪录增添至user表
     * @param openId
     * @param name
     */
    public int modifyName(String openId, String name){
        String curMethod = "modifyName";

        User user = new User(openId,name);
        try {
            User userExist = userDao.getNameByOpenId(openId);
            if (null != userExist){
                user.setId(userExist.getId());
            }
            userDao.save(user);
            return CODE.SUCCESS;
        }catch (Exception exc){
            logger.error(curMethod + "sql excption: " + exc.getMessage());
            return CODE.SYSTEM_ERROR;
        }
    }
}
