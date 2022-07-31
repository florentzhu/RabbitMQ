package com.xxx.rabbitmqspringboot.Config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuyuxuan
 * 2022/7/23/20:58
 * @description:
 */
@Configuration
public class ConfirmConfig {
    //交换机
    public static final String CONFIRM_EXCHAANGE_NAME = "confirm_exchange";
    //队列
    public static final String CONFIRM_QUEUE_NAME = "confirm_queue";
    //RoutingKey
    public static final String CONFIRM_ROUTINGKEY = "confirm_key";
    //声明交换机
    @Bean
    public DirectExchange confirmExchange(){
        return new DirectExchange(CONFIRM_EXCHAANGE_NAME);
    }
    //声明队列
    @Bean
    public Queue confirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }
    //绑定
    @Bean
    public Binding queueBindingExchange(@Qualifier("confirmQueue") Queue queue,
                         @Qualifier("confirmExchange") DirectExchange Exchange
    ){
        return BindingBuilder.bind(queue).to(Exchange).with(CONFIRM_ROUTINGKEY);
    }


}
