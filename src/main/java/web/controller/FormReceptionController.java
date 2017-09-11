package web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.common.RequestData;
import web.entity.RecordsOfOvertime;
import web.dao.RecordsOfOvertimeDao;

/**
 * Created by qaa on 2017/9/8.
 */
@Controller
public class FormReceptionController {
    @Autowired
    private RecordsOfOvertimeDao recordsOfOvertimeDao;

    private static Logger logger = Logger.getLogger(FormReceptionController.class);

    @RequestMapping(value = "/hello")
    @ResponseBody
    public String Hello(){
        logger.info("hello界面");
        return "hello form!";
    }

    @RequestMapping(value = "/formSubmitting")
    @ResponseBody
    public String FormReceive(@RequestBody RequestData requestData){
        logger.info("收到的表单数据：" + requestData.toString());
        String department = "";
        String name = requestData.getName();
        String reason = requestData.getReason();
        float duration = Float.parseFloat(requestData.getDuration());
        String date = "2017-09-11";
        String place = requestData.getPlace();
        RecordsOfOvertime recordsOfOvertime = new RecordsOfOvertime(department, name, reason, duration, date, place);
        recordsOfOvertimeDao.save(recordsOfOvertime);
        return "success!";
    }
}
