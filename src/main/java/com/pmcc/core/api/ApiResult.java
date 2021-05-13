package com.pmcc.core.api;

import com.pmcc.core.constant.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ApiResult <br>
 * @Description: TODO 接口返回数据格式
 * @Date: 2019/12/14 11:21 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Getter
@Setter
public class ApiResult {
    /**
     * 如果是成功，则code为200
     */
    private int code;
    /**
     * 对错误的具体解释
     */
    private String message;
    /**
     * 返回的结果包装在value中，value可以是单个对象
     */
    private Object data;

    public ApiResult(int code){
        this.code = code;
    }


    public ApiResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 通用返回消息
     * @param code
     * @return
     */
    public static ApiResult api(int code){
        ApiResult jsonData = new ApiResult(code);
        switch(code){
            case ErrorCode.SUCCESS_CODE : //操作成功
                jsonData.message = ErrorCode.SUCCESS_MSG;
                break;
            case ErrorCode.PERMISSION_CODE : //权限不足
                jsonData.message = ErrorCode.PERMISSION_MSG;
                break;
            case ErrorCode.DATA_READ_FAIL_CODE : //数据查询异常
                jsonData.message = ErrorCode.DATA_READ_FAIL_MSG;
                break;
            case ErrorCode.DATA_WRITE_FAIL_CODE : //数据写入异常
                jsonData.message = ErrorCode.DATA_WRITE_FAIL_MSG;
                break;
            case ErrorCode.SERVICE_SYSTEM_ERROR_CODE : //未知异常
                jsonData.message = ErrorCode.SERVICE_SYSTEM_ERROR_MSG;
                break;
            case ErrorCode.DELETE_FORCE_CONFIRM_CODE : //检测到存在依赖数据
                jsonData.message = ErrorCode.DELETE_FORCE_CONFIRM_MSG;
                break;
            case ErrorCode.AUTH_ERROR_CODE : //认证失败
                jsonData.message = ErrorCode.AUTH_ERROR_MSG;
                break;
            case ErrorCode.ILLEAGAL_STRING_CODE : //非法字符串
                jsonData.message = ErrorCode.ILLEAGAL_STRING_MSG;
                break;
            default :
                jsonData.message = ErrorCode.UNKNOW_ERROR_MSG;
        }
        return jsonData;
    }


    public static ApiResult success(Object object, String message) {
        ApiResult jsonData = new ApiResult(ErrorCode.SUCCESS_CODE);
        jsonData.data = object;
        jsonData.message = message;
        return jsonData;
    }

    public static ApiResult success(Object object) {
        ApiResult jsonData = new ApiResult(ErrorCode.SUCCESS_CODE);
        jsonData.data = object;
        jsonData.message = ErrorCode.SUCCESS_MSG;
        return jsonData;
    }

    public static ApiResult success(String message) {
        ApiResult jsonData = new ApiResult(ErrorCode.SUCCESS_CODE);
        jsonData.message = message;
        return jsonData;
    }

    public static ApiResult success() {
        ApiResult jsonData = new ApiResult(ErrorCode.SUCCESS_CODE);
        jsonData.message = ErrorCode.SUCCESS_MSG;
        return jsonData;
    }

    public static ApiResult fail(String message) {
        ApiResult jsonData = new ApiResult(ErrorCode.UNKNOW_ERROR_CODE);
        jsonData.message = ErrorCode.UNKNOW_ERROR_MSG;
        return jsonData;
    }

    public static ApiResult fail(int code, String message) {
        ApiResult jsonData = new ApiResult(code);
        jsonData.message = message;
        return jsonData;
    }

    public Map<String, Object> toMap() {
        HashMap<String,Object> result = new HashMap<String, Object>();
        result.put("code", code);
        result.put("message", message);
        result.put("data", data);
        return result;
    }
}
