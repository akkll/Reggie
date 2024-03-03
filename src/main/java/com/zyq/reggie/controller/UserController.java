package com.zyq.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zyq.reggie.common.R;
import com.zyq.reggie.entity.User;
import com.zyq.reggie.service.UserService;
import com.zyq.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/code")
    public R<String> get01(HttpSession httpSession, @RequestBody User user){
        //获取手机号
        String phone = user.getPhone();
        if (!phone.isEmpty()){
            //生成随机四位数
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            //调用阿里云短信服务api发送短信
            log.info("code={}", code);
//            SMSUtils.sendMessage("");
            //保存验证码
            httpSession.setAttribute("code", code);
            return R.success("验证码已发送");
        }
        return R.error("电话号码为空");
    }
    @PostMapping("/login")
    public R<String> post02(HttpSession httpSession, @RequestBody Map<String, String> map){//传入phone和code参数
        //获取验证码
        String code = map.get("code");
        String phone = map.get("phone");
        //与session里的比对
        String code1 = (String) httpSession.getAttribute("code");
        //比对成功后，判断手机号是否为新用户
        if(!code.isEmpty()&&code.equals(code1)){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User one = userService.getOne(queryWrapper);
            if(one==null){
                //新用户则自动注册
                User user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
//                User one1 = userService.getOne(queryWrapper);
                httpSession.setAttribute("user", user.getId());
            }else{
                //老用户直接登录
                httpSession.setAttribute("user", one.getId());
            }
            return R.success("成功登录");
        }
        return R.error("验证码错误");

    }
    @GetMapping("/haha")
    public void test01(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");

        response.getWriter().write("哈哈，小丑🤡");
    }
}
