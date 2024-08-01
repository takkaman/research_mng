package com.example.research_mng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: Project
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/7/16 15:15
 */
@Data
public class ProjectParam {
    private Long currentPage;
    private Long pageSize;
    private Long projectId;
    private Long cardNum;
    private String name;
    private String responsible;
    private String category;
    private Long budget;
    private String department;
//    private Date startTime;
//    private Date endTime;
}
