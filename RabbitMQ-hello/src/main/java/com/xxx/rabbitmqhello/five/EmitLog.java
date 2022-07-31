package com.xxx.rabbitmqhello.five;

import com.rabbitmq.client.Channel;
import com.xxx.rabbitmqhello.utis.RabbitMQUtils;

import java.util.Scanner;

/**
 * @author zhuyuxuan
 * 2022/7/22/11:39
 * @description:
 *     发布订阅模式
 */

public class EmitLog {
    public static final String EXCHANGE_NAME = "logs1";

    public static void main(String[] argv) throws Exception {
        try (Channel channel = RabbitMQUtils.getChannel()) {

            Scanner sc = new Scanner(System.in);
            System.out.println("请输入信息");
            while (sc.hasNext()) {
                String message = sc.nextLine();
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
                System.out.println("生产者发出消息" + message);
            }
        }
    }
}