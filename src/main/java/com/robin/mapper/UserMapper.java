package com.robin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robin.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hehongbing
 * @date 2023/4/8
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
