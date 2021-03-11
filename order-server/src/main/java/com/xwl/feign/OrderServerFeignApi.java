package com.xwl.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "order-server")
public interface OrderServerFeignApi {
    String SERVER_API = "/client";

    String DELE_ORDER_INFO_BYCODE=SERVER_API+"/deleOrderInfoByCode";
    @PostMapping(DELE_ORDER_INFO_BYCODE)
    Boolean  deleOrderInfoByCode(@RequestParam("orderCode")String orderCode);
}
