package com.example.research_mng.entity;

import lombok.Data;

/**
 * @ClassName: Project
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/7/16 15:15
 */
@Data
public class WorkParam {
    private Long currentPage;
    private Long pageSize;
    private Long id;
    private String name;
    private String press;
    private String authors;
    private String department;
    private String category;
}
