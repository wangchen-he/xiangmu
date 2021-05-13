package com.pmcc.core.Interceptors;

import com.pmcc.core.api.ApiResult;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.system.utils.FilterHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @ClassName: ApiResultInterceptor <br>
 * @Description: TODO 接口返回拦截器
 * @Date: 2019/12/15 12:13 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@ControllerAdvice(annotations = RestController.class)
public class ApiResultInterceptor implements ResponseBodyAdvice {

    //包含项
    private String[] includes = {};
    //排除项
    private String[] excludes = {};
    //是否加密
    private boolean encode = true;

    private ThreadLocal<ObjectMapper> mapperThreadLocal = ThreadLocal.withInitial(ObjectMapper::new);

    private static final Class[] annos = {
            RequestMapping.class,
            GetMapping.class,
            PostMapping.class,
            DeleteMapping.class,
            PutMapping.class
    };

    /**
     * 判断哪些需要拦截
     * 对所有RestController的接口方法进行拦截
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        AnnotatedElement element = returnType.getAnnotatedElement();
        return Arrays.stream(annos).anyMatch(anno -> anno.isAnnotation() && element.isAnnotationPresent(anno));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType, Class aClass, ServerHttpRequest request, ServerHttpResponse response) {
        Object out;
        ObjectMapper mapper = mapperThreadLocal.get();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

//        //获取注解配置的包含和去除字段
//        if(returnType.getMethod().isAnnotationPresent(SerializedField.class)){
//            SerializedField serializedField = returnType.getMethodAnnotation(SerializedField.class);
//            includes = serializedField.includes();
//            excludes = serializedField.excludes();
//            //是否加密
//            encode = serializedField.encode();
//        }


//        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(SysUser.class, "creater.password");
//        String json = JSON.toJSONString(body);
//        String str = JSON.toJSONString(body, filter);


        if (body instanceof ApiResult) {
            //返回类容是ApiResult类型 则直接返回
            out = body;
        } else if (body instanceof ErrorCode) {
            //如果返回内容是错误编号 则封装成ApiResult类型数据返回
            ErrorCode errorCode = (ErrorCode) body;
            out = new ApiResult(errorCode.getCode(), errorCode.getMsg(), null);
        }
        else if (body instanceof String) {
            //如果返回是字符串 则封装成ApiResult类型数据返回
            ApiResult result = ApiResult.success(body);
            try {
                //因为是String类型，我们要返回Json字符串，否则SpringBoot框架会转换出错
                out = mapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                out = ApiResult.fail(ErrorCode.JSON_PARSE_ERROR_CODE, e.getMessage());
            }
        }
//        else if (body instanceof List){
//            //List
//            List list = (List)body;
//            out = handleList(list);
//        }else{
//            //Single Object
//            out = handleSingleObject(body);
//        }

        else {
            out = ApiResult.success(body);
        }

        return out;
    }


    /**
     * 处理返回值是单个enity对象
     *
     * @param o
     * @return
     */
    private Object handleSingleObject(Object o){
        Map<String,Object> map = new HashMap<String, Object>();

        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field:fields){
            //如果未配置表示全部的都返回
            if(includes.length==0 && excludes.length==0){
                String newVal = getNewVal(o, field);
                map.put(field.getName(), newVal);
            }else if(includes.length>0){
                //有限考虑包含字段
                if(FilterHelper.isStringInArray(field.getName(), includes)){
                    String newVal = getNewVal(o, field);
                    map.put(field.getName(), newVal);
                }
            }else{
                //去除字段
                if(excludes.length>0){
                    if(!FilterHelper.isStringInArray(field.getName(), excludes)){
                        String newVal = getNewVal(o, field);
                        map.put(field.getName(), newVal);
                    }
                }
            }

        }
        return map;
    }

    /**
     * 处理返回值是列表
     *
     * @param list
     * @return
     */
    private List handleList(List list){
        List retList = new ArrayList();
        for (Object o:list){
            Map map = (Map) handleSingleObject(o);
            retList.add(map);
        }
        return retList;
    }

    /**
     * 获取加密后的新值
     *
     * @param o
     * @param field
     * @return
     */
    private String getNewVal(Object o, Field field){
        String newVal = "";
        try {
            field.setAccessible(true);
            Object val = field.get(o);

            if(val!=null){
                if(encode){
                    newVal = FilterHelper.encode(val.toString());
                }else{
                    newVal = val.toString();
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return newVal;
    }
}
