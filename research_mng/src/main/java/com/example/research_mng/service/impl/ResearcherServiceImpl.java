package com.example.research_mng.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.research_mng.entity.Researcher;
import com.example.research_mng.entity.ResearcherParam;
import com.example.research_mng.mapper.ResearcherMapper;
import com.example.research_mng.service.ResearcherService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ProjectServiceImpl
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/7/16 15:19
 */
@Service
public class ResearcherServiceImpl extends ServiceImpl<ResearcherMapper, Researcher> implements ResearcherService {
    @Override
    public IPage<Researcher> searchResearchers(ResearcherParam param) {
        IPage<Researcher> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        return this.baseMapper.searchResearchers(page, param);
    }
}