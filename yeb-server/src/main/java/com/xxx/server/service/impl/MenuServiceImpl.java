package com.xxx.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.server.mapper.MenuMapper;
import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.Menu;
import com.xxx.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yujiayong
 * @since 2021-11-08
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 通过用户id获取菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenusByAdminId() {
        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = admin.getId();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 从redis中那数据
        List<Menu> list = (List<Menu>) valueOperations.get("menu_" + id);
        if (CollectionUtils.isEmpty(list)) {
            // 如果为空，从数据库中去
            list = menuMapper.getMenusByAdminId(id);
            // 将数据放到redis中
            valueOperations.set("menu_"+id,list);
        }
        return list;
    }

    /**
     * 通过角色获取菜单
     * @return
     */
    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }

    /**
     * 获取所有菜单
     * @return
     */
    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}
