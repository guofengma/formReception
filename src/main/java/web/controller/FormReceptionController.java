package web.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import web.common.LoginData;
import web.common.NameRequestData;
import web.common.OverTimeRecord;
import web.common.SessionKey;
import web.constant.DURATION;
import web.entity.Records;
import web.constant.CODE;
import web.schedule.SendRecordsByMail;
import web.service.OnLoginService;
import web.service.RecordsService;
import web.service.UserService;
import web.utils.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by qaa on 2017/9/8.
 */
@Controller
public class FormReceptionController {
    @Autowired
    private RecordsService recordsService;

    @Autowired
    private OnLoginService onLoginService;

    @Autowired
    private UserService userService;

    private static Logger logger = Logger.getLogger(FormReceptionController.class);

    @RequestMapping(value = "/hello")
    @ResponseBody
    public String hello(){
        logger.info("hello界面");
        return "hello form!";
    }

    /**
     * 某月部门内所有成员的加班记录
     * @param month
     * @return
     */
    @RequestMapping(value = "/getAllRecordsOfMonth")
    @ResponseBody
    public List<Records> getAllRecordsOfMonth(@RequestParam String month) throws Exception{
        FileOutputStream path = new FileOutputStream("E:\\产品研发中心二部" + TimeUtil.lastMonth() + "月加班记录.xls");
        SendRecordsByMail.excelGenerate(recordsService.getAllRecordsOfMonth(month), path);
        return recordsService.getAllRecordsOfMonth(month);
    }


    /**
     * 删除某微信号某个日期的加班记录
     * @param date
     * @param request
     * @return 0(删除成功);1(删除失败)
     */
    @RequestMapping(value = "/deleteRecord")
    @ResponseBody
    public String deleteRecord(@RequestParam String date, HttpServletRequest request){
        String sessionKey = request.getHeader("Session-Key");
        logger.debug("Session-key:" + sessionKey);
        LoginData userData = SessionKey.SessionsMap.get(sessionKey);
        logger.info("用户信息：" + userData);
        String openId = userData.getOpenId();
        try {
            Records records = recordsService.findByOpenIdAndDate(openId, date);
            if (records != null){
                recordsService.delete(records.getId());
                logger.info("已删除记录:"+ records);
            }
            return "0";//删除成功，返回"0"
        }catch (Exception exc){
            logger.error("删除记录出错,open_id:" + openId + ",date:" + date);
            logger.error("报错信息：" + exc.getMessage());
            return "1";//删除失败,返回"1"
        }
    }

    /**
     * 从服务器同步某月某用户提交的加班记录
     * @param date
     * @return
     */
    @RequestMapping(value = "/download")
    @ResponseBody
    public List<Records> getAllRecords(@RequestParam String date, HttpServletRequest request) {
        String sessionKey = request.getHeader("Session-Key");
        logger.debug("Session-key:" + sessionKey);
        LoginData userData = SessionKey.SessionsMap.get(sessionKey);
        logger.info("用户信息：" + userData);
        String openId = userData.getOpenId();
        return recordsService.findByOpenIdAndDateLike(openId, date.substring(0,8) + "%");
    }


    //在用户登录时使用登录凭证 requestdata 获取 session_key 和 openid
    @RequestMapping(value = "/onLogin")
    @ResponseBody
    public String onLogin(@RequestBody String requestdata) throws Exception{
        logger.info("++++++++++++++++++++++++++++用户登录+++++++++++++++++++++++++++++++");
        logger.info("requestdata:" + requestdata);
        JSONObject jsonObject = new JSONObject(requestdata);
        String code = (String) jsonObject.get("code");
        String sessionKey = onLoginService.onLogin(code);
        return sessionKey;
    }

    //查询已绑定的姓名
    @RequestMapping(value = "/getName")
    @ResponseBody
    public String getName(@RequestBody String sessionKey) throws Exception{
        String curMethod = "getName";
        logger.info(curMethod + ":" + sessionKey);
        try {
            logger.info(new JSONObject(sessionKey).get("sessionKey"));
            LoginData userData = SessionKey.SessionsMap.get(new JSONObject(sessionKey).get("sessionKey"));
            logger.info("用户信息：" + userData);
            return userService.getName(userData.getOpenId());
        }catch (Exception exc){
            logger.error(curMethod + " Excption: " + exc.getMessage());
            return "";
        }
    }

    //openid与姓名绑定
    @RequestMapping(value = "/setName")
    @ResponseBody
    public int setName(@RequestBody NameRequestData setNameRequestData) throws Exception{
        String curMethod = "setName";
        logger.info(curMethod  +  ": " + setNameRequestData);
        try {
            LoginData userData = SessionKey.SessionsMap.get(setNameRequestData.getLocalSessionKey());
            userService.updateUser(userData.getOpenId(),setNameRequestData.getName());
            return CODE.SUCCESS;
        }catch (Exception exc){
            logger.error(curMethod + " excption:" + exc.getMessage());
            return CODE.SYSTEM_ERROR;
        }

    }

    //修改姓名
    @RequestMapping(value = "/modifyName")
    @ResponseBody
    public int modifyName(@RequestBody NameRequestData nameRequestData){
        String curMethod = "/modifyName";
        logger.info(curMethod + "传入参数：" + nameRequestData);
        try {
            LoginData userData = SessionKey.SessionsMap.get(nameRequestData.getLocalSessionKey());
            return userService.modifyName(userData.getOpenId(),nameRequestData.getName());
        }catch (Exception exc){
            logger.error(curMethod + " excption:" + exc.getMessage());
            return CODE.SYSTEM_ERROR;
        }
    }

    //接收表单信息并存入mysql
    @RequestMapping(value = "/formSubmitting")
    @ResponseBody
    public int formReceive(@RequestBody OverTimeRecord overTimeRecord, HttpServletRequest request){
        logger.info("收到的表单数据：" + overTimeRecord.toString());

        String sessionKey = request.getHeader("Session-Key");
        logger.info("Session-key:" + sessionKey);
        LoginData userData = SessionKey.SessionsMap.get(sessionKey);
        logger.info("用户信息：" + userData);
        String openId = userData.getOpenId();

//        userService.updateUser(userData.getOpenId(),overTimeRecord.getName());


        //参数检查
        try{
            //检查所给参数是否空缺，空缺则返回相应的错误
            Field[] fields = overTimeRecord.getClass().getDeclaredFields();
            for (int i=0;i<fields.length;i++){
                Field f = fields[i];
                f.setAccessible(true);
                Method m = overTimeRecord.getClass().getMethod("get" + f.getName().substring(0,1).toUpperCase() + f.getName().substring(1));
                String value = (String) m.invoke(overTimeRecord);
                if (StringUtils.isBlank(value)){
                    logger.error(f.getName() + " is null.");
                    return CODE.CODEMAP.get(f.getName());
                }
            }
            //核查duration格式，正确的格式为0.5-23.5的数字（以0.5为间隔）
            Pattern duration = Pattern.compile("[1]?[0-9]([.][5])?|[2]?[0-3]([.][5])?");
            if (!duration.matcher(overTimeRecord.getDuration()).matches()){
                logger.error("duration格式错误，\"duration\":\"" + overTimeRecord.getDuration() + "\"");
                return CODE.DURATION_ERROR;
            }
        }catch (Exception exc){
            logger.error("参数检查出错：" + exc.getMessage());
            return CODE.SYSTEM_ERROR;
        }


        try {
            //获取传入参数中各个字段的值并赋给一个Records对象
            String department = overTimeRecord.getDepartment();//部门
            String name = overTimeRecord.getName();//姓名
            String reason = overTimeRecord.getReason();//加班缘由
            int durationIndex = Integer.parseInt(overTimeRecord.getDuration());//加班时长
            float duration = Float.parseFloat(DURATION.DURATIONS[durationIndex]);
            String date = overTimeRecord.getDate();//加班日期
            String place = overTimeRecord.getPlace();//加班地点
            Records records = new Records(department, name, reason, duration, date, place,openId);

            /*
            *根据openId和date查询数据库中已有记录
            *如果数据库中已有记录，则执行更新操作；否则，执行新增操作。
             */
            try {
//                Records record = recordsDao.findByNameAndDate(name,date);
                Records record = recordsService.findByOpenIdAndDate(openId,date);
                if(record!=null){
                    logger.info("数据库中已有记录：" + record.toString());
                    records.setId(record.getId());
                }
            }catch (Exception exc){
                logger.error("sql select error: " + exc.getMessage());
                return CODE.SYSTEM_ERROR;
            }
            recordsService.save(records);//向数据库插入一条数据
        }catch (Exception exc){
            logger.error("sql save error:" + exc.getMessage());
            return CODE.SYSTEM_ERROR;
        }
        return CODE.SUCCESS;
    }


}
