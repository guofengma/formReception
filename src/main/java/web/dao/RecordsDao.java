package web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.entity.Records;
import web.entity.Teams;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by qaa on 2017/9/11.
 * @author zhaoqing
 */
@Repository
public interface RecordsDao extends JpaRepository<Records, Long>{
    Records findByNameAndDate(String name, String date);//通过name和date查询records表中的记录
    Records findByOpenIdAndDate(String openId, String date);
    List<Records> findByOpenIdAndDateLike(String openId, String date);//查询某个微信号某个月提交的记录
//    void deleteByOpenIdAndDate(Long id);//删除某个微信号某个月提交的记录

//    @Query("select r from Records r join Teams t where r.name = t.name")
//    List<Records> getRecordsByName1();

    @Query(value = "select r.id,r.department,r.name,r.reason,r.duration,r.date,r.place,r.open_id,r.submit_time from records r join teams t where r.name = t.name and r.date like ?1",nativeQuery = true)
    List<Records> getDepartmentRecordsByMonth(String month);
}
