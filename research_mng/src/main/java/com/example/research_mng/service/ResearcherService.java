package com.example.research_mng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.research_mng.entity.Researcher;
import com.example.research_mng.entity.ResearcherParam;

/**
 * @InterfaceName: ProjectService
 * @Description: Description of the interface file
 * @author: Yansomia
 * @date: 2024/7/16 15:19
 */
public interface ResearcherService extends IService<Researcher> {
    IPage<Researcher> searchResearchers(ResearcherParam param);
}
