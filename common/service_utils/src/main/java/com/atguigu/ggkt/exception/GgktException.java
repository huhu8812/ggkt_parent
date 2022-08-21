package com.atguigu.ggkt.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //有参构造
@NoArgsConstructor //无参构造注解
public class GgktException extends RuntimeException{
    private Integer code;
    private String msg;

}
