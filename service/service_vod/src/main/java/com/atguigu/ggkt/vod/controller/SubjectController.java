package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vod.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author huhu
 * @since 2022-08-26
 */
@Api(value = "课程列表控制器")
@RestController
@RequestMapping("/vod/subject")
@CrossOrigin
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation("根据parent id查询课程列表")
    @GetMapping("getSubject/{id}")
    public Result getSubjectList(@PathVariable Long id){
        List<Subject> subjectList = subjectService.selectSubjectList(id);
        return Result.ok(subjectList);
    }

    @ApiOperation("导出课程信息")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response){
        subjectService.exportData(response);
    }

    @ApiOperation("导入课程信息")
    @GetMapping("/importData")
    public Result importData(MultipartFile file){
        subjectService.importData(file);
        return Result.ok(null);
    }
}

