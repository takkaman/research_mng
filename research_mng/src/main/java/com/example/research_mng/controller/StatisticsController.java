package com.example.research_mng.controller;

import com.example.research_mng.entity.CategoryStats;
import com.example.research_mng.entity.ResultVo;
import com.example.research_mng.entity.YearStats;
import com.example.research_mng.service.*;
import com.example.research_mng.utils.ResultUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @ClassName: RecitationController
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/4/30 22:55
 */
@RestController
@RequestMapping("/stats")
public class StatisticsController {

    @Autowired
    private ResearcherService researcherService;

    @Autowired
    private VProjectService vProjectService;

    @Autowired
    private HProjectService hProjectService;

    @Autowired
    private WorkService workService;

    @Autowired
    private AwardService awardService;

    @Autowired
    private PaperService paperService;

    @GetMapping({"/all"})
    public ResultVo allStats() {

        Map<String, List<ResearchStats>> researchStatsMap = new HashMap<>();
        List<YearStats> vProjectYearStatsList = vProjectService.countByYear();
        for (YearStats vProjectYearStats : vProjectYearStatsList) {
            researchStatsMap.putIfAbsent(vProjectYearStats.getYear(), new ArrayList<>());
            researchStatsMap.get(vProjectYearStats.getYear()).add(new ResearchStats("纵向项目", vProjectYearStats.getCount()));
        }

        List<YearStats> hProjectYearStatsList = hProjectService.countByYear();
        for (YearStats hProjectYearStats : hProjectYearStatsList) {
            researchStatsMap.putIfAbsent(hProjectYearStats.getYear(), new ArrayList<>());
            researchStatsMap.get(hProjectYearStats.getYear()).add(new ResearchStats("横向项目", hProjectYearStats.getCount()));
        }

        List<YearStats> workYearStatsList = workService.countByYear();
        for (YearStats workYearStats : workYearStatsList) {
            researchStatsMap.putIfAbsent(workYearStats.getYear(), new ArrayList<>());
            researchStatsMap.get(workYearStats.getYear()).add(new ResearchStats("著作", workYearStats.getCount()));
        }

        List<YearStats> paperYearStatsList = paperService.countByYear();
        for (YearStats paperYearStats : paperYearStatsList) {
            researchStatsMap.putIfAbsent(paperYearStats.getYear(), new ArrayList<>());
            researchStatsMap.get(paperYearStats.getYear()).add(new ResearchStats("论文", paperYearStats.getCount()));
        }

        List<YearStats> awardYearStatsList = awardService.countByYear();
        for (YearStats awardYearStats : awardYearStatsList) {
            researchStatsMap.putIfAbsent(awardYearStats.getYear(), new ArrayList<>());
            researchStatsMap.get(awardYearStats.getYear()).add(new ResearchStats("获奖", awardYearStats.getCount()));
        }

        List<ResearchStatsFinal> researchStatsFinals = new ArrayList<>();
        researchStatsMap.forEach((k, v) -> {
            ResearchStatsFinal researchStatsFinal = new ResearchStatsFinal();
            researchStatsFinal.setYear(k);
            researchStatsFinal.setProducts(v);
            researchStatsFinals.add(researchStatsFinal);
        });

        return ResultUtils.success("成功", researchStatsFinals);
    }

    @GetMapping({"/category"})
    public ResultVo categoryStats() {

        List<ResearchStats> researchStatsList = new ArrayList<>();

        List<CategoryStats> vProjectCategoryStatsList = vProjectService.countByCategory();
        for (CategoryStats vProjectCategoryStats : vProjectCategoryStatsList) {
            researchStatsList.add(new ResearchStats(vProjectCategoryStats.getCategory(), vProjectCategoryStats.getCount()));
        }

        List<CategoryStats> hProjectCategoryStatsList = hProjectService.countByCategory();
        List<ResearchStats> hResearchStats = new ArrayList<>();
        int count = 0;
        for (CategoryStats hProjectCategoryStats : hProjectCategoryStatsList) {
            count += hProjectCategoryStats.getCount();
        }
        researchStatsList.add(new ResearchStats("横向项目", count));

        return ResultUtils.success("成功", researchStatsList);
    }

    @Data
    public static class ResearchStats {
        private String name;
        private long value;

        public ResearchStats(String name, long value) {
            this.name = name;
            this.value = value;
        }
    }

    @Data
    public static class ResearchStatsFinal {
        private String year;
        private List<ResearchStats> products;
    }

    @Data
    public static class ResearchStatsCategory {
        private String category;
        private List<ResearchStats> products;

        public ResearchStatsCategory(String category, List<ResearchStats> products) {
            this.category = category;
            this.products = products;
        }
    }
}
