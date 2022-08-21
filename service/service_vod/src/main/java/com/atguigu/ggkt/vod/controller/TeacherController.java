package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.exception.GgktException;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.atguigu.ggkt.vod.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
        if (isSuccess){
            return Result.ok(null).message("删除成功");
        } else {
            return Result.fail(null).message("删除失败");
        }
    }

    @ApiOperation("分页查询讲师")
    @PostMapping("/{page}/{pageSize}")
    public Result<Page<Teacher>> findPage(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                               @ApiParam(name = "pageSize", value = "每页大小size", required = false) @PathVariable Long pageSize,
                           @ApiParam(name = "teacherQueryVo", value = "teacher分页查询前端VO", required = false) @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {

        Page<Teacher> pageInfo = new Page<>(page, pageSize);

        if (teacherQueryVo == null) {
            teacherService.page(pageInfo);
            return Result.ok(pageInfo);
        } else {
            String name = teacherQueryVo.getName();
            Integer level = teacherQueryVo.getLevel();
            String joinDateBegin = teacherQueryVo.getJoinDateBegin();
            String joinDateEnd = teacherQueryVo.getJoinDateEnd();


            LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.like(name != null, Teacher::getName, name)
                    .eq(level != null, Teacher::getLevel, level)
                    .ge(joinDateBegin != null, Teacher::getJoinDate, joinDateBegin)
                    .le(joinDateEnd != null, Teacher::getJoinDate, joinDateEnd);

            Page<Teacher> teacherPage = teacherService.page(pageInfo, lambdaQueryWrapper);

            return Result.ok(teacherPage).message("分页查询讲师成功");
        }
    }

    @ApiOperation("新增一个讲师")
    @PostMapping("/add")
    public Result add(@RequestBody Teacher teacher){
        boolean isSuccess = teacherService.save(teacher);
        if (isSuccess) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    /**
     * 编辑讲师前，先查询
     * @param id
     * @return
     */
    @ApiOperation("根据ID查询讲师")
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id){
        Teacher teacher = teacherService.getById(id);
        if (teacher != null){
            return Result.ok(teacher);
        } else {
            return Result.fail(null).message("没有查询到讲师信息");
        }
    }

    @ApiOperation("编辑讲师信息")
    @PostMapping("/edit")
    public Result edit(@RequestBody Teacher teacher){
        boolean isSuccess = teacherService.updateById(teacher);
        if (isSuccess){
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    @ApiOperation("根据ID列表批量删除讲师")
    @DeleteMapping("/batchremove")
    public Result removeBatch(@RequestBody List<Long> idList){
//        try {
//            int a = 10/0;
//        } catch (Exception e){
//            throw new GgktException(1000, "抛出自定义异常");
//        }
        boolean isSuccess = teacherService.removeByIds(idList);
        if (isSuccess){
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }
}

