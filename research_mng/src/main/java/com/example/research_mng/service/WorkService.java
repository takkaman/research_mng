package com.example.research_mng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.research_mng.entity.YearStats;
import com.example.research_mng.entity.Work;
import com.example.research_mng.entity.WorkParam;

import java.util.List;

/**
 * @InterfaceName: ProjectService
 * @Description: Description of the interface file
 * @author: Yansomia
 * @date: 2024/7/16 15:19
 */
public interface WorkService extends IService<Work> {
    IPage<Work> searchWorks(WorkParam param);
    List<YearStats> countByYear();
}
