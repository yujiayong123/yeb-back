package com.xxx.server.controller;

import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Description: 个人中心
 * <p>
 * User: 于家勇
 * Date: 2021-12-12
 * version: 1.0.0
 */
@RestController
public class AdminInfoController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "更新当前用户信息")
    @PutMapping(value = "/admin/info")
    public RespBean updateInfo(@RequestBody Admin admin, Authentication authentication) {
        if (adminService.updateById(admin)) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin,null,authentication.getAuthorities()));
            return RespBean.success("更新成功");
        }
        return RespBean.success("更新失败");
    }

    @ApiOperation(value = "更新用户密码")
    @PutMapping(value = "/admin/updatePassword")
    public RespBean updatePassword(@RequestBody Map<String,Object> info) {
        String oldPass = (String) info.get("oldPass");
        String newPass = (String) info.get("newPass");
        Integer adminId = (Integer) info.get("adminId");
        return adminService.updateAdminPassword(oldPass,newPass,adminId);
    }


}
