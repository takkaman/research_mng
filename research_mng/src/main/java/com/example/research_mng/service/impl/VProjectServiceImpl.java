package com.example.research_mng.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.research_mng.entity.CategoryStats;
import com.example.research_mng.entity.YearStats;
import com.example.research_mng.entity.VProject;
import com.example.research_mng.entity.ProjectParam;
import com.example.research_mng.mapper.VProjectMapper;
import com.example.research_mng.service.VProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ProjectServiceImpl
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/7/16 15:19
 */
@Service
public class VProjectServiceImpl extends ServiceImpl<VProjectMapper, VProject> implements VProjectService {
    @Override
    public IPage<VProject> searchProjects(ProjectParam param) {
        IPage<VProject> page = new Page<>(param.getCurrentPage(), param.getPageSize());
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
