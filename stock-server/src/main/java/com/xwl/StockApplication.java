package com.xwl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: 薛
 * @Date: 2021/1/30 16:34
 * @Description:
 */
@SpringBootApplication
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class,args);
    }
}
