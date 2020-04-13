package com.baizhi.zsj.service;

import com.baizhi.zsj.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface UserService {
    //查询所有
    HashMap<String,Object> queryByPage(Integer page, Integer rows);
    //添加
    String add(User user);

    void uploadUser(MultipartFile headImg, String id, HttpServletRequest request);
    //修改方法 修改状态 status
    void update(User user);
    //删除
    void delete(User user);
    //上传到阿里云
    void uploadUserAliyun(MultipartFile headImg, String id, HttpServletRequest request);
    //封装工具类测试
    void uploadUserAliyuns(MultipartFile headImg, String id, HttpServletRequest request);


}
