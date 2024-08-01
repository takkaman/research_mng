package com.example.research_mng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName: Project
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/7/16 15:15
 */
@TableName("t_researcher")
@Data
public class Researcher {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String title;
    private String subject;
    private String degree;
    private String department;
    private int status = 0;
}
