package com.pmcc.core.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: BaseController <br>
 * @Description: TODO 基础控制器
 * @Date: 2019/12/14 20:50 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class BaseController {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 返回JSON字符串
     *
     * @param response
     * @param object
     * @return
     * @return
     */
    protected void printString(HttpServletResponse response, Object object) {
//        printString(response, JSON.toJSONString(object));
    }

}
