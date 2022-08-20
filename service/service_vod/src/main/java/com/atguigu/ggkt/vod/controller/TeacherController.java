package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vod.service.TeacherService;
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
}

