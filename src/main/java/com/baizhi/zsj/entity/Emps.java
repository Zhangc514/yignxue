package com.baizhi.zsj.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emps {
    @Excel(name = "ID",width = 20,height = 20)
    private String id;
    @Excel(name = "名字")
    private String name;
    @Excel(name = "芳龄")
    private Integer age;
    @Excel(name = "生日",format = "yyyy-MM-dd")
    private Date date;
}
