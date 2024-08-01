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
@TableName("t_h_project")
@Data
public class HProject {
    @TableId(type = IdType.AUTO)
    private Long projectId;
    private Long cardNum;
    private String name;
    private String responsible;
    private String category;
    private Long budget;
    private String department;
    private int status = 0;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endTime;
    private int type; // 0: vertical, 1: horizontal
}
