package com.example.research_mng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.research_mng.entity.*;

import java.util.List;

/**
 * @InterfaceName: ProjectService
 * @Description: Description of the interface file
 * @author: Yansomia
 * @date: 2024/7/16 15:19
 */
public interface PaperService extends IService<Paper> {
    IPage<Paper> searchPapers(PaperParam param);
    List<YearStats> countByYear();
}
