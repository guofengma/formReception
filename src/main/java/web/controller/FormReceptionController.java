package web.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.common.RequestData;
import web.entity.Records;
import web.dao.RecordsDao;
import web.constant.CODE;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Created by qaa on 2017/9/8.
 */
@Controller
public class FormReceptionController {
    @Autowired
    private RecordsDao recordsDao;

    private static Logger logger = Logger.getLogger(FormReceptionController.class);

    @RequestMapping(value = "/hello")
    @ResponseBody
    public String Hello(){
        logger.info("hello界面");
        return "hello form!";
    }

    //接收表单信息并更新或插入数据
    @RequestMapping(value = "/formSubmitting")
    @ResponseBody
    public int FormReceive(@RequestBody RequestData requestData){
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
//                logger.info(f.getName() + ": " + value);
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
            String department = requestData.getDepartment();
            String name = requestData.getName();
            String reason = requestData.getReason();
            float duration = Float.parseFloat(requestData.getDuration());
            String date = requestData.getDate();
            String place = requestData.getPlace();
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
