package com.jdktomcat.demo.dead.lock.restart.transaction.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`user`")
public class User {

    private Integer id;

    private String name;

    private int age;

}
