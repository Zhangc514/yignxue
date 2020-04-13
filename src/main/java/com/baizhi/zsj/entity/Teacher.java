package com.baizhi.zsj.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @ExcelIgnore
    private String id;

    @Excel(name = "名字",needMerge = true)
    private String name;

    @Excel(name = "年龄",needMerge = true)
    private Integer age;

    //关系属性 一对多连接  必须要用 collection 集合
    @ExcelCollection(name = "学生信息")
    private List<Emps> empsList;
}
