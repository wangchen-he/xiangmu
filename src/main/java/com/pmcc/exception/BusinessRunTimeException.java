package com.pmcc.exception;

/**
 * @ClassName: BusinessRunTimeException <br>
 * @Description: TODO业务运行异常
 * @Date: 2020/3/9 12:45 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class BusinessRunTimeException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private int code;
    private String reason;

    public BusinessRunTimeException(int code, String reason) {
        super(reason);
        this.code = code;
        this.reason = reason;
    }

    public BusinessRunTimeException(int code, String reason, Throwable throwable) {
        super(reason, throwable);
        this.code = code;
        this.reason = reason;
    }
}
