package com.jesus.user.web.controller.login;

import com.jesus.common.base.constant.GlobalConstant;
import com.jesus.common.response.Response;
import com.jesus.common.utils.CommonUtil;
import com.jesus.user.shiro.data.Login;
import com.jesus.user.shiro.token.CipherFreeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

@RestController
@Slf4j
@RequestMapping("/jesus")
public class LoginController {

    /**
     * 渠道登录，免密登录
     *
     * @param request HTTP 请求体体
     * @return JSON
     */
    @GetMapping(value = "/doLogin")
    public Response doLogin(HttpServletRequest request) {
        String code = request.getParameter("code");
        if (CommonUtil.isEmpty(code)) {
            return Response.fail("请输入用户名");
        }

        // 通过code，或其他方式进行渠道认证
        //Java 8 新特性 函数式接口
        Function<String, String> channel = (r -> {
            for(String str : GlobalConstant.Channel.channelList){
                if(str.equals(r)){
                    return str;
                }
            }
            return r;
        });
        String login = channel.apply(code);

        // 执行免密码登录
        return doLogin(new CipherFreeToken(login));
    }

    /**
     * 无状态登录
     *
     * @param login 登录信息
     * @return json
     */
    @PostMapping(value = "/doLogin")
    public Response doLogin(@RequestBody Login login) {

        UsernamePasswordToken token = new UsernamePasswordToken(login.getUsername(),
                login.getPassword(),
                login.isRememberMe());
        return doLogin(token);
    }

    @GetMapping("/logout")
    public Response logout(){
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
        } catch (Exception e) {
            log.error("登出异常:{}",e.getMessage());
            return Response.error("登出失败，请刷新重试！");
        }
        return Response.ok();
    }

    /**
     * 执行登录
     *
     * @param token 令牌
     * @return 结果
     */
    private Response doLogin(AuthenticationToken token) {
        // 尝试登录
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException e) {
            log.error("登录异常:{}",e.getMessage());
            return Response.fail("密码错误");
        } catch (LockedAccountException e) {
            log.error("登录异常:{}",e.getMessage());
            return Response.fail("账号锁定，无法登录");
        } catch (UnknownAccountException e) {
            log.error("登录异常:{}",e.getMessage());
            return Response.fail("用户名或密码错误");
        } catch (AuthenticationException e) {
            log.error("登录异常:{}",e.getMessage());
            return Response.fail("认证失败，" + e.getMessage());
        } catch (Exception e) {
            log.error("登录异常:{}",e.getMessage());
            return Response.error(e.getMessage());
        }
        return Response.ok();
    }

}
