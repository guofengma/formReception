package web.controller;

import org.json.JSONObject;
import org.json.JSONString;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import web.controller.http.HttpRequest;


/**
 * Created by qaa on 2017/9/9.
 */
public class FormReceptionControllerTest {
    private static String baseUrl = "http://localhost:8080";
//    private static String baseUrl = "http://123.206.49.57/form-1.0.3";

    @Before
    public void setUp() throws Exception{
    }

    @After
    public void tearDown() throws Exception{
    }

    @Test
    public void hello() throws Exception {
        String url = baseUrl + "/hello";
        String response = HttpRequest.sendGet(url,"");
        System.out.println(response);
    }

    @Test
    public void formReceive() throws Exception {
//        String url = baseUrl + "/formSubmitting";
        String url = baseUrl + "/login/register3";
        String jsonData1 = "{\"date\":\"2017-10-26\",\"department\":\"计算机学院\",\"name\":\"周周\",\"reason\":\"小程序测试\",\"duration\":\"3\",\"place\":\"四川大学望江校区\"}";
        String jsonData = "{\"username\":\"zq\",\"password\":\"zzz\",\"email\":\"walkingzq@163.com\"}";
//        JSONObject jsonObject = new JSONObject(jsonData);
        System.out.println(jsonData);
        System.out.println(jsonData1);
        String response = HttpRequest.sendPost(url,jsonData);
        System.out.println(response);
    }

}