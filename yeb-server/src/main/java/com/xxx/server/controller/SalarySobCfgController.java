package com.xxx.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xxx.server.pojo.Employee;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.pojo.RespPageBean;
import com.xxx.server.pojo.Salary;
import com.xxx.server.service.IEmployeeService;
import com.xxx.server.service.ISalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description: 工资账套
 * <p>
 * User: 于家勇
 * Date: 2021-12-10
 * version: 1.0.0
 */
@RestController
@RequestMapping("/salary/sob/cfg")
public class SalarySobCfgController {

    @Autowired
    private ISalaryService salaryService;
    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "获取所有工资账套")
    @GetMapping(value = "/")
    public List<Salary> getAllSalary() {
        return salaryService.list();
    }

    @ApiOperation(value = "获取所有员工账套")
    @GetMapping(value = "/getAllSalary")
    public RespPageBean getAllSalary(@RequestParam(defaultValue = "1") Integer currentPage,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        return employeeService.getAllSalary(currentPage,pageSize);
    }

    @ApiOperation(value = "更新员工账套")
    @PutMapping(value = "/update")
    public RespBean update(Integer eid,Integer sid) {
        if (employeeService.update(new UpdateWrapper<Employee>().set("salaryId",sid).eq("id",eid))) {
            return RespBean.success("更新成功");
        }
        return RespBean.success("更新失败");
    }
}
