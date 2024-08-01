package com.example.research_mng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.research_mng.entity.Award;
import com.example.research_mng.entity.AwardParam;
import com.example.research_mng.entity.YearStats;

import java.util.List;

/**
 * @InterfaceName: ProjectService
 * @Description: Description of the interface file
 * @author: Yansomia
 * @date: 2024/7/16 15:19
 */
public interface AwardService extends IService<Award> {
    IPage<Award> searchAwards(AwardParam param);
    List<YearStats> countByYear();
}
