package com.pinyougou.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.vo.Cart;

import java.util.List;

@Service(interfaceClass = CartService.class)
public class CartServiceImpl implements CartService {
    @Override
    public List<Cart> addItemToCartList(List<Cart> cartList, Long itemId, Integer num) {
        return null;
    }
}
