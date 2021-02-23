package com.xwl.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    SUCCESS(200,"操作成功"),
    LACK_MUST_PARAMS(400,"缺少必要请求参数,请确认!"),
    ;
    private Integer status;
    private String message;
}
