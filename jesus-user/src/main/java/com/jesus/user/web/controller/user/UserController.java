package com.jesus.user.web.controller.user;

import com.jesus.common.base.bind.annotation.LoginAccount;
import com.jesus.common.base.constant.GlobalConstant;
import com.jesus.common.base.redis.service.RedisService;
import com.jesus.common.response.Response;
import com.jesus.common.utils.CommonUtil;
import com.jesus.common.utils.encrypt.SecuritySHA1Utils;
import com.jesus.user.modules.user.service.UserService;
import com.jesus.user.web.controller.user.dto.UserDto;
import com.jesus.user.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@RestController
@RequestMapping("/user/v1/web")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Resource
    private RedisService redisService;

    @PostMapping("/register")
    public Response register(@RequestBody User user){
        if(CommonUtil.isEmpty(user.getMobile())){
            return Response.fail("手机号为必填的哦");
        }
        if(CommonUtil.isEmpty(user.getName())){
            return Response.fail("为了您的信息安全，请填写您的真实姓名哟");
        }
        if(CommonUtil.isEmpty(user.getUsername())){
            user.setUsername(user.getMobile());
        }
        if(CommonUtil.isEmpty(user.getPassword())){
            //如用户未设置密码  默认为手机号后六位
            String mobile = user.getMobile();
            user.setPassword(mobile.substring(mobile.length()-6));
        }
        if (CommonUtil.isNull(user.getRoleId())) {
            //默认为普通用户
            user.setRoleId(2l);
        }
        user.setCreateTime(new Date());
        user.setState(User.State.ENABLED);
        try {
            //获取随机盐值
            String salt = SecuritySHA1Utils.getRandomSalt();
            if(CommonUtil.isEmpty(salt) || salt.length() < 20){
                return Response.fail("程序开小差了，请重新尝试");
            }
            user.setSalt(salt);

            //密码加密
            String password = SecuritySHA1Utils.SHA1(user.getPassword(),salt);
            user.setPassword(password);
            userService.save(user);
            return Response.ok();
        } catch (Exception e) {
            log.error("用户注册异常：{}",e.getMessage());
            return Response.error("竟然注册失败了，刷新重试一下吧");
        }
    }

    @PostMapping("/getUserInfo")
    public Response getUserInfo(@RequestBody User user){
        try {
            if(CommonUtil.isNull(user.getUsername())){
                return Response.fail("用户名不能为空");
            }
            user = userService.findByUserName(user.getUsername());
            if(CommonUtil.isNull(user)){
                return Response.fail("查询失败");
            }
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(dto,user);
            return Response.ok(user);
        } catch (Exception e) {
            log.error("/getUserInfo 查询用户信息异常{}",e.getMessage());
        }

        return Response.fail("查询失败");
    }

    @RequestMapping("/getThisUser")
    public Response getThisUser(HttpServletRequest request){
        User user = null;
        try {
            user = (User) request.getAttribute(GlobalConstant.User.LOGIN_ACCOUNT);
            if(CommonUtil.isNull(user)){
                return Response.fail("获取失败");
            }
        } catch (Exception e) {
            log.error("查询当前用户异常{}",e.getMessage());
        }
        return Response.ok(user);
    }

    @PostMapping("/getAccountUser")
    public Response getAccountUser(@LoginAccount User user){
        return Response.ok(user);
    }

    @GetMapping("/getCacheUser/{key}")
    public Response getCacheUser(@PathVariable String key){
        return Response.ok(redisService.get(key));
    }

    @PostMapping("/setUser")
    public Response setUser(){
        redisService.set("token",new User());
        return Response.ok();
    }
}
