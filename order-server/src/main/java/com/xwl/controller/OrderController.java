package com.xwl.controller;

import com.xwl.service.IOrderService;
import com.xwl.util.ExceptionEnum;
import com.xwl.util.R;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 薛
 * @Date: 2021/1/30 16:18
 * @Description:
 */
@RestController
@RequestMapping("xwl")
@AllArgsConstructor
public class OrderController {
    private IOrderService orderService;

    /**
     * 下单接口
     * @param userName
     * @return
     */
    @PostMapping("placeOrder")
    public R<Boolean> placeOrder(@RequestParam(value = "userName",required = true) String userName){
        if (StringUtils.isEmpty(userName))return R.errors(ExceptionEnum.LACK_MUST_PARAMS);
        Boolean isSuccess=false;
        try {
             isSuccess=orderService.placeOrder(userName);
        } catch (RuntimeException e) {
           return R.fail(e.getMessage());
        }
        return R.data(isSuccess);
    }
}
