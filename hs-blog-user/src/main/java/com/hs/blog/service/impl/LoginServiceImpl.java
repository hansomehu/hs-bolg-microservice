package com.hs.blog.service.impl;

import com.hs.blog.entity.SysUser;
import com.hs.blog.service.LoginService;
import com.hs.blog.service.SysUserService;
import com.hs.blog.service.feign.AuthServiceFeign;
import com.hs.blog.utils.SnowFlakeUtil;
import com.hs.blog.vo.Result;
import com.hs.blog.vo.ErrorCode;
import com.hs.blog.vo.UserVo;
import com.hs.blog.vo.params.OAuthLoginParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result login(OAuthLoginParam loginParam) {

        /**
         * 前端：
         * 1. 远程调用oauth/token 接口，传入对应参数获取token
         * 2. 拿到token后前端传回给用户
         * 服务端：
         * 1. 根据账号密码查询用户Sysuser对象返回给前端
         *
         */
        SysUser sysUser = sysUserService.findUserByUsername(loginParam.getUsername());
        if (sysUser == null) return Result.fail(400, "user doesn't registered");

        // Keep in mind here we have to use Encoded password to compare with the one in DB
        if (sysUser.getPassword() != new BCryptPasswordEncoder().encode(loginParam.getPassword())) return Result.fail(400, "incorrect password");

        UserVo userVo = new UserVo();
        userVo.setId(sysUser.getId().toString());
        userVo.setAvatar(sysUser.getAvatar());
        userVo.setNickname(sysUser.getNickname());

        return Result.success(userVo);

    }

    @Override
    public Result register(OAuthLoginParam registerParam) {
        /**
         * 1. 将用户信息入数据库（密码加密）
         * 2. 将结果返回前端
         */
        String username = registerParam.getUsername();
        String password = registerParam.getPassword();
        String nickname = registerParam.getNickname();
        if (StringUtils.isBlank(username)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser =  sysUserService.findUserByUsername(username);
        if (sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),"账户已经被注册了");
        }
        sysUser = new SysUser();
        sysUser.setId(new SnowFlakeUtil(1,1,1).nextId());
        sysUser.setNickname(nickname);
        sysUser.setUsername(username);
        sysUser.setPassword(new BCryptPasswordEncoder().encode(password));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);

        UserVo userVo = new UserVo();
        userVo.setId(sysUser.getId().toString());
        userVo.setAvatar(sysUser.getAvatar());
        userVo.setNickname(sysUser.getNickname());

        return Result.success(userVo);
    }

}
