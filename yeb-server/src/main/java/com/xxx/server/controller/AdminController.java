package com.xxx.server.controller;


import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.pojo.Role;
import com.xxx.server.service.IAdminService;
import com.xxx.server.service.IRoleService;
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
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IRoleService roleService;

    /**
     * 获取所有操作员
     * @param keywords
     * @return
     */
    @ApiOperation(value = "获取所有操作员")
    @GetMapping(value = "/getAllAdmin")
    public List<Admin> getAllAdmin(String keywords) {
        return adminService.getAllAdmin(keywords);
    }

    /**
     * 更新操作员
     * @param admin
     * @return
     */
    @ApiOperation(value = "更新操作员")
    @PutMapping(value = "/updateAdmin")
    public RespBean updateAdmin(@RequestBody Admin admin) {
        if (adminService.updateById(admin)) {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    /**
     * 删除操作员
     * @param id
     * @return
     */
    @ApiOperation(value = "删除操作员")
    @DeleteMapping(value = "/deleteAdmin/{id}")
    public RespBean deleteAdmin(@PathVariable Integer id) {
        if (adminService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    /**
     * 获取操作员角色
     * @return
     */
    @ApiOperation(value = "获取操作员角色")
    @GetMapping(value = "/getAllRoles")
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    @ApiOperation(value = "更新操作员角色")
    @PutMapping(value = "/updateAdminRoles")
    public RespBean updateAdminRoles(Integer adminId,Integer[] rids) {
        return adminService.updateAdminRoles(adminId,rids);
    }
}
