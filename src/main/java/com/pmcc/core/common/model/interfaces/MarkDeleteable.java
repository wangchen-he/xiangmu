package com.pmcc.core.common.model.interfaces;

/**
 * @ClassName: MarkDeleteable <br>
 * @Description: TODO(约定删除标识列名为delflag)
 * @Date: 2019/11/3 14:14 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public interface MarkDeleteable {
    /**
     * 删除标记（0：正常；1：删除 ）
     */
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";

    public String getDelFlag();

    public void setDelFlag(String delFlag);

    /**
     * 这表数据是否需要标记删除
     *
     * @param isDelete
     */
    public void markDelete(Boolean isDelete);

    public Boolean markStatus();
}
