package com.xwl.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * @Auther: 薛
 * @Date: 2021/2/19 10:25
 * @Description:
 */
@Data
@NoArgsConstructor
public class R<T> {
    private int status;
    private boolean success;
    private String message;
    private T data;

    private R(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.success = ExceptionEnum.SUCCESS.getStatus() == status;
    }
    private R(ExceptionEnum exceptionEnum){
        this.status=exceptionEnum.getStatus();
        this.success=false;
        this.message=exceptionEnum.getMessage();
    }
    /**
     * 其它异常
     * @param message
     */
    private R(String message){
        this.status=400;
        this.success=false;
        this.message=message;
    }
    // TODO 操作成功

    /**
     * 参数T
     * 第一个 表示是泛型
     * 第二个 表示返回的是T类型的数据
     * 第三个 限制参数类型为T
     *
     * @param data
     * @return
     */
    public static <T> R<T> data(T data) {
        return getMsgAndCode(data, ExceptionEnum.SUCCESS.getMessage());
    }

    /**
     * 操作成功返回子体
     * @param data
     * @param message
     * @param <T>
     * @return
     */
    private static <T> R<T> getMsgAndCode(T data, String message) {
        if (StringUtils.isEmpty(data)) {
            return new R(200, "当前条件下暂无数据", data);
        }
        return new R(200, message, data);
    }
    /**
     * 失败返回格式
     */
    public static <T> R<T> errors(ExceptionEnum exceptionEnum){
        return new R(exceptionEnum);
    }
    /**
     * 失败返回格式
     */
    public static <T> R<T> fail(String message){
        return new R(message);
    }
}
