package com.example.research_mng.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.research_mng.entity.Paper;
import com.example.research_mng.entity.PaperParam;
import com.example.research_mng.entity.YearStats;
import com.example.research_mng.mapper.PaperMapper;
import com.example.research_mng.service.PaperService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ProjectServiceImpl
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/7/16 15:19
 */
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements PaperService {
    @Override
    public IPage<Paper> searchPapers(PaperParam param) {
        IPage<Paper> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        return this.baseMapper.searchPapers(page, param);
    }

    @Override
    public List<YearStats> countByYear() {
        return this.baseMapper.countByYear();
    }
}
