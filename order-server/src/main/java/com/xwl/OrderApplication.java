package com.xwl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: 薛
 * @Date: 2021/1/30 16:16
 * @Description:
 */
@SpringBootApplication
//@EnableFeignClients
public class OrderApplication {
    /**
     * 启动类
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
