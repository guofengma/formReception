package web.schedule;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import web.entity.Records;
import web.service.RecordsService;
import web.utils.TimeUtil;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

/**
 * Created by Zhao Qing on 2018/1/8.
 */
@Configuration
@EnableScheduling
public class SendRecordsByMail {

    private static Logger logger = Logger.getLogger(SendRecordsByMail.class);

    @Autowired
    private RecordsService recordsService;

    /**
     * 定期发送邮件给自己上月的加班记录
     * 时间：每月2号上午8点
     * cron(秒 分 时 每月的第几天 月 每周的第几天)
     */
    @Scheduled(cron = "0 10 12 2 * ?")//每月第二天上午8点
//    @Scheduled(cron = "0 40 16 * * ?")//测试
    public void SendRecordsByMail() throws IOException{
        //从数据库读取上月部门加班记录，并生成excel文件
        List<Records> records = recordsService.getAllRecordsOfMonth(TimeUtil.lastMonth());
//        FileOutputStream path = new FileOutputStream("E:\\产品研发中心二部" + TimeUtil.lastMonth() + "月加班记录.xls");
        FileOutputStream path = new FileOutputStream("/data/records/产品研发中心二部" + TimeUtil.lastMonth() + "月加班记录.xls");
        excelGenerate(records, path);

        String to = "shenminyan@ctsi.com.cn";//收件人
        String copyto = "walkingzq@163.com";//抄送人
        String from = "walkingbupt@163.com";//发件人
        final String password = "bupt2016";//发件人邮箱密码
//        String host = "smtp.163.com";//邮件发送主机
        String host = "220.181.12.15";//邮件发送主机

        Properties properties = System.getProperties();//获取系统属性
        properties.setProperty("mail.smtp.host", host);//设置邮件服务器
        properties.setProperty("mail.transport.protocol", "smtp");//协议设置
        properties.setProperty("mail.smtp.port", "465");//端口设置
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        properties.setProperty("mail.smtp.auth","true");//

        Session session = Session.getDefaultInstance(properties);//获取默认session对象
        session.setDebug(true);
//        final String user = "walkingbupt@163.com";
//        final String secret = "bupt2016";
//        Session session = Session.getDefaultInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(user,secret);
//            }
//        });


        try{
            MimeMessage message = new MimeMessage(session);//创建默认的MimeMessage对象
            message.setFrom(new InternetAddress(from));
            message.setSubject("产品研发中心二部" + TimeUtil.lastMonth() + "月加班记录");//邮件主题
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));//收件人设置
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(copyto));//抄送人设置

            MimeMultipart msgMultipart = new MimeMultipart("mixed");//MINE消息体
            message.setContent(msgMultipart);

            MimeBodyPart content = new MimeBodyPart();//正文
            MimeBodyPart attach = new MimeBodyPart();//附件
            msgMultipart.addBodyPart(content);//添加正文
            msgMultipart.addBodyPart(attach);//添加附件

            //正文内容
            content.setText("沈经理：\n\n    您好，产品研发中心二部" + TimeUtil.lastMonth() + "月加班记录已上传至邮件附件，请查收。\n\n    备注：此邮件为系统自动发送，如有疑问请联系walkingzq@163.com。");

            DataSource ds1 = new FileDataSource(new File("/data/records/产品研发中心二部" + TimeUtil.lastMonth() + "月加班记录.xls"));
             //数据处理器
            DataHandler dh1 = new DataHandler(ds1 );
            //设置附件数据
            attach.setDataHandler(dh1);
            //设置附件文件名
            attach.setFileName(MimeUtility.encodeText("产品研发中心二部" + TimeUtil.lastMonth() + "月加班记录.xls"));

            Transport transport = session.getTransport();//获取Transport对象
            transport.connect(from, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            logger.info("邮件发送成功！");

        }catch (MessagingException exc){
            logger.error("邮件发送失败！");
            logger.error(exc.getMessage());
        }
    }

    /**
     * 将加班记录生成excel并输出至指定路径
     * @param records
     * @param outputStream
     * @throws IOException
     */
    public static void excelGenerate(List<Records> records, OutputStream outputStream){
        String[] titles = {"部门", "姓名", "加班原因", "加班时长（小时）", "加班日期", "加班地点"};

        HSSFWorkbook workbook = new HSSFWorkbook();//创建excel文档
        HSSFSheet sheet = workbook.createSheet("产品研发二部" + TimeUtil.lastMonth() + "月加班记录");

        HSSFFont font = workbook.createFont();//字体样式
        font.setFontName("宋体");

        HSSFCellStyle cellStyle = workbook.createCellStyle();//单元格样式
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平对齐方式
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直对齐方式
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);//边框颜色
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setFont(font);//字体

        //标题
        HSSFRow title = sheet.createRow(0);//创建第一行
        title.setHeight((short) 500);//行高度
        for (int i = 0; i< titles.length; i++){
            HSSFCell cell = title.createCell(i);//创建一个单元格
            cell.setCellStyle(cellStyle);
            cell.setCellValue(titles[i]);
        }

        //内容
        for (int i=0;i<records.size();i++){
            HSSFRow row = sheet.createRow(i+1);

            HSSFCell cell = row.createCell(0);//创建单元格
            cell.setCellStyle(cellStyle);
            cell.setCellValue(records.get(i).getDepartment());

            cell = row.createCell(1);//创建单元格
            cell.setCellStyle(cellStyle);
            cell.setCellValue(records.get(i).getName());

            cell = row.createCell(2);//创建单元格
            cell.setCellStyle(cellStyle);
            cell.setCellValue(records.get(i).getReason());

            cell = row.createCell(3);//创建单元格
            cell.setCellStyle(cellStyle);
            cell.setCellValue(records.get(i).getDuration());

            cell = row.createCell(4);//创建单元格
            cell.setCellStyle(cellStyle);
            cell.setCellValue(records.get(i).getDate());

            cell = row.createCell(5);//创建单元格
            cell.setCellStyle(cellStyle);
            cell.setCellValue(records.get(i).getPlace());
        }

        sheet.autoSizeColumn((short)0); //调整第一列宽度
        sheet.autoSizeColumn((short)1); //调整第二列宽度
        sheet.autoSizeColumn((short)2); //调整第三列宽度
        sheet.autoSizeColumn((short)3); //调整第四列宽度
        sheet.autoSizeColumn((short)4); //调整第五列宽度
        sheet.autoSizeColumn((short)5); //调整第六列宽度

        try {
            workbook.write(outputStream);//输出excel文档
            logger.info("excel file generating succeed!");
        }catch (IOException exc){
            logger.error("Failed to generate excel file.");
            logger.error(exc.getMessage());
        }
    }

}
