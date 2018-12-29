package cn.itcast.springboot.activemq.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessageListener {

    /**
     * 接收某个模式的消息
     * destination 为模式的名称
     * @param map 消息
     */
    @JmsListener(destination = "spring.boot.map.queue")
    public void receiveMsg(Map<String, Object> map){
        System.out.println(map);
    }
}
