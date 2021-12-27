package com.xxx.server.controller;

import com.xxx.server.pojo.Department;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    /**
     * 获取所有部门
     * @return
     */
    @ApiOperation(value = "获取所有部门")
    @GetMapping(value = "/getAllDepartments")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    /**
     * 添加部门
     * @param department
     * @return
     */
    @ApiOperation(value = "添加部门")
    @PostMapping(value = "/addDep")
    public RespBean addDep(@RequestBody Department department) {
        return departmentService.addDep(department);
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @ApiOperation(value = "删除部门")
    @DeleteMapping(value = "/deleteDep/{id}")
    public RespBean deleteDep(@PathVariable Integer id) {
        return departmentService.deleteDep(id);
    }

}
