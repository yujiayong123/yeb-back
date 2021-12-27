package com.xxx.server.controller;

import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.AdminLoginParam;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;

    /**
     * 登录之后返回token
     * @param adminLoginParam
     * @param request
     * @return
     */
    @ApiOperation(value = "登录之后返回token")
    @PostMapping(value = "/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request) {
        return adminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCode(),request);
    }

    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping(value = "/admin/info")
    public Admin getAdminInfo(Principal principal) {
        if (null == principal) {
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUsername(username);
        admin.setPassword(null);
        admin.setRoles(adminService.getRoleWithAdminId(admin.getId()));
        return admin;
    }

    @ApiOperation(value = "退出登录")
    @PostMapping(value = "/logout")
    public RespBean logout() {
        return RespBean.success("注销成功");
    }

}
