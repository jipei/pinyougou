package com.pinyougou.search.activemq.listener;

import com.alibaba.fastjson.JSONArray;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.adapter.AbstractAdaptableMessageListener;

import javax.jms.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 需要接收来自运营商管理系统发送过来的商品spu id消息之后删除solr中的商品数据。
 */
public class ItemDeleteMessageListener extends AbstractAdaptableMessageListener {

    @Autowired
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(Message message, Session session) throws JMSException {

        //1、接收并转换消息
        ObjectMessage objectMessage = (ObjectMessage) message;
        Long[] goodsIds = (Long[]) objectMessage.getObject();

        //2、同步solr数据
        itemSearchService.deleteByGoodsIds(Arrays.asList(goodsIds));
    }
}
