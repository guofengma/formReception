package web.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import web.controller.http.HttpRequest;


/**
 * Created by qaa on 2017/9/9.
 */
public class FormReceptionControllerTest {
    private static String baseUrl = "http://localhost:8080";

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
        String url = baseUrl + "/formSubmitting";
        String jsonData = "{\"" + "name\":\"赵庆\",\"reason\":\"微信小程序制作\",\"duration\":\"3\",\"place\":\"公司\"}";
        System.out.println(jsonData);
        String response = HttpRequest.sendPost(url,jsonData);
        System.out.println(response);
    }

}