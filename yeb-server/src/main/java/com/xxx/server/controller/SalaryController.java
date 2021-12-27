package com.xxx.server.controller;


import com.xxx.server.pojo.RespBean;
import com.xxx.server.pojo.Salary;
import com.xxx.server.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yujiayong
 * @since 2021-11-08
 */
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {

    @Autowired
    private ISalaryService salaryService;

    @ApiOperation(value = "获取所有工资账套")
    @GetMapping(value = "/")
    public List<Salary> getAllSalary() {
        return salaryService.list();
    }

    @ApiOperation(value = "添加工资账套")
    @PostMapping(value = "/addSalary")
    public RespBean add(@RequestBody Salary salary) {
        salary.setCreateDate(LocalDateTime.now());
        if (salaryService.save(salary)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "删除工资账套")
    @DeleteMapping(value = "/{id}")
    public RespBean delete(@PathVariable("id") Integer id) {
        if (salaryService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "更新工资账套")
    @PutMapping(value = "/updateSalary")
    public RespBean update(@RequestBody Salary salary) {
        if (salaryService.updateById(salary)) {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

}
