package com.pmcc.system.utils;

import java.util.HashMap;

/**
 * @ClassName: JsonMessage <br>
 * @Description: TODO封装统一返回结果结构对象
 * @Date: 2019/12/7 11:46 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class JsonMessage extends HashMap<String, Object> {

    private static final long serialVersionUID = -7149712196874923440L;

    public JsonMessage() {
        this.put("status", 200);
    }

    public JsonMessage(boolean status) {
        putStatus(status);
    }

    public JsonMessage(String msg) {
        this.put("status", 200);
        this.put("msg", msg);
    }

    public JsonMessage(boolean status, String msg) {
        this.put("msg", msg);
        putStatus(status);
    }

    public JsonMessage(String key, Object object) {
        this.put("status", 200);
        this.put(key, object);
    }

    public JsonMessage(boolean status, String msg, String key, Object value) {
        this.put("msg", msg);
        putStatus(status);
        this.put(key, value);
    }

    public JsonMessage putStatusAndMsg(int code, String msg) {
        this.put("status", code);
        this.put("msg", msg);
        return this;
    }

    public JsonMessage putStatusAndMsg(boolean status, String msg) {
        putStatus(status);
        this.put("msg", msg);
        return this;
    }

    public JsonMessage putStatus(int code) {
        this.put("status", code);
        return this;
    }

    public JsonMessage putStatus(boolean status) {
        if (status) {
            this.put("status", 200);
        } else {
            this.put("status", 500);
        }
        return this;
    }

    public JsonMessage putRedirectUrl(String redirectUrl) {
        this.put("url", redirectUrl);
        this.put("status", 501);
        return this;
    }

    public JsonMessage putMsg(String msg) {
        this.put("msg", msg);
        return this;
    }

    public JsonMessage put(String arg0, Object arg1) {
        super.put(arg0, arg1);
        return this;
    }

}
