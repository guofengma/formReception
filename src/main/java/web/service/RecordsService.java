package web.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.RecordsDao;
import web.entity.Records;

import java.util.List;

/**
 * Created by Zhao Qing on 2017/12/29.
 */
@Service("RecordsService")
public class RecordsService {
   private Logger logger = Logger.getLogger(RecordsService.class);

   @Autowired
   private RecordsDao recordsDao;

    /**
     * 根据openId和date查找记录
     * @param openId
     * @param date
     * @return
     */
   public Records findByOpenIdAndDate(String openId, String date){
       return recordsDao.findByOpenIdAndDate(openId, date);
   }

    /**
     * 根据主键删除记录
     * @param id
     */
   public void delete(Long id){
       recordsDao.delete(id);
   }

    /**
     * 保存一个实体到Records表
     * @param records
     */
   public void save(Records records){
       recordsDao.save(records);
   }

    /**
     * 根据openId和date查找当月该微信号的提交记录
     * @param openId
     * @param date
     * @return
     */
   public List<Records> findByOpenIdAndDateLike(String openId, String date){
       return recordsDao.findByOpenIdAndDateLike(openId, date.substring(0,8) + "%");
   }
}
