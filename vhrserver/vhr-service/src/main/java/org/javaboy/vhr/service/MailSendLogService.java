package org.javaboy.vhr.service;

import org.javaboy.vhr.bean.MailSendLog;
import org.javaboy.vhr.mapper.MailSendLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Classname MailSendLogService
 * @Description TODO
 * @Date 2021/1/7 21:38
 * @Created by Jieqiyue
 */
@Service
public class MailSendLogService {
    @Autowired
    MailSendLogMapper mailSendLogMapper;

    public void updateMailSendLogStatus(String msgId, int status) {
        mailSendLogMapper.updateMailSendLogStatus(msgId,status);
    }

    public void insert(MailSendLog msdLog) {
        mailSendLogMapper.insert(msdLog);
    }

    public List<MailSendLog> getMailSendLogsByStatus() {
        return mailSendLogMapper.getMailSendLogsByStatus();
    }

    public Integer updateCount(String msgId, Date date) {
        return mailSendLogMapper.updateCount(msgId, date);
    }
}
