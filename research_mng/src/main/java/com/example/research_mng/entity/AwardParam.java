package com.example.research_mng.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: Project
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/7/16 15:15
 */
@Data
public class AwardParam {
    private Long currentPage;
    private Long pageSize;
    private Long id;
    private String name;
    private String department;
    private String awardScope;
    private String awardLevel;
    private int status = 0;
}
