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
@TableName("t_awards")
@Data
public class Award {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String department;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date awardTime;
    private String awardScope;
    private String awardLevel;
    private int status = 0;
}
