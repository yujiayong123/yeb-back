package com.xxx.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxx.server.pojo.Menu;
import com.xxx.server.pojo.MenuRole;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.pojo.Role;
import com.xxx.server.service.IMenuRoleService;
import com.xxx.server.service.IMenuService;
import com.xxx.server.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.math3.analysis.solvers.RiddersSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IMenuRoleService menuRoleService;

    @ApiOperation(value = "获取所有角色信息")
    @GetMapping("/")
    public List<Role> getAllPositions() {
        return roleService.list();
    }

    @ApiOperation(value = "添加角色信息")
    @PostMapping("/")
    public RespBean addRole(@RequestBody Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_"+role.getName());
        }
        if (roleService.save(role)) {
            return RespBean.success("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @ApiOperation(value = "更新角色信息")
    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Role role) {
        if (roleService.updateById(role)) {
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败！");
    }

    @ApiOperation(value = "删除角色信息")
    @DeleteMapping("/role/{id}")
    public RespBean deletePosition(@PathVariable Integer id) {
        if (roleService.removeById(id)) {
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    @ApiOperation(value = "批量删除角色信息")
    @DeleteMapping("/")
    public RespBean deletePositionByIds(Integer[] ids) {
        if (roleService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    @ApiOperation(value = "获取所有菜单")
    @GetMapping(value = "/getAllMenus")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @ApiOperation(value = "根据角色id查询菜单id")
    @GetMapping(value = "/mid/{rid}")
    public List<Integer> getMidByRid(Integer rid) {
        LambdaQueryWrapper<MenuRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MenuRole::getRid, rid);
        return menuRoleService.list(queryWrapper).stream().map(MenuRole::getMid).collect(Collectors.toList());
    }

    @ApiOperation(value = "更新角色菜单")
    @GetMapping(value = "/updateMenuRole")
    public RespBean updateMenuRole(Integer rid,Integer[] mids) {
        return menuRoleService.updateMenuRole(rid,mids);
    }
}
