package com.example.research_mng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.research_mng.entity.VProject;
import com.example.research_mng.entity.ProjectParam;
import com.example.research_mng.entity.ResultVo;
import com.example.research_mng.service.VProjectService;
import com.example.research_mng.utils.ResultUtils;
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
@RequestMapping("/vproject")
public class VProjectController {

    @Autowired
    private VProjectService projectService;

    @GetMapping("/list")
    public ResultVo listProjects(Model model) {
        LambdaQueryWrapper<VProject> query = new LambdaQueryWrapper<>();
        query.eq(VProject::getStatus, 0);
        List<VProject> projectList = projectService.list(query);
        return ResultUtils.success("查询成功", projectList);
    }

    @PostMapping("/upsert")
    public ResultVo addProject(Model model, @RequestBody VProject project) {
        try {
            projectService.saveOrUpdate(project);
        } catch (Exception e) {
            return ResultUtils.error("添加失败", e.getMessage());
        }

        return ResultUtils.success("添加成功");
    }

    @RequestMapping("/delete/{id}")
    public ResultVo deleteProject(Model model, @PathVariable("id") Long id) {
        LambdaUpdateWrapper<VProject> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(VProject::getProjectId, id).set(VProject::getStatus, 1);
        projectService.update(updateWrapper);
        return ResultUtils.success("删除成功");
    }

    @GetMapping("/search")
    public ResultVo searchProject(Model model, ProjectParam param) {
        IPage<VProject> list = projectService.searchProjects(param);
        return ResultUtils.success("查询成功", list);
    }

    @RequestMapping("/export")
    public ResultVo exportProjects(Model model) {
        return ResultUtils.success("导出成功");
    }
}
