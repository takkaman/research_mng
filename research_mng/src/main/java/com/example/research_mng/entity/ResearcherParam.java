package com.example.research_mng.entity;

import lombok.Data;

/**
 * @ClassName: Project
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/7/16 15:15
 */
@Data
public class ResearcherParam {
    private Long currentPage;
    private Long pageSize;
    private Long id;
    private String name;
    private String title;
    private String subject;
    private String degree;
    private String department;
    private int status = 0;
}
