package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.common.util.CookieUtils;
import com.pinyougou.vo.Cart;
import com.pinyougou.vo.Result;
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

    //购物车在cookie中的名称
    private static final String COOKIE_CART_LIST = "PYG_CART_LIST";
    //购物车在cookie中的最大生存时间；1天
    private static final int COOKIE_CART_MAX_AGE = 3600*24;


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Reference
    private CartService cartService;

    /**
     * 登录与未登录加入购物车
     *
     * @param itemId 商品sku id
     * @param num 购买数量
     * @return 操作结果
     */
    @GetMapping("/addItemToCartList")
    public Result addItemToCartList(Long itemId, Integer num){
        Result result = Result.fail("加入购物车失败");
        try {
            //判断用户是否已经登录；
            //1. 获取购物车列表；
            List<Cart> cartList = findCartList();
            //2. 调用业务方法将商品加入购物车列表；
            List<Cart> newCartList = cartService.addItemToCartList(cartList, itemId, num);

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if ("anonymousUser".equals(username)) {
                //未登录；将购物车存入cookie

                //3. 将最新的购物车列表更新回cookie；
                CookieUtils.setCookie(request, response, COOKIE_CART_LIST,
                        JSONArray.toJSONString(newCartList), COOKIE_CART_MAX_AGE, true);
            } else {
                //已登录；将购物车存入redis
            }
            result = Result.ok("加入购物车成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

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
