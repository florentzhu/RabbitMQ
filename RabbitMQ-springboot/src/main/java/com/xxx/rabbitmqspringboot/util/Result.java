package com.xxx.rabbitmqspringboot.util;

/**
 * @author zhuyuxuan
 * 2022/7/1/10:50
 */

import java.io.Serializable;

/**
 * 结果集封装
 * @author dsc
 */
public class Result implements Serializable {
    /**
     * 是否成功
     */
    private boolean flag;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String message;



    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result() {
        this.flag = true;
        this.code = 200;
        this.message = "操作成功!";
    }




    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}


