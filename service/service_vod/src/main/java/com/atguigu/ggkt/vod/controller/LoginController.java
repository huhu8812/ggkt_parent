package com.atguigu.ggkt.vod.controller;

import com.atguigu.ggkt.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "讲师登录登出接口管理")
@RestController
@RequestMapping("/admin/vod/user")
@CrossOrigin
public class LoginController {

    @ApiOperation("讲师登录接口")
    @PostMapping("login")
    public Result login(){
        Map<String, Object> map = new HashMap<>();
        map.put("token", "admin-token");
        return Result.ok(map);
    }

    @GetMapping("info")
    public Result info(){
        Map<String, Object> map = new HashMap<>();
        map.put("roles", "admin");
        map.put("introduction", "I am a super administrator");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name", "Super Admin");
        return Result.ok(map);
    }

    @PostMapping("logout")
    public Result logout(){
        return Result.ok(null);
    }

}
