package com.example.research_mng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.research_mng.entity.ResultVo;
import com.example.research_mng.entity.Work;
import com.example.research_mng.entity.WorkParam;
import com.example.research_mng.service.WorkService;
import com.example.research_mng.utils.ResultUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: ResearchController
 * @Description: Description of the class file
 * @author: Yansomia
 * @date: 2024/7/13 23:21
 */
@RestController
@RequestMapping("/work")
public class WorkController {

    @Autowired
    private WorkService workService;

    @GetMapping("/list")
    public ResultVo listWorks(Model model) {
        LambdaQueryWrapper<Work> query = new LambdaQueryWrapper<>();
        query.eq(Work::getStatus, 0);
        List<Work> workList = workService.list(query);
        return ResultUtils.success("查询成功", workList);
    }

    @PostMapping("/upsert")
    public ResultVo addWork(Model model, @RequestBody Work work) {
        try {
            workService.saveOrUpdate(work);
        } catch (Exception e) {
            return ResultUtils.error("添加失败", e.getMessage());
        }

        return ResultUtils.success("添加成功");
    }

    @RequestMapping("/delete/{id}")
    public ResultVo deleteWork(Model model, @PathVariable("id") Long id) {
        LambdaUpdateWrapper<Work> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Work::getId, id).set(Work::getStatus, 1);
        workService.update(updateWrapper);
        return ResultUtils.success("删除成功");
    }

    @GetMapping("/search")
    public ResultVo searchWork(Model model, WorkParam param) {
        IPage<Work> list = workService.searchWorks(param);
        return ResultUtils.success("查询成功", list);
    }

    @RequestMapping("/export")
    public ResultVo exportWorks(Model model) {
        return ResultUtils.success("查询成功");
    }

    @RequestMapping("/import")
    public ResultVo importWork(@RequestParam("file") MultipartFile file) {
        try {
            // 使用 Apache POI 解析 Excel 文件
            List<Work> works = parseExcel(file);
            // 将解析出的数据保存到数据库
            saveWorks(works);
            return ResultUtils.success("导入成功");
        } catch (Exception e) {
            return ResultUtils.error("导入失败", e);
        }
    }

    public List<Work> parseExcel(MultipartFile file) throws IOException, ParseException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0); // 假设只处理第一个工作表
        List<Work> works = new ArrayList<>();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue; // 跳过第一行
            }

            Work work = new Work();
            // 假设 Excel 的列顺序和 Work 类的字段顺序一致
            work.setName(row.getCell(0).getStringCellValue());
            work.setPress(row.getCell(1).getStringCellValue());
            work.setAuthors(row.getCell(2).getStringCellValue());
            work.setDepartment(row.getCell(3).getStringCellValue());
            String dateString = row.getCell(4).getStringCellValue();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(dateString);
            work.setPublishTime(date);
            work.setCategory(row.getCell(5).getStringCellValue());
            // 根据实际情况设置其他字段
            works.add(work);
        }

        workbook.close();
        return works;
    }

    private void saveWorks(List<Work> works) {
        for (Work work : works) {
            workService.save(work);
        }
    }
}
