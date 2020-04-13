package com.baizhi.zsj.service;

import com.baizhi.zsj.entity.Category;

import java.util.HashMap;

public interface CategoryService {
    //查询一级类别
    HashMap<String,Object> queryByOnePage(Integer page,Integer rows);
    //查询二级类别
    HashMap<String,Object> queryByTwoPage(Integer page,Integer rows,String parentId);
    //删除
    HashMap<String,Object> delete(Category category);
    //添加
    void add(Category category);
    //修改
    void update(Category category);

}
