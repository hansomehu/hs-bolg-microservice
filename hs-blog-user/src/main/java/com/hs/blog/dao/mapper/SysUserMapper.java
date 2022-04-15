package com.hs.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hs.blog.entity.Authority;
import com.hs.blog.entity.SysUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    @Select("SELECT * FROM hs_authority where id in (select auth_id from hs_user_auth where user_id=#{id})")
    Set<Authority> findAuthoritiesByUserId(Long id);
}
