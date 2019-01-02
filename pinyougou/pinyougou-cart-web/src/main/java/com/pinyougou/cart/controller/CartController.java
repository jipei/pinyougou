package com.pinyougou.cart.controller;

import com.alibaba.fastjson.JSONArray;
import com.pinyougou.common.util.CookieUtils;
import com.pinyougou.vo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/cart")
@RestController
public class CartController {

    //在cookie中的名称
    private static final String COOKIE_CART_LIST = "PYG_CART_LIST";
    
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    /**
     * 登录与未登录情况下获取购物车列表
     * @return 购物车列表
     */
    @GetMapping("/findCartList")
    public List<Cart> findCartList(){
        //判断用户是否已经登录；
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("anonymousUser".equals(username)) {
            //未登录；从cookie中获取数据并转换
            String cartListJsonStr = CookieUtils.getCookieValue(request, COOKIE_CART_LIST, true);
            List<Cart> cookieCartList = new ArrayList<>();
            if (!StringUtils.isEmpty(cartListJsonStr)) {
                cookieCartList = JSONArray.parseArray(cartListJsonStr, Cart.class);
            }
            return cookieCartList;

        } else {
            //已登录；从redis中获取数据
        }
        return null;
    }

    /**
     * 获取当前登录用户名；如果是没有登录的话，那么获取到的用户名为anonymousUser
     * @return 用户信息
     */
    @GetMapping("/getUsername")
    public Map<String, Object> getUsername(){
        Map<String, Object> map = new HashMap<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        map.put("username", username);

        return map;
    }
}
