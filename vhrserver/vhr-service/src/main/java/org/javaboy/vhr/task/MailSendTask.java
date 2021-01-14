package org.javaboy.vhr.task;

import org.javaboy.vhr.bean.Employee;
import org.javaboy.vhr.bean.MailConstants;
import org.javaboy.vhr.bean.MailSendLog;
import org.javaboy.vhr.service.EmployeeService;
import org.javaboy.vhr.service.MailSendLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * @Classname MailSendTask
 * @Description TODO
 * @Date 2021/1/8 9:28
 * @Created by Jieqiyue
 */
@Component
public class MailSendTask {
    @Autowired
    MailSendLogService mailSendLogService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    EmployeeService employeeService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void mailResendTask(){
        List<MailSendLog> logs = mailSendLogService.getMailSendLogsByStatus();
        logs.forEach(mailSendLog -> {
            if (mailSendLog.getCount() > 3){
                mailSendLogService.updateMailSendLogStatus(mailSendLog.getMsgId(),2);
            }else{
                mailSendLogService.updateCount(mailSendLog.getMsgId(),new Date());
                Employee emp = employeeService.getEmployeeById(mailSendLog.getEmpId());
                rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,
                        MailConstants.MAIL_ROUTING_KEY_NAME,
                        emp,
                        new CorrelationData(mailSendLog.getMsgId()));
            }
        });
    }

}
