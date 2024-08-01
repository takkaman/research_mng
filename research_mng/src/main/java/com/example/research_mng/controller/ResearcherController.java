package com.example.research_mng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.research_mng.entity.*;
import com.example.research_mng.service.AwardService;
import com.example.research_mng.service.VProjectService;
import com.example.research_mng.service.ResearcherService;
import com.example.research_mng.service.WorkService;
import com.example.research_mng.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: ResearchController
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/7/13 23:21
 */
@RestController
@RequestMapping("/researcher")
public class ResearcherController {

    @Autowired
    private ResearcherService researcherService;

    @Autowired
    private VProjectService projectService;

    @Autowired
    private AwardService awardService;

    @Autowired
    private WorkService workService;

    @GetMapping("/list")
    public ResultVo listResearchers(Model model) {
        LambdaQueryWrapper<Researcher> query = new LambdaQueryWrapper<>();
        query.eq(Researcher::getStatus, 0);
        List<Researcher> researcherList = researcherService.list(query);
        return ResultUtils.success("查询成功", researcherList);
    }

    @PostMapping("/upsert")
    public ResultVo addResearcher(Model model, @RequestBody Researcher researcher) {
        try {
            researcherService.saveOrUpdate(researcher);
        } catch (Exception e) {
            return ResultUtils.error("添加失败", e.getMessage());
        }

        return ResultUtils.success("添加成功");
    }

    @GetMapping("/search")
    public ResultVo searchResearcher(Model model, ResearcherParam param) {
        IPage<Researcher> list = researcherService.searchResearchers(param);
        return ResultUtils.success("查询成功", list);
    }

    @GetMapping("/details/{id}")
    public ResultVo researcherDetails(Model model, @PathVariable("id") Long id) {
        ResearcherVo researcherVo = new ResearcherVo();
        Researcher researcher = researcherService.getById(id);
        researcherVo.setResearcher(researcher);

        LambdaQueryWrapper<VProject> projectQuery = new LambdaQueryWrapper<>();
        projectQuery.eq(VProject::getResponsible, researcher.getName()).eq(VProject::getStatus, 0);
        researcherVo.setProjects(projectService.list(projectQuery));

        LambdaQueryWrapper<Work> workQuery = new LambdaQueryWrapper<>();
        workQuery.eq(Work::getAuthors, researcher.getName()).eq(Work::getStatus, 0);
        researcherVo.setWorks(workService.list(workQuery));

        return ResultUtils.success("查询成功", researcherVo);
    }

    @GetMapping("/details/{id}/search")
    public ResultVo searchResearcherDetails(Model model, @PathVariable("id") Long id,
                                            @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        ResearcherVo researcherVo = new ResearcherVo();
        Researcher researcher = researcherService.getById(id);
        researcherVo.setResearcher(researcher);

        LambdaQueryWrapper<VProject> projectQuery = new LambdaQueryWrapper<>();
        projectQuery.eq(VProject::getResponsible, researcher.getName()).eq(VProject::getStatus, 0);
        if (StringUtils.isNotBlank(startTime)) {
            projectQuery.gt(VProject::getStartTime, startTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            projectQuery.lt(VProject::getStartTime, endTime);
        }
        researcherVo.setProjects(projectService.list(projectQuery));

        LambdaQueryWrapper<Work> workQuery = new LambdaQueryWrapper<>();
        workQuery.eq(Work::getAuthors, researcher.getName()).eq(Work::getStatus, 0);
        if (StringUtils.isNotBlank(startTime)) {
            workQuery.gt(Work::getPublishTime, startTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            workQuery.lt(Work::getPublishTime, endTime);
        }
        researcherVo.setWorks(workService.list(workQuery));

        return ResultUtils.success("查询成功", researcherVo);
    }

    @RequestMapping("/export")
    public ResultVo exportResearchers(Model model) {
        return ResultUtils.success("查询成功");
    }
}
