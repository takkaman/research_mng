package com.example.research_mng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.research_mng.entity.Award;
import com.example.research_mng.entity.AwardParam;
import com.example.research_mng.entity.ResultVo;
import com.example.research_mng.service.AwardService;
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
@RequestMapping("/award")
public class AwardsController {

    @Autowired
    private AwardService awardService;

    @GetMapping("/list")
    public ResultVo listAwards(Model model) {
        LambdaQueryWrapper<Award> query = new LambdaQueryWrapper<>();
        query.eq(Award::getStatus, 0);
        List<Award> awardList = awardService.list(query);
        return ResultUtils.success("查询成功", awardList);
    }

    @PostMapping("/upsert")
    public ResultVo addAward(Model model, @RequestBody Award award) {
        try {
            awardService.saveOrUpdate(award);
        } catch (Exception e) {
            return ResultUtils.error("添加失败", e.getMessage());
        }

        return ResultUtils.success("添加成功");
    }

    @RequestMapping("/delete/{id}")
    public ResultVo deleteAward(Model model, @PathVariable("id") Long id) {
        LambdaUpdateWrapper<Award> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Award::getId, id).set(Award::getStatus, 1);
        awardService.update(updateWrapper);
        return ResultUtils.success("删除成功");
    }

    @GetMapping("/search")
    public ResultVo searchAward(Model model, AwardParam param) {
        IPage<Award> list = awardService.searchAwards(param);
        return ResultUtils.success("查询成功", list);
    }

    @RequestMapping("/export")
    public ResultVo exportAwards(Model model) {
        return ResultUtils.success("查询成功");
    }
}
