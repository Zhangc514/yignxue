package com.baizhi.zsj.controller;


import com.baizhi.zsj.entity.Category;
import com.baizhi.zsj.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Resource
    CategoryService categoryService;


    //查询一级类别
    @RequestMapping("queryByOnePage")
    public HashMap<String,Object> queryByOnePage(Integer page,Integer rows){
        HashMap<String, Object> map = categoryService.queryByOnePage(page, rows);
        return map;
    }
    //查询二级类别
    @RequestMapping("queryByTwoPage")
    public HashMap<String,Object> queryByTwoPage(Integer page,Integer rows,String parentId){
        HashMap<String, Object> queryByTwoPage = categoryService.queryByTwoPage(page, rows, parentId);
        return queryByTwoPage;
    }


    @RequestMapping("edit")
    public Object edit(Category category,String oper){
        String uid = null;
        if (oper.equals("add")){
            categoryService.add(category);
        }

        if (oper.equals("edit")){
            categoryService.update(category);
        }

        if (oper.equals("del")){
            HashMap<String, Object> map = categoryService.delete(category);
            System.out.println(map.get("message"));
            return map;
        }

        return null;
    }
}
