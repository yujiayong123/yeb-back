package com.xxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.server.pojo.Menu;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yujiayong
 * @since 2021-11-08
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 通过用户id获取菜单列表
     * @return
     */
    List<Menu> getMenusByAdminId();

    /**
     * 通过角色获取菜单列表
     * @return
     */
    List<Menu> getMenusWithRole();

    /**
     * 获取所有菜单
     * @return
     */
    List<Menu> getAllMenus();
}
