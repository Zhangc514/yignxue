package com.baizhi.zsj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@tk.mybatis.spring.annotation.MapperScan("com.baizhi.zsj.dao")
@MapperScan("com.baizhi.zsj.dao")
@SpringBootApplication
public class YingxueApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingxueApplication.class, args);
    }
}