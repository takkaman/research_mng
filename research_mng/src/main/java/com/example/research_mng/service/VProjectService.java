package com.example.research_mng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.research_mng.entity.CategoryStats;
import com.example.research_mng.entity.YearStats;
import com.example.research_mng.entity.VProject;
import com.example.research_mng.entity.ProjectParam;

import java.util.List;

/**
 * @InterfaceName: ProjectService
 * @Description: Description of the interface file
 * @author: Yansomia
 * @date: 2024/7/16 15:19
 */
public interface VProjectService extends IService<VProject> {
    IPage<VProject> searchProjects(ProjectParam param);
    List<YearStats> countByYear();
    List<CategoryStats> countByCategory();
}
