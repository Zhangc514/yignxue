package com.baizhi.zsj;

import com.baizhi.zsj.dao.AdminDao;
import com.baizhi.zsj.dao.UserMapper;
import com.baizhi.zsj.entity.Admin;
import com.baizhi.zsj.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YingxueApplicationTests {

    @Resource
    AdminDao adminDao;

    @Resource
    UserMapper userMapper;

    @Test
    public void testquerys(){
        User user1 = userMapper.queryByUsername("琳");
        System.out.println(user1);
    }

    @Test
    public void testquery(){
        List<User> users = userMapper.selectAll();
        users.forEach(user -> System.out.println(user));
    }

    /*@Test
    public void testQuery(){
        //条件对象
        UserExample example = new UserExample();
        //example.createCriteria().andIdEqualTo("1");
        List<User> users = userMapper.selectByExample(example);
        users.forEach(user -> System.out.println(user));
    }

    @Test
    public void save(){
        //条件对象
        UserExample example = new UserExample();
        //example.createCriteria().andIdEqualTo("1");
        User user = new User("2","琳","514514","2.jpg","山川异域 风月同天","514514","1",new Date());
        //User user = new User("3","xiaohei","514514","2.jpg","good","514514","1",new Date());
        userMapper.insert(user);

        //users.forEach(user -> System.out.println(user));
    }
*/
    @Test
    public void contextLoads() {
        Admin admin = adminDao.queryByUsername("admin");
        System.out.println(admin);
    }

}
