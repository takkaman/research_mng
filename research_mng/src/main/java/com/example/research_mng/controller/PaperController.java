package com.example.research_mng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.research_mng.entity.ResultVo;
import com.example.research_mng.entity.Paper;
import com.example.research_mng.entity.PaperParam;
import com.example.research_mng.service.PaperService;
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
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private PaperService paperService;

    @GetMapping("/list")
    public ResultVo listPapers(Model model) {
        LambdaQueryWrapper<Paper> query = new LambdaQueryWrapper<>();
        query.eq(Paper::getStatus, 0);
        List<Paper> paperList = paperService.list(query);
        return ResultUtils.success("查询成功", paperList);
    }

    @PostMapping("/upsert")
    public ResultVo addPaper(Model model, @RequestBody Paper paper) {
        try {
            paperService.saveOrUpdate(paper);
        } catch (Exception e) {
            return ResultUtils.error("添加失败", e.getMessage());
        }

        return ResultUtils.success("添加成功");
    }

    @RequestMapping("/delete/{id}")
    public ResultVo deletePaper(Model model, @PathVariable("id") Long id) {
        LambdaUpdateWrapper<Paper> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Paper::getId, id).set(Paper::getStatus, 1);
        paperService.update(updateWrapper);
        return ResultUtils.success("删除成功");
    }

    @GetMapping("/search")
    public ResultVo searchPaper(Model model, PaperParam param) {
        IPage<Paper> list = paperService.searchPapers(param);
        return ResultUtils.success("查询成功", list);
    }

    @RequestMapping("/export")
    public ResultVo exportPapers(Model model) {
        return ResultUtils.success("查询成功");
    }
}
