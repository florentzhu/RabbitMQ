package com.xxx.rabbitmqhello.one;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhuyuxuan
 * 2022/7/5/22:36
 * @description:
 */
class ConsumerTest {
    public static void main(String[] args) {
        Thread a = new Thread() {
            public void run() {
                throw new RuntimeException("我是帅哥");
            }
        };
        try {
            a.start();
        } catch (Exception e) {
            System.out.println("接收: " + e.getMessage());
        }
    }
}