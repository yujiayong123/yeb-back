package com.xxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yujiayong
 * @since 2021-11-08
 */
public interface IAdminService extends IService<Admin> {
    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    RespBean login(String username, String password,String code, HttpServletRequest request);

    /**
     * 获取当前登录的用户信息
     * @param username
     * @return
     */
    Admin getAdminByUsername(String username);

    /**
     * 通过用户id获取角色
     * @return
     */
    List<Role> getRoleWithAdminId(Integer id);

    /**
     * 获取所有操作员
     * @param keywords
     * @return
     */
    List<Admin> getAllAdmin(String keywords);

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    RespBean updateAdminRoles(Integer adminId, Integer[] rids);

    /**
     * 更新用户密码
     * @param oldPass
     * @param newPass
     * @param adminId
     * @return
     */
    RespBean updateAdminPassword(String oldPass, String newPass, Integer adminId);
}
