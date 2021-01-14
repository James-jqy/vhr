package org.javaboy.vhr.mapper;

import org.apache.ibatis.annotations.Param;
import org.javaboy.vhr.bean.MailSendLog;
import org.springframework.security.core.parameters.P;

import java.util.Date;
import java.util.List;

/**
 * @Classname MailSendLogMapper
 * @Description TODO
 * @Date 2021/1/7 22:04
 * @Created by Jieqiyue
 */
public interface MailSendLogMapper {
    void updateMailSendLogStatus(@Param("msgId") String msgId, @Param("status") int status);

    void insert(MailSendLog msdLog);

    List<MailSendLog> getMailSendLogsByStatus();

    Integer updateCount(@Param("msgId") String msgId,@Param("date") Date date);
}
