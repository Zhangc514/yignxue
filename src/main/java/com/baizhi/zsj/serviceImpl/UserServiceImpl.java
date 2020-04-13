package com.baizhi.zsj.serviceImpl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baizhi.zsj.annotation.AddLog;
import com.baizhi.zsj.dao.UserMapper;
import com.baizhi.zsj.entity.User;
import com.baizhi.zsj.entity.UserExample;
import com.baizhi.zsj.service.UserService;
import com.baizhi.zsj.util.AliyunOssUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @AddLog(value = "查询用户")
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {

        HashMap<String, Object> map = new HashMap<>();

        //封装数据
        //总条数 records
        UserExample example = new UserExample();
        int records = userMapper.selectCountByExample(example);
        map.put("records",records);

        //总页数 total  总条数/每页展示是条数  看看是否有余数 有的话 total+1
        Integer total = records% rows == 0? records/rows:records/rows+1;
        map.put("total",total);
        //当前页
        map.put("page",page);
        //数据 rows
        //参数
        RowBounds rowBounds = new RowBounds((page-1*rows),rows);
        List<User> users = userMapper.selectByRowBounds(new User(), rowBounds);
        map.put("rows",users);

        return map;
    }

    //添加
    @Override
    public String add(User user) {
        //uid是往后的修改操作需要用到
        String uid = UUID.randomUUID().toString();
        //获取id
        user.setId(uid);
        //获取状态
        user.setStatus("1");
        //获取日期
        user.setCreateDate(new Date());
        //添加
        //System.out.println(user);
        userMapper.insert(user);
        return uid;
    }
    //文件上传
    @Override
    public void uploadUser(MultipartFile headImg, String id, HttpServletRequest request) {
        //根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");
        File file = new File(realPath);
        //判断文件夹是否存在 不存在则创建一个
        if (!file.exists()) {
            file.mkdirs();
        }

        //2.获取文件名
        String filename = headImg.getOriginalFilename();
        //加一个时间戳好区分
        String newName = new Date().getTime() + "-" + filename;


        try {
            //3.文件上传
            headImg.transferTo(new File(realPath, newName));
            //4.图片修改
            //修改的条件
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(id);
            User user = new User();
            user.setHeadImg(newName);//设置修改结果
            //修改了
            userMapper.updateByExampleSelective(user,example);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //修改状态 status
    @Override
    public void update(User user) {

        //根据主键修改 id为主键
        userMapper.updateByPrimaryKeySelective(user);

    }
    //删除
    @Override
    public void delete(User user) {

        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void uploadUserAliyun(MultipartFile headImg, String id, HttpServletRequest request) {
        //将文件转为byte数组
        byte[] bytes =null;
        try {
            bytes = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取文件名
        String filename = headImg.getOriginalFilename();
        String newName=new Date().getTime()+"-"+filename;


        //1.文件上传至阿里云

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4Fp4xbKGiigG2CWzBjt6";
        String accessKeySecret = "ZfBr6EbokdfrwnTre4rsKOYRsOftpQ";
        String bucket="yingxue-app";   //存储空间名
        String fileName=newName;  //指定上传文件名  可以指定上传目录

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);

        // 上传Byte数组。
        ossClient.putObject(bucket, fileName, new ByteArrayInputStream(bytes));

        // 关闭OSSClient。
        ossClient.shutdown();


        //2.图片信息的修改
        //修改的条件
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);

        User user = new User();
        user.setHeadImg("https://yingxue-app.oss-cn-beijing.aliyuncs.com/"+newName);  //设置修改的结果   网络路径
        //https://yingxue-app.oss-cn-beijing.aliyuncs.com/1585674847550-pic4.jpg

        //修改
        userMapper.updateByExampleSelective(user,example);
    }

    @Override
    public void uploadUserAliyuns(MultipartFile headImg, String id, HttpServletRequest request) {
        //获取文件名
        String filename = headImg.getOriginalFilename();
        String newName=new Date().getTime()+"-"+filename;

        //1.文件上传至阿里云
        AliyunOssUtil.uploadFileBytes("yingxue-app",headImg,newName);

        //截取视频第一帧
        //上传封面

        //2.图片信息的修改
        //修改的条件
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);

        User user = new User();
        user.setHeadImg("https://yingxue-app.oss-cn-beijing.aliyuncs.com/"+newName);  //设置修改的结果   网络路径
        //https://yingxue-app.oss-cn-beijing.aliyuncs.com/1585641490828-9.jpg

        //修改
        userMapper.updateByExampleSelective(user,example);

    }
}
