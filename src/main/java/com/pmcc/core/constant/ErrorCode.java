package com.pmcc.core.constant;

/**
 * @ClassName: ErrorCode <br>
 * @Description: TODO 返回错误代码
 * @Date: 2019/12/15 12:10 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class ErrorCode {

    /**
     * 正常返回/操作成功
     **/
    public static final int SUCCESS_CODE = 200;
    public static final String SUCCESS_MSG = "操作成功";

    public static final int PERMISSION_CODE = 211;
    public static final String PERMISSION_MSG = "权限不足";

    /**
     * 数据查询异常
     */
    public static final int DATA_READ_FAIL_CODE = 300;
    public static final String DATA_READ_FAIL_MSG = "数据查询异常";
    /**
     * 数据写入异常
     */
    public static final int DATA_WRITE_FAIL_CODE = 301;
    public static final String DATA_WRITE_FAIL_MSG = "数据写入异常";
    /**
     * 中文名重复
     */
    public static final int DATA_WRITE_CNAME_CODE = 302;
    public static final String DATA_WRITE_CNAME_MSG = "中文名已存在";
    /**
     * 英文名重复
     */
    public static final int DATA_WRITE_ENAME_CODE = 303;
    public static final String DATA_WRITE_ENAME_MSG = "英文名已存在";
    /**
     * 编码重复
     */
    public static final int DATA_WRITE_CODE_CODE = 304;
    public static final String DATA_WRITE_CODE_MSG = "编码已存在";
    /**
     * 系统运行时未知错误
     **/
    public static final int SERVICE_SYSTEM_ERROR_CODE = 500;
    public static final String SERVICE_SYSTEM_ERROR_MSG = "未知异常";

    /**
     * 删除操作被拒绝，请联系管理员
     **/
    public static final int DELETE_REFUSED_CODE = 600;
    public static final String DELETE_REFUSED_MSG = "删除操作被拒绝，请联系管理员";
    /**
     * 检测到存在依赖数据，是否强制删除？
     **/
    public static final int DELETE_FORCE_CONFIRM_CODE = 601;
    public static final String DELETE_FORCE_CONFIRM_MSG = "检测到存在依赖数据，是否强制删除？";

    public static final int AUTH_ERROR_CODE = 10001;
    public static final String AUTH_ERROR_MSG = "认证失败";

    public static final int PARAMS_ERROR_CODE = 10002;
    public static final String PARAMS_ERROR_MSG = "参数错误";

    public static final int JSON_PARSE_ERROR_CODE = 10003;
    public static final String JSON_PARSE_ERROR_MSG = "Json解析错误";

    public static final int ILLEAGAL_STRING_CODE = 15001;
    public static final String ILLEAGAL_STRING_MSG = "非法字符串";

    public static final int UNKNOW_ERROR_CODE = 16000;
    public static final String UNKNOW_ERROR_MSG = "未知错误";

    /**
     * 用户信息
     * type = 5
     * */
    //添加用户信息失败
    public static final int USER_ADD_FAILED_CODE = 500000;
    public static final String USER_ADD_FAILED_MSG = "添加用户信息失败";
    //删除用户信息失败
    public static final int USER_DELETE_FAILED_CODE = 500001;
    public static final String USER_DELETE_FAILED_MSG = "删除用户信息失败";
    //修改用户信息失败
    public static final int USER_EDIT_FAILED_CODE = 500002;
    public static final String USER_EDIT_FAILED_MSG = "修改用户信息失败";
    //用户名已存在
    public static final int USER_USER_NAME_ALREADY_EXISTS_CODE = 500003;
    public static final String USER_USER_NAME_ALREADY_EXISTS_MSG = "用户名在本系统已存在";
    //登录名已存在
    public static final int USER_LOGIN_NAME_ALREADY_EXISTS_CODE = 500003;
    public static final String USER_LOGIN_NAME_ALREADY_EXISTS_MSG = "登录名在本系统已存在";
    //用户录入数量超出限制
    public static final int USER_OVER_LIMIT_FAILED_CODE = 500004;
    public static final String USER_OVER_LIMIT_FAILED_MSG = "用户录入数量超出限制，请联系管理员续费";
    //此用户名限制使用
    public static final int USER_NAME_LIMIT_USE_CODE = 500004;
    public static final String USER_NAME_LIMIT_USE_MSG = "此用户名限制使用";


    private int code;
    private String msg;

    ErrorCode(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
