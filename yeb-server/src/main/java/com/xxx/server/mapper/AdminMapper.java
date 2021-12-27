package com.xxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yujiayong
 * @since 2021-11-08
 */
public interface AdminMapper extends BaseMapper<Admin> {

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
    List<Admin> getAllAdmin(@Param("id") Integer id, String keywords);
}
