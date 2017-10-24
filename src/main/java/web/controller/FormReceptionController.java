package web.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.common.RequestData;
import web.constant.DURATION;
import web.constant.NAME;
import web.entity.Records;
import web.dao.RecordsDao;
import web.constant.CODE;
import web.service.OnLoginService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by qaa on 2017/9/8.
 */
@Controller
public class FormReceptionController {
    @Autowired
    private RecordsDao recordsDao;

    @Autowired
    private OnLoginService onLoginService;

    private static Logger logger = Logger.getLogger(FormReceptionController.class);

    @RequestMapping(value = "/hello")
    @ResponseBody
    public String hello(){
        logger.info("hello界面");
        return "hello form!";
    }

    //在用户登录时使用登录凭证 code 获取 session_key 和 openid
    @RequestMapping(value = "/onLogin")
    @ResponseBody
    public String onLogin(@RequestBody RequestData requestData) throws Exception{
        String localSessionKey = onLoginService.onLogin(requestData.getCode());
        return localSessionKey;
    }

    //接收表单信息并存入mysql
    @RequestMapping(value = "/formSubmitting")
    @ResponseBody
    public int formReceive(@RequestBody RequestData requestData){
        logger.info("收到的表单数据：" + requestData.toString());

        //参数检查
        try{
            //检查所给参数是否空缺，空缺则返回相应的错误
            Field[] fields = requestData.getClass().getDeclaredFields();
            for (int i=0;i<fields.length;i++){
                Field f = fields[i];
                f.setAccessible(true);
                Method m = requestData.getClass().getMethod("get" + f.getName().substring(0,1).toUpperCase() + f.getName().substring(1));
                String value = (String) m.invoke(requestData);
                if (StringUtils.isBlank(value)){
                    logger.error(f.getName() + " is null.");
                    return CODE.CODEMAP.get(f.getName());
                }
            }
            //核查duration格式，正确的格式为0.5-23.5的数字（以0.5为间隔）
            Pattern duration = Pattern.compile("[1]?[0-9]([.][5])?|[2]?[0-3]([.][5])?");
            if (!duration.matcher(requestData.getDuration()).matches()){
                logger.error("duration格式错误，\"duration\":\"" + requestData.getDuration() + "\"");
                return CODE.DURATION_ERROR;
            }
        }catch (Exception exc){
            logger.error("参数检查出错：" + exc.getMessage());
            return CODE.SYSTEM_ERROR;
        }


        try {
            //获取传入参数中各个字段的值并赋给一个Records对象
            String department = requestData.getDepartment();//部门
            int nameIndex = Integer.parseInt(requestData.getName());//姓名
            String name = NAME.NAMES[nameIndex];
            String reason = requestData.getReason();//加班缘由
            int durationIndex = Integer.parseInt(requestData.getDuration());//加班时长
            float duration = Float.parseFloat(DURATION.DURATIONS[durationIndex]);
            String date = requestData.getDate();//加班日期
            String place = requestData.getPlace();//加班地点
            Records records = new Records(department, name, reason, duration, date, place);

            /*
            *根据name和date查询数据库中已有记录
            *如果数据库中已有记录，则执行更新操作；否则，执行新增操作。
             */
            try {
                Records record = recordsDao.findByNameAndDate(name,date);
                if(record!=null){
                    logger.info("数据库中已有记录：" + record.toString());
                    records.setId(record.getId());
                }
            }catch (Exception exc){
                logger.error("sql select error: " + exc.getMessage());
                return CODE.SYSTEM_ERROR;
            }
            recordsDao.save(records);//向数据库插入一条数据
        }catch (Exception exc){
            logger.error("sql save error:" + exc.getMessage());
            return CODE.SYSTEM_ERROR;
        }
        return CODE.SUCCESS;
    }


}
