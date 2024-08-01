package com.example.research_mng.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.research_mng.entity.YearStats;
import com.example.research_mng.entity.Work;
import com.example.research_mng.entity.WorkParam;
import com.example.research_mng.mapper.WorkMapper;
import com.example.research_mng.service.WorkService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ProjectServiceImpl
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/7/16 15:19
 */
@Service
public class WorkServiceImpl extends ServiceImpl<WorkMapper, Work> implements WorkService {
    @Override
    public IPage<Work> searchWorks(WorkParam param) {
        IPage<Work> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        return this.baseMapper.searchWorks(page, param);
    }

    @Override
    public List<YearStats> countByYear() {
        return this.baseMapper.countByYear();
    }
}
