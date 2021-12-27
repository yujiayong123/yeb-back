package com.xxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.server.mapper.EmployeeMapper;
import com.xxx.server.mapper.MailLogMapper;
import com.xxx.server.pojo.*;
import com.xxx.server.service.IEmployeeService;
import org.apache.tomcat.jni.Local;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yujiayong
 * @since 2021-11-08
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MailLogMapper mailLogMapper;

    /**
     * 获取所有员工(分页)
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    @Override
    public RespPageBean getEmployeeInfo(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        Page<Employee> page = new Page<>(currentPage,size);
        IPage<Employee> employeeIPage = employeeMapper.getEmployeeInfo(page,employee,beginDateScope);
        RespPageBean respPageBean = new RespPageBean(employeeIPage.getTotal(),employeeIPage.getRecords());
        return respPageBean;
    }

    /**
     * 获取工号
     * @return
     */
    @Override
    public RespBean getWorkID() {
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workID)"));
        String format = String.format("%08d", Integer.parseInt(maps.get(0).get("max(workID)").toString()) + 1);
        return RespBean.success(null,format);
    }

    /**
     * 添加员工
     * @param employee
     * @return
     */
    @Override
    public RespBean addDep(Employee employee) {
        // 处理合同期限，保留两位小数
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days/365.00)));
        if (1 == employeeMapper.insert(employee)) {
            Employee emp = employeeMapper.getEmp(employee.getId()).get(0);
            // 数据库记录发送的消息
            String msgId = UUID.randomUUID().toString();
            MailLog mailLog = new MailLog();
            mailLog.setMsgId(msgId);
            mailLog.setEid(employee.getId());
            mailLog.setStatus(0);
            mailLog.setCount(0);
            mailLog.setExchange(MailContants.MAIL_EXCHANGE_NAME);
            mailLog.setRouteKey(MailContants.MAIL_ROUTING_KEY_NAME);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailContants.MSG_TIMEOUT));
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());
            mailLogMapper.insert(mailLog);
            // 发送消息
            rabbitTemplate.convertAndSend(MailContants.MAIL_EXCHANGE_NAME,MailContants.MAIL_ROUTING_KEY_NAME,emp,new CorrelationData(msgId));
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    /**
     * 获取员工
     * @return
     */
    @Override
    public List<Employee> getEmp(Integer id) {
        return employeeMapper.getEmp(id);
    }

    /**
     * 分页展示员工账套
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public RespPageBean getAllSalary(Integer currentPage, Integer pageSize) {
        Page<Employee> page = new Page<>(currentPage,pageSize);
        IPage<Employee> employeeIPage = employeeMapper.getAllSalary(page);
        RespPageBean respPageBean = new RespPageBean(employeeIPage.getTotal(),employeeIPage.getRecords());
        return respPageBean;
    }
}
