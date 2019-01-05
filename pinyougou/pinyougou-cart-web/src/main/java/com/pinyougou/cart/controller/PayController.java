package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.order.service.OrderService;
import com.pinyougou.pay.service.WeixinPayService;
import com.pinyougou.pojo.TbPayLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/pay")
@RestController
public class PayController {

    @Reference
    private OrderService orderService;

    @Reference
    private WeixinPayService weixinPayService;



    /**
     * 获取支付二维码链接地址等信息
     * @param outTradeNo 订单号
     * @return 支付二维码链接地址等信息
     */
    @GetMapping("/createNative")
    public Map<String, String> createNative(String outTradeNo){
        try {
            //1、根据支付日志id获取支付日志
            TbPayLog payLog = orderService.findPayLogByOutTradeNo(outTradeNo);
            //本次要支付的总金额
            String totalFee = payLog.getTotalFee() + "";

            //2、调用支付接口获取信息
            return weixinPayService.createNative(outTradeNo, totalFee);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }
}
