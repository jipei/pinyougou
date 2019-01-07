package com.pinyougou.seckill.service;

import com.pinyougou.pojo.TbSeckillOrder;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;

public interface SeckillOrderService extends BaseService<TbSeckillOrder> {

    PageResult search(Integer page, Integer rows, TbSeckillOrder seckillOrder);

    /**
     * 根据秒杀商品id生成秒杀订单
     * @param seckillId 秒杀商品id
     * @param username 用户id
     * @return 操作结果
     */
    String submitOrder(Long seckillId, String username) throws InterruptedException;
}