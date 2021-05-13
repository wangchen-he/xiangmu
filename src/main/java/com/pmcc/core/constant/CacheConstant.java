package com.pmcc.core.constant;

/**
 * @ClassName: CacheConstant <br>
 * @Description: TODO 缓存常量
 * @Date: 2019/12/14 11:14 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class CacheConstant {

    /**
     * 字典信息缓存
     */
    public static final String SYS_DICT_CACHE = "sys:cache:dict";
    /**
     * 表字典信息缓存
     */
    public static final String SYS_DICT_TABLE_CACHE = "sys:cache:dictTable";

    /**
     * 数据权限配置缓存
     */
    public static final String SYS_DATA_PERMISSIONS_CACHE = "sys:cache:permission:dataRules";

    /**
     * 缓存用户信息
     */
    public static final String SYS_USERS_CACHE = "sys:cache:user";

    /**
     * 全部部门信息缓存
     */
    public static final String SYS_DEPARTS_CACHE = "sys:cache:depart:allDep";


    /**
     * 全部部门ids缓存
     */
    public static final String SYS_DEPART_IDS_CACHE = "sys:cache:depart:allDepID";


    /**
     * 测试缓存key
     */
    public static final String TEST_DEMO_CACHE = "test:demo";
}
