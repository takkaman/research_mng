package com.example.research_mng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.research_mng.entity.CategoryStats;
import com.example.research_mng.entity.HProject;
import com.example.research_mng.entity.ProjectParam;
import com.example.research_mng.entity.YearStats;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @InterfaceName: ProjectMapper
 * @Description: Description of the interface file
 * @author: Yansomia
 * @date: 2024/7/16 15:18
 */
@Mapper
public interface HProjectMapper extends BaseMapper<HProject> {
    IPage<HProject> searchProjects(IPage<HProject> page, @Param("param") ProjectParam param);
    List<YearStats> countByYear();
    List<CategoryStats> countByCategory();
}
