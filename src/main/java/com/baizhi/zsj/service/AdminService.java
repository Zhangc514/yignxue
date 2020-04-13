package com.baizhi.zsj.service;

import com.baizhi.zsj.entity.Admin;

import java.util.HashMap;

public interface AdminService {

    HashMap<String, Object> login(Admin admin, String enCode);
}
