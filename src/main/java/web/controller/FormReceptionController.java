package web.controller;

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

    @RequestMapping(value = "/hello")
    @ResponseBody
    public String Hello(){return "hello form!";}

    @RequestMapping(value = "/formSubmitting")
    @ResponseBody
    public String FormReceive(@RequestBody RequestData requestData){
        return "";
    }
}
