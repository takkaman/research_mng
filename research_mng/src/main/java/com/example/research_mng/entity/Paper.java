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
@TableName("t_academic_paper")
@Data
public class Paper {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String press;
    private String authors;
    private String department;
    private int status = 0;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date publishTime;
}
