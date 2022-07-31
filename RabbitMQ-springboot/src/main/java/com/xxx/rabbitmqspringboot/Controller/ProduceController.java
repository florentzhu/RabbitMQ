package com.xxx.rabbitmqspringboot.Controller;

import com.xxx.rabbitmqspringboot.Callback.MyCallBack;
import com.xxx.rabbitmqspringboot.Config.ConfirmConfig;
import com.xxx.rabbitmqspringboot.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author zhuyuxuan
 * 2022/7/23/21:24
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/confirm")
public class ProduceController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //发消息
    @GetMapping("sendMsg")
    public Result sengMsg(@RequestParam("message") String Msg){
        //发消息1
        CorrelationData correlationData = new CorrelationData("1111");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHAANGE_NAME+ "2",
                ConfirmConfig.CONFIRM_ROUTINGKEY,Msg,correlationData);
        log.info("发送的消息是 : {}" , Msg);
        //发消息2
        CorrelationData correlationData2 = new CorrelationData("222");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHAANGE_NAME,
                ConfirmConfig.CONFIRM_ROUTINGKEY + "2",Msg,correlationData2);
        log.info("发送的消息是 : {}" , Msg);
        return new Result(false,200,Msg);
    }
}
