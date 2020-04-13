package com.baizhi.zsj.serviceImpl;

import com.baizhi.zsj.dao.CategoryMapper;
import com.baizhi.zsj.entity.Category;
import com.baizhi.zsj.entity.CategoryExample;
import com.baizhi.zsj.service.CategoryService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;


    //查询所有的一级类别方法
    @Override
    public HashMap<String, Object> queryByOnePage(Integer page, Integer rows) {

        HashMap<String, Object> map = new HashMap<>();

        //封装数据
        //总条数 records   一级类别总条数
        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelsEqualTo(1);
        Integer records = categoryMapper.selectCountByExample(example);
        map.put("records",records);

        //总页数 total  总条数/每页展示是条数  看看是否有余数 有的话 total+1
        Integer total = records% rows == 0? records/rows:records/rows+1;
        map.put("total",total);
        //当前页
        map.put("page",page);


        //数据 rows
        //参数
        RowBounds rowBounds = new RowBounds((page-1*rows),rows);
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",categories);

        return map;
    }



    //查询二级类别的方法
    @Override
    public HashMap<String, Object> queryByTwoPage(Integer page, Integer rows, String parentId) {
        HashMap<String, Object> map = new HashMap<>();

        //封装数据
        //总条数 records   二级类别总条数
        CategoryExample example = new CategoryExample();
        //查询条件与一级类别不同
        example.createCriteria().andParentIdEqualTo(parentId);
        Integer records = categoryMapper.selectCountByExample(example);
        map.put("records",records);

        //总页数 total  总条数/每页展示是条数  看看是否有余数 有的话 total+1
        Integer total = records% rows == 0? records/rows:records/rows+1;
        map.put("total",total);
        //当前页
        map.put("page",page);


        //数据 rows
        //参数
        RowBounds rowBounds = new RowBounds((page-1*rows),rows);
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",categories);

        return map;
    }

    @Override
    public HashMap<String, Object> delete(Category category) {
        HashMap<String, Object> map = new HashMap<>();
        //根据类别对象查询类别信息   id
        Category cate = categoryMapper.selectOne(category);

        //判断删除的是一级类别还是二级类别
        if (cate.getLevels()==1){
            //一级类别  判断是否有二级类别   二级类别数量
            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentIdEqualTo(category.getId());
            int count = categoryMapper.selectCountByExample(example);
            if(count==0){
                //没有   直接删除
                categoryMapper.deleteByPrimaryKey(category);
                map.put("status","200");
                map.put("message","删除成功");
            }else{
                //有二级类别   返回提示信息  不能删除
                map.put("status","400");
                map.put("message","删除失败，该类别下有子类别");
            }
        }else{
            //二级类别  是否有视频
            //有   不能删除  提示信息
            //没有 直接删除
            categoryMapper.deleteByPrimaryKey(category);
            map.put("status","200");
            map.put("message","删除成功");
        }
        return map;
    }
    //添加
    @Override
    public void add(Category category) {

        //判断添加的是一级类别还是二级类别
        category.setId(UUID.randomUUID().toString());
        if (category.getParentId()==null){
            //添加的是一级类别
            category.setLevels(1);
        }else{
            //否则添加的为二级类别
            category.setLevels(2);
        }
        //执行添加
        categoryMapper.insertSelective(category);
    }
    //修改
    @Override
    public void update(Category category) {
        //根据主键修改  Selective 没有的不会修改，且不为空
        categoryMapper.updateByPrimaryKeySelective(category);
    }
}
