package com.xxx.rabbitmqspringboot.Controller;


import com.xxx.rabbitmqspringboot.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.MacSpi;
import java.util.Date;

/**
 * @author zhuyuxuan
 * 2022/7/23/10:38
 * @description: 生产者代码
 */
@Slf4j
@RequestMapping("ttl")
@RestController
public class SendMsgController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("sendMsg")
    public Result sendMsg(@RequestParam("message") String message) {
        log.info("当前时间：{},发送一条信息给两个 TTL 队列:{}", new Date(), message);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自 ttl 为 10S 的队列: " + message);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自 ttl 为 40S 的队列: " + message);
        return new Result(false, 200, "成功接收: " + message);
    }

    //
    @GetMapping("sendMsgTTL")
    public Result sendMsgTTL(@RequestParam("message") String message, @RequestParam("ttl") String ttl) {
        log.info("当前时间：{},发送一条时时长是{}毫秒的信息给一个TTL 队列:{}", new Date(), ttl, message);
        rabbitTemplate.convertAndSend("X", "XC", "消息来自 ttl 的队列: "
                + message, Msg -> {
            Msg.getMessageProperties().setExpiration(ttl);
            return Msg;
        });
        return new Result(false, 200, "成功接收: " + message);
    }

    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";

    @GetMapping("sendDelayMsg/{message}/{delayTime}")
    public void sendDelayMsg(@PathVariable String message, @PathVariable Integer delayTime) {
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, message,
                correlationData -> {
                    correlationData.getMessageProperties().setDelay(delayTime);
                    return correlationData;
                });
        log.info(" 当 前 时 间 ： {}, 发 送 一 条 延 迟 {} 毫秒的信息给队列 delayed.queue:{}", new Date(), delayTime, message);
    }

    //开始发消息,测试一旦交换机接收不到消息

}