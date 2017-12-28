package web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.entity.Records;

import java.util.List;

/**
 * Created by qaa on 2017/9/11.
 * @author zhaoqing
 */
@Repository
public interface RecordsDao extends JpaRepository<Records, Integer>{
    Records findByNameAndDate(String name, String date);//通过name和date查询records表中的记录
    Records findByOpenIdAndDate(String openId, String date);
    List<Records> findByOpenIdAndDateLike(String openId, String date);//查询某个微信号某个月提交的记录
}
