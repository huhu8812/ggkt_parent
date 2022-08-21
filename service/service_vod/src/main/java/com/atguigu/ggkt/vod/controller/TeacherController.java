package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.atguigu.ggkt.vod.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author huhu
 * @since 2022-08-20
 */
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping("/vod/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("查询所有讲师列表")
    @GetMapping("/findAll")
    public Result findAll(){
        List<Teacher> teacherList = teacherService.list();
        return Result.ok(teacherList).message("查询数据成功");
    }

    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/remove/{id}")
    public Result removeById(@ApiParam(name = "id", value = "ID", required = true)
                                  @PathVariable String id){
        boolean isSuccess = teacherService.removeById(id);
        if (isSuccess == true){
            return Result.ok(null).message("删除成功");
        } else {
            return Result.fail(null).message("删除失败");
        }
    }

    @ApiOperation("分页查询讲师")
    @PostMapping("/{page}/{pageSize}")
    public Result findPage(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                               @ApiParam(name = "pageSize", value = "每页大小size", required = false) @PathVariable Long pageSize,
                           @ApiParam(name = "teacherQueryVo", value = "teacher分页查询前端VO", required = false) TeacherQueryVo teacherQueryVo){
        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String joinDateBegin = teacherQueryVo.getJoinDateBegin();
        String joinDateEnd = teacherQueryVo.getJoinDateEnd();

        Page<Teacher> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(name != null, Teacher::getName, name)
                .eq(level != null, Teacher::getLevel, level)
                .ge(joinDateBegin != null, Teacher:: getJoinDate, joinDateBegin)
                .le(joinDateEnd != null, Teacher::getJoinDate, joinDateEnd);

        Page<Teacher> teacherPage = teacherService.page(pageInfo, lambdaQueryWrapper);

        return Result.ok(teacherPage).message("分页查询讲师成功");
    }
}

