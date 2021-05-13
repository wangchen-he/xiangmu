package com.pmcc.core.filters;

import java.lang.annotation.*;

/**
 * @ClassName: SerializedField <br>
 * @Description: TODO过滤
 * @Date: 2020/3/24 10:23 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SerializedField {

    /**
     * 过滤开始的根类型
     * @return
     */
    Class<?> type();

    /**
     * 需要返回的字段
     * @return
     */
    String[] includes() default {};

    /**
     * 需要去除的字段
     * @return
     */
    String[] excludes() default {};

    /**
     * 数据是否需要加密
     * @return
     */
    boolean encode() default true;
}
