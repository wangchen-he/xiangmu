package com.pmcc.core.utils;

import com.pmcc.core.constant.ErrorCode;
import com.pmcc.exception.BusinessRunTimeException;
import org.slf4j.Logger;

/**
 * @ClassName: LogException <br>
 * @Description: 封装日志打印
 * @Date: 2020/3/9 12:41 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class LogException {
    public static void readFail(Logger logger, Exception e) {
        logger.error("异常码[{}],异常提示[{}],异常[{}]",
                ErrorCode.DATA_READ_FAIL_CODE, ErrorCode.DATA_READ_FAIL_MSG,e);
        throw new BusinessRunTimeException(ErrorCode.DATA_READ_FAIL_CODE,
                ErrorCode.DATA_READ_FAIL_MSG);
    }

    public static void writeFail(Logger logger, Exception e) {
        logger.error("异常码[{}],异常提示[{}],异常[{}]",
                ErrorCode.DATA_WRITE_FAIL_CODE,ErrorCode.DATA_WRITE_FAIL_MSG,e);
        throw new BusinessRunTimeException(ErrorCode.DATA_WRITE_FAIL_CODE,
                ErrorCode.DATA_WRITE_FAIL_MSG);
    }
}
