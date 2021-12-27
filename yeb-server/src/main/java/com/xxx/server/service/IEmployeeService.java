package com.xxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.server.pojo.Employee;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.pojo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yujiayong
 * @since 2021-11-08
 */
public interface IEmployeeService extends IService<Employee> {
    /**
     * 获取所有员工(分页)
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    RespPageBean getEmployeeInfo(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    /**
     * 获取工号
     * @return
     */
    RespBean getWorkID();

    /**
     * 添加员工
     * @param employee
     * @return
     */
    RespBean addDep(Employee employee);

    /**
     * 获取员工
     * @return
     */
    List<Employee> getEmp(Integer id);

    /**
     * 分页展示员工账套
     * @param currentPage
     * @param pageSize
     * @return
     */
    RespPageBean getAllSalary(Integer currentPage, Integer pageSize);
}
