package com.graduation.rbackend.controller;

import com.graduation.rbackend.model.User;
import com.graduation.rbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 处理用户注册、登录、信息更新等操作
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private  UserService userService;
    // 获取所有用户
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    // 根据id获取用户
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }
    // 根据用户名获取用户
    @GetMapping("/role/{role}")
    public Optional<User> findByRole(@PathVariable String role) {
        return  userService.findByRole(role);
    }
    // 根据用户名获取用户
    @GetMapping("/{name}")
    public Optional<User> findByUserName(@PathVariable String name) {
        return userService.findByUserName(name);
    }
    // 添加用户
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
    // 删除用户
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
