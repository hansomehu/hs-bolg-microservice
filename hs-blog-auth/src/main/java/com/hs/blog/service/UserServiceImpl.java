package com.hs.blog.service;

import com.hs.blog.constant.MessageConstant;
import com.hs.blog.entity.Authority;
import com.hs.blog.entity.SecurityUser;
import com.hs.blog.entity.SysUser;
import com.hs.blog.service.feign.UserServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


//@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private UserServiceFeign userServiceFeign;

    /**
     * 远程调用用户服务通过username获得Sysuser对象，将其中的一些属性选择性复制到SecurityUser对象中去
     *
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String clientId = httpServletRequest.getParameter("client_id");
        SysUser sysUser = userServiceFeign.findUserByUsername(s);
        // check username & password
        String password = sysUser.getPassword();
        if (sysUser == null | password != httpServletRequest.getParameter("password")) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }
        SecurityUser securityUser = new SecurityUser(sysUser, clientId);
        // remote call to query user's Authorities
        Set<Authority> authorities= userServiceFeign.findAuthoritiesByUserId(sysUser.getId());
        Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        authorities.forEach(authority ->{
            SimpleGrantedAuthority t = new SimpleGrantedAuthority(authority.getRole());
            simpleGrantedAuthorities.add(t);
        });
        securityUser.setAuthorities(simpleGrantedAuthorities);

        return securityUser;
    }
}
