package com.xxx.rabbitmqspringboot.Callback;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zhuyuxuan
 * 2022/7/24/14:56
 * @description: 交换机的确认回调方法
 * 1、发消息,交换机收到了
 * 1.1、correlationData 保存的是消息的ID和相关信息
 * 1.2、交换机收到消息  ack = true
 * 1.3、cause null
 * 2、发消息,交换机接收失败
 * 2.1、correlationData 保存的是消息的ID和相关信息
 * 2.2、交换机收到消息  ack = false
 * 2.3、cause 失败的原因
 */
@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    /**
     * 交换机不管是否收到消息的一个回调方法
     * CorrelationData
     * 消息相关数据
     * ack
     * 交换机是否收到消息
     */

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //rabbitTemplate 注入之后就设置该值
    @PostConstruct
    private void init() {
        rabbitTemplate.setConfirmCallback(this);
/**
 * true： * 交换机无法将消息进行路由时，会将该消息返回给生产者
 * false： * 如果发现消息无法进行路由，则直接丢弃
 */
        rabbitTemplate.setMandatory(true);
//设置回退消息交给谁处理
        rabbitTemplate.setReturnCallback(this);
    }


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {


        String id = correlationData != null ? correlationData.getId() : "";
        System.out.println(id);
        if (ack) {
            log.info("交换机已经收到 id 为:{}的消息", id);
        } else {
            log.info("交换机还未收到 id 为:{}消息,由于原因:{}", id, cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String
            exchange, String routingKey) {
        log.error(" 消 息 {}, 被 交 换 机 {} 退 回 ， 退 回 原 因 :{}, 路 由 key:{}", new
                String(message.getBody()), exchange, replyText, routingKey);
    }
}