package com.xxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.server.utils.AdminUtils;
import com.xxx.server.config.security.component.JwtTokenUtil;
import com.xxx.server.mapper.AdminMapper;
import com.xxx.server.mapper.AdminRoleMapper;
import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.AdminRole;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.pojo.Role;
import com.xxx.server.service.IAdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yujiayong
 * @since 2021-11-08
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public RespBean login(String username, String password,String code, HttpServletRequest request) {
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)) {
            return RespBean.error("验证码错误，请重新输入");
        }
        // 登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null == userDetails || passwordEncoder.matches(userDetails.getPassword(),password)) {
            return RespBean.error("用户名或密码错误");
        }
        if (!userDetails.isEnabled()) {
            return RespBean.error("用户不可用，请联系管理员");
        }
        // 更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("tokenHead",tokenHead);
        return RespBean.success("登陆成功",map);
    }

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUsername(String username) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username).eq("enabled",true);
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    /**
     * 通过用户id获取角色
     * @return
     */
    @Override
    public List<Role> getRoleWithAdminId(Integer id) {
        return adminMapper.getRoleWithAdminId(id);
    }

    /**
     * 获取所有操作员
     * @param keywords
     * @return
     */
    @Override
    public List<Admin> getAllAdmin(String keywords) {
        Integer id = AdminUtils.getCurrentAdmin().getId();
        return adminMapper.getAllAdmin(id,keywords);
    }

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    @Override
    @Transactional
    public RespBean updateAdminRoles(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId",adminId));

        Integer result = adminRoleMapper.addAdminRoles(adminId,rids);
        if (rids.length==result) {
            RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    /**
     * 更新用户密码
     * @param oldPass
     * @param newPass
     * @param adminId
     * @return
     */
    @Override
    public RespBean updateAdminPassword(String oldPass, String newPass, Integer adminId) {
        Admin admin = adminMapper.selectById(adminId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 判断旧密码是否正确
        if (encoder.matches(oldPass,admin.getPassword())) {
            admin.setPassword(encoder.encode(newPass));
            int result = adminMapper.updateById(admin);
            if (1==result) {
                return RespBean.success("密码修改成功");
            }
        }
        return RespBean.error("密码修改失败");
    }
}
