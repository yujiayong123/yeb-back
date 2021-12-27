package com.xxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.server.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yujiayong
 * @since 2021-11-08
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
