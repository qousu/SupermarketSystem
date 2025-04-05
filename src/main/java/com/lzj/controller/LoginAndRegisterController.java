package com.lzj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzj.mapper.UserMapper;
import com.lzj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginAndRegisterController {
    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @GetMapping({"/login","/"})
    public String login(Model model){
        return "login";
    }

    @Autowired
    private UserMapper userMapper;
    @PostMapping("/register")
    public String registerUser(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirm-password") String confirmPassword,
            Model model) {

        // 验证密码一致性
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "密码不一致");
            return "register";
        }

        // 检查用户名是否存在
        User existingUser = userMapper.selectOne(
                new QueryWrapper<User>().eq("username", username));
        if (existingUser != null) {
            model.addAttribute("error", "用户名已存在");
            return "register";
        }

        // 创建新用户
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // 添加密码
        newUser.setEmail(email);
        newUser.setIsAdmin(0); // 默认普通用户

        try {
            userMapper.insert(newUser);
        } catch (Exception e) {
            // 处理数据库异常（如唯一约束冲突）
            model.addAttribute("error", "注册失败，请稍后再试");
            return "register";
        }

        return "redirect:/login?registered";
    }
}
