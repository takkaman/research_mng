package com.example.research_mng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: Project
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/7/16 15:15
 */
@TableName("t_user")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String loginName;
    private String name;
    private String password;
    private int isAdmin = 0;
    private int status = 0;
    private String department;
    private String mobile;
    private String position;
    private String email;
}
