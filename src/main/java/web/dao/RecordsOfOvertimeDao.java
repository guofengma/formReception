package web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.entity.RecordsOfOvertime;

/**
 * Created by qaa on 2017/9/11.
 */
@Repository
public interface RecordsOfOvertimeDao extends JpaRepository<RecordsOfOvertime, Integer>{
}
