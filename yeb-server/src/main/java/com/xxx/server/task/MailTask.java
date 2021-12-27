package com.xxx.server.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xxx.server.pojo.Employee;
import com.xxx.server.pojo.MailContants;
import com.xxx.server.pojo.MailLog;
import com.xxx.server.service.IEmployeeService;
import com.xxx.server.service.IMailLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description: 邮件发送定时任务
 * <p>
 * User: 于家勇
 * Date: 2021-12-02
 * version: 1.0.0
 */
@Component
public class MailTask {

    @Autowired
    private IMailLogService mailLogService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask() {
        List<MailLog> list = mailLogService.list(new QueryWrapper<MailLog>().eq("status", 1).lt("tryTime", LocalDateTime.now()));
        list.forEach(mailLog -> {
            if (mailLog.getCount()>=3) {
                mailLogService.update(new UpdateWrapper<MailLog>().set("status",2).eq("msgId",mailLog.getMsgId()));
            }
            mailLogService.update(new UpdateWrapper<MailLog>().set("count",mailLog.getCount()+1)
                    .set("updateTime",LocalDateTime.now())
                    .set("tryTime",LocalDateTime.now().plusMinutes(MailContants.MSG_TIMEOUT)).eq("msgId",mailLog.getMsgId()));
            Employee emp = employeeService.getEmp(mailLog.getEid()).get(0);
            // 发送消息
            rabbitTemplate.convertAndSend(MailContants.MAIL_EXCHANGE_NAME,MailContants.MAIL_ROUTING_KEY_NAME,emp,new CorrelationData(mailLog.getMsgId()));
        });
    }
}
