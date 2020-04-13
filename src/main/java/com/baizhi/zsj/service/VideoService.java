package com.baizhi.zsj.service;

import com.baizhi.zsj.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface VideoService {
    //页面展示查所有
    HashMap<String, Object> queryByPage(Integer page, Integer rows);
    //添加
    String add(Video video);

    void update(Video video);
    //上传视频
    void uploadVdieo(MultipartFile path, String id, HttpServletRequest request);

    void uploadVdieos(MultipartFile path, String id, HttpServletRequest request);
    //删除
    HashMap<String, Object> delete(Video video);



}
