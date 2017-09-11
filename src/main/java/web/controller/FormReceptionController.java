package web.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.common.RequestData;

/**
 * Created by qaa on 2017/9/8.
 */
@Controller
public class FormReceptionController {
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
        return "success!";
    }
}
