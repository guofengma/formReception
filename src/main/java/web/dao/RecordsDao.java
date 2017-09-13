package web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.entity.Records;

/**
 * Created by qaa on 2017/9/11.
 * @author zhaoqing
 */
@Repository
public interface RecordsDao extends JpaRepository<Records, Integer>{
}
