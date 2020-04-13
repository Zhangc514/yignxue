package com.baizhi.zsj.dao;

import com.baizhi.zsj.entity.User;
import com.baizhi.zsj.entity.UserExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper <User> {
    User queryByUsername(String username);
}