package com.example.research_mng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.research_mng.entity.Award;
import com.example.research_mng.entity.AwardParam;
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
public interface AwardMapper extends BaseMapper<Award> {
    IPage<Award> searchAwards(IPage<Award> page, @Param("param") AwardParam param);
    List<YearStats> countByYear();
}
