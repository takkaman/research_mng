package com.example.research_mng.entity;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: ResearcherVo
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/7/18 23:16
 */
@Data
public class ResearcherVo {
    private Researcher researcher;
    private List<VProject> projects;
    private List<Award> awards;
    private List<Work> works;
}
