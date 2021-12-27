package com.xxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxx.server.pojo.Employee;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yujiayong
 * @since 2021-11-08
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 获取所有员工(分页)
     * @param page
     * @param employee
     * @param beginDateScope
     * @return
     */
    IPage<Employee> getEmployeeInfo(Page<Employee> page, @Param("employee") Employee employee, @Param("beginDateScope") LocalDate[] beginDateScope);

    /**
     * 获取员工
     * @return
     */
    List<Employee> getEmp(@Param("id") Integer id);

    /**
     * 分页展示员工账套
     * @param page
     * @return
     */
    IPage<Employee> getAllSalary(Page<Employee> page);
}
