package com.example.research_mng.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.research_mng.entity.Award;
import com.example.research_mng.entity.LoginResult;
import com.example.research_mng.entity.ResultVo;
import com.example.research_mng.entity.User;
import com.example.research_mng.service.UserService;
import com.example.research_mng.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: RecitationController
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/4/30 22:55
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping({"/login"})
    public ResultVo login(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getLoginName()) || StringUtils.isEmpty(user.getPassword())) {
            return ResultUtils.error("用户名、密码不能为空!");
        }

        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().eq(User::getLoginName, user.getLoginName()).eq(User::getPassword, user.getPassword());
        User usr = userService.getOne(query);

        if (usr == null) {
            return ResultUtils.error("用户名或密码错误!");
        }

        LoginResult loginResult = new LoginResult();
        loginResult.setUserId(usr.getId());
        loginResult.setUserName(usr.getName());
        loginResult.setUserType(usr.getIsAdmin());

        return ResultUtils.success("登录成功", loginResult);
    }

    @PostMapping({"/register"})
    public ResultVo register(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPassword())) {
            return ResultUtils.error("用户名、密码不能为空!");
        }

        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().eq(User::getName, user.getName());
        User usr = userService.getOne(query);

        if (usr != null) {
            return ResultUtils.error("已存在用户!");
        }

        userService.save(user);

        return ResultUtils.success("注册成功", null);
    }

    @GetMapping({"/user/list"})
    public ResultVo users(Model model) {

        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().eq(User::getStatus, 0);
        List<User> users = userService.list(query);

        return ResultUtils.success("查询成功", users);
    }

    @PostMapping("/user/upsert")
    public ResultVo addUser(Model model, @RequestBody User user) {
        try {
            userService.saveOrUpdate(user);
        } catch (Exception e) {
            return ResultUtils.error("添加失败", e.getMessage());
        }

        return ResultUtils.success("添加成功");
    }

    @RequestMapping("/user/delete/{id}")
    public ResultVo deleteAward(Model model, @PathVariable("id") Long id) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, id).set(User::getStatus, 1);
        userService.update(updateWrapper);
        return ResultUtils.success("删除成功");
    }
}
