package com.example.research_mng.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.research_mng.entity.Award;
import com.example.research_mng.entity.AwardParam;
import com.example.research_mng.entity.YearStats;
import com.example.research_mng.mapper.AwardMapper;
import com.example.research_mng.service.AwardService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ProjectServiceImpl
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/7/16 15:19
 */
@Service
public class AwardServiceImpl extends ServiceImpl<AwardMapper, Award> implements AwardService {
    @Override
    public IPage<Award> searchAwards(AwardParam param) {
        IPage<Award> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        return this.baseMapper.searchAwards(page, param);
    }

    @Override
    public List<YearStats> countByYear() {
        return this.baseMapper.countByYear();
    }
}
