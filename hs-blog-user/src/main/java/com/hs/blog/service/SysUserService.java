package com.hs.blog.service;

import com.hs.blog.entity.Authority;
import com.hs.blog.entity.SysUser;
import com.hs.blog.vo.Result;
import com.hs.blog.vo.UserVo;

import java.util.Set;


public interface SysUserService {

    UserVo findUserVoById(Long id);

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据账户查找用户
     * @param username
     * @return
     */
    SysUser findUserByUsername(String username);

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**
     * 根据用户id来查找用户的角色
     * @param id
     * @return
     */
    Set<Authority> findAuthoritiesByUserId(Long id);
}
