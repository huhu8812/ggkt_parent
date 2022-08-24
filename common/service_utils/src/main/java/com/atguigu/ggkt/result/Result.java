package com.atguigu.ggkt.result;

import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "全局统一返回结果类")
@Data // 不用再写get,set方法了
public class Result<T> {

    @ApiModelProperty(value = "返回状态码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public Result(){}

    // 成功的方法，没有data数据
//    public static<T> Result<T> ok(){
//        Result<T> result = new Result();
//        result.setCode(200);
//        result.setMessage("成功");
//        return result;
//    }

    // 失败的方法，没有data数据
//    public static<T> Result<T> fail(){
//        Result<T> result = new Result();
//        result.setCode(201);
//        result.setMessage("失败");
//        return result;
//    }

    public static<T> Result<T> build(T data, Integer code, String message){
        Result<T> result = new Result<>();
        if (data!=null){
            result.setData(data);
        }
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    // 成功的方法，有data数据
    public static<T> Result<T> ok(T data){
        return Result.build(data, 20000, "成功");
    }

    // 成功的方法，没有data数据
    public static<T> Result<T> fail(T data){
        return Result.build(data, 20001, "失败");
    }

    // 设置message的值，就不会至于在上面的方法中写死了
    public Result<T> message(String message){
        this.setMessage(message);
        return this;
    }

    // 设置code的值，就不会至于在上面的方法中写死了
    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }
}
