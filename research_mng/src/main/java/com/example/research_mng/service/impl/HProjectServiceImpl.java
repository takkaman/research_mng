package com.example.research_mng.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.research_mng.entity.CategoryStats;
import com.example.research_mng.entity.HProject;
import com.example.research_mng.entity.ProjectParam;
import com.example.research_mng.entity.YearStats;
import com.example.research_mng.mapper.HProjectMapper;
import com.example.research_mng.service.HProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ProjectServiceImpl
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/7/16 15:19
 */
@Service
public class HProjectServiceImpl extends ServiceImpl<HProjectMapper, HProject> implements HProjectService {
    @Override
    public IPage<HProject> searchProjects(ProjectParam param) {
        IPage<HProject> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        return this.baseMapper.searchProjects(page, param);
    }

    @Override
    public List<YearStats> countByYear() {
        return this.baseMapper.countByYear();
    }

    @Override
    public List<CategoryStats> countByCategory() {
        return this.baseMapper.countByCategory();
    }
}
