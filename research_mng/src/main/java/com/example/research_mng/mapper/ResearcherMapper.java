package com.example.research_mng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.research_mng.entity.Researcher;
import com.example.research_mng.entity.ResearcherParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @InterfaceName: ProjectMapper
 * @Description: Description of the interface file
 * @author: Yansomia
 * @date: 2024/7/16 15:18
 */
@Mapper
public interface ResearcherMapper extends BaseMapper<Researcher> {
    IPage<Researcher> searchResearchers(IPage<Researcher> page, @Param("param") ResearcherParam param);
}
