package com.baizhi.zsj;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.StorageClass;
import com.baizhi.zsj.dao.AdminDao;
import com.baizhi.zsj.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class AliyunTest {

    @Resource
    AdminDao adminDao;

    @Resource
    UserMapper userMapper;


    //创建存储空间
    @Test
    public void createBucket(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4Fp4xbKGiigG2CWzBjt6";
        String accessKeySecret = "ZfBr6EbokdfrwnTre4rsKOYRsOftpQ";
        String bucketName = "myyingxue";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建存储空间。
        ossClient.createBucket(bucketName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //上传文件 图片
    @Test
    public void uploadFile() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4Fp4xbKGiigG2CWzBjt6";
        String accessKeySecret = "ZfBr6EbokdfrwnTre4rsKOYRsOftpQ";

        String bucket="yingxue-app";   //存储空间名
        //String fileName="我的女神";
        String fileName="photo/我的女神";  //指定上传文件名  可以指定上传目录
        String localFile="C:\\Users\\Administrator\\Pictures\\photo\\pic1.jpg";  //指定本地文件路径

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, new File(localFile));

        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //上传文件 视频
    @Test
    public void uploadFile1() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4Fp4xbKGiigG2CWzBjt6";
        String accessKeySecret = "ZfBr6EbokdfrwnTre4rsKOYRsOftpQ";

        String bucket = "yingxue-app";   //存储空间名
        //String fileName="我的女神";
        String fileName = "video/天使降临在我身边";  //指定上传文件名  可以指定上传目录
        String localFile = "C:\\Users\\Administrator\\Pictures\\video\\天使降临.mp4";  //指定本地文件路径

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, new File(localFile));

        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //下载文件
        @Test
        public void downLoad(){
            // Endpoint以杭州为例，其它Region请按实际情况填写。
            String endpoint = "http://oss-cn-beijing.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
            String accessKeyId = "LTAI4Fp4xbKGiigG2CWzBjt6";
            String accessKeySecret = "ZfBr6EbokdfrwnTre4rsKOYRsOftpQ";
            String bucketName = "yingxue-app";
            String objectName = "我的女神";
            String localFile="C:\\Users\\Administrator\\Pictures\\photo\\"+objectName;

// 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
            ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

// 关闭OSSClient。
            ossClient.shutdown();
        }
}
