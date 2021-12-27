package com.xxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.server.pojo.MenuRole;
import com.xxx.server.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yujiayong
 * @since 2021-11-08
 */
public interface IMenuRoleService extends IService<MenuRole> {
    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
