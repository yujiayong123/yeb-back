package com.xxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yujiayong
 * @since 2021-11-08
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 通过用户id获取菜单列表
     * @param id
     * @return
     */
    List<Menu> getMenusByAdminId(Integer id);

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
