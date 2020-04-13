package com.baizhi.zsj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    private String id;
    private String adminName;
    private Date date;
    private String operation;;
    private String status;

}
