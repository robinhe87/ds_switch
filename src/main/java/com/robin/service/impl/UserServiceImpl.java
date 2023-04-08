package com.robin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robin.entity.User;
import com.robin.mapper.UserMapper;
import com.robin.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author hehongbing
 * @date 2023/4/8
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
