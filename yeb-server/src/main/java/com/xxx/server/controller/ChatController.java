package com.xxx.server.controller;

import com.xxx.server.pojo.Admin;
import com.xxx.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description: 在线聊天
 * <p>
 * User: 于家勇
 * Date: 2021-12-10
 * version: 1.0.0
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    private IAdminService adminService;

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/admin")
    public List<Admin> getAllAdmin(String keywords) {
        return adminService.getAllAdmin(keywords);
    }
}
