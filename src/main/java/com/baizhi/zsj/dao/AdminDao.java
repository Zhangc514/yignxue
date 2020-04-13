package com.baizhi.zsj.dao;

import com.baizhi.zsj.entity.Admin;

public interface AdminDao {
    //根据姓名查
    Admin queryByUsername(String username);
}
