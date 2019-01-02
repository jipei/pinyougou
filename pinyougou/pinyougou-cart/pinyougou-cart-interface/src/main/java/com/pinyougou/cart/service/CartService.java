package com.pinyougou.cart.service;

import com.pinyougou.vo.Cart;

import java.util.List;

public interface CartService {
    /**
     * 往购物车列表中添加商品
     * @param cartList 购物车列表
     * @param itemId 商品sku id
     * @param num 购买数量
     * @return 购物车列表
     */
    List<Cart> addItemToCartList(List<Cart> cartList, Long itemId, Integer num);

    /**
     * 根据用户名到redis中查询其对应的购物车列表
     * @param username 用户名
     * @return 购物车列表
     */
    List<Cart> findCartListInRedisByUsername(String username);

    /**
     * 将用户对应的购物车存入redis
     * @param cartList 购物车列表
     * @param username 用户名
     */
    void saveCartListInRedisByUsername(List<Cart> cartList, String username);
}
