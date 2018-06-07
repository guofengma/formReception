package web.utils;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by Zhao Qing on 2018/1/8.
 */
public class TimeUtilTest {
    @Test
    public void lastMonth() throws Exception {
        System.out.println(TimeUtil.lastMonth());
    }

    @Test
    public void exceptionFa() throws IOException{
        exceptionSon();
    }

    @Test
    public void exceptionSon() throws FileNotFoundException{
        throw new FileNotFoundException("error");
    }

}