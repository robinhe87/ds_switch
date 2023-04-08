package com.robin.web;

import com.robin.entity.User;
import com.robin.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hehongbing
 * @date 2023/4/8
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/find/{id}")
    public User find(@PathVariable("id") Long id){
        return userService.getById(id);
    }

}
