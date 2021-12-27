package com.xxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.server.pojo.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yujiayong
 * @since 2021-11-08
 */
public interface DepartmentMapper extends BaseMapper<Department> {
    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDepartments(Integer parentId);

    /**
     * 添加部门
     * @param department
     * @return
     */
    void addDep(Department department);

    /**
     * 删除部门
     * @param department
     * @return
     */
    void deleteDep(Department department);
}
