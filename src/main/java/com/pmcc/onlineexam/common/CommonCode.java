package com.pmcc.onlineexam.common;

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

        /**
         * 各个id对应
         */
        public static  final  String adminid="000000000000000000j1";  //超级用户标识的组织id
        public static final  String  workid="e645e783cf6f4eaaa61f7c6993252d80"; //工种id

        /**
         * 单选多选**/
        public static  final  String Radino="0";  //单选
        public static final  String  Multiple="1"; //多选
        public static final  String  Judge="2"; //判断

        /**
         * 题型难度
         * **/
        public static final String Simpleness="0";  //简单
        public static final String Ordinary="1";  //普通
        public static final String Diff="2";  //困难


        /**
         * 正确错误
        * */
        public static final String Fl="0";
        public static final String Tr="1";

}
