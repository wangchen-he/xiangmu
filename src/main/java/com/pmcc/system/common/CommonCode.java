package com.pmcc.system.common;

/**
 * @Classname CommonCode
 * @Description TODO 静态常量
 * @Version 1.0
 */

public class CommonCode {

        /*** 冻结*/
        public static final Integer STATUS_DISABLE = 0;
        /*** 正常*/
        public static final Integer STATUS_NORMAL = 1;
        /*** 删除*/
        public static final Integer DEL_FLAG = 2;

        /**
         * 审核状态（0未审核, 1通过，2未通过）
         */
        public static final Integer UN_AUDITO = 0;
        public static final Integer AUDITO = 1;
        public static final Integer NO_AUDITO = 2;

        /**
         * 提交状态（0未提交，1已提交）
         */
        public static final Integer SUBMIT = 1;
        public static final Integer UN_SUBMIT = 0;

        /**
         * 领取状态（0未领取 1部分领取 2领取完成）
         */
        public static final Integer RECEIVE = 0;
        public static final Integer RECEIVE_PART = 1;
        public static final Integer RECEIVE_ALL = 2;
        public static final Integer RECEIVE_PURCHASE = 3;




}
