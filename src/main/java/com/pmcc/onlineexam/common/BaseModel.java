package com.pmcc.onlineexam.common;
/**
 * @ClassName: BaseModel
 * @Description:
 * @Author: 94105
 * @Date: 2021/4/6 10:18
 */

import java.io.Serializable;

public class BaseModel implements Serializable {
    private int page = 1;

    private int rows = 10;

    private String sort;

    private String order;

    private int initPage;

    private String topA;

    private String topB;

    public String getTopA() {
        String top = "";
        if (getInitPage() == 0)
            top = "top " + getRows();
        return top;
    }

    public String getTopB() {
        String top = "";
        if (getInitPage() == 0)
            top = "top " + (getPage() * getRows());
        return top;
    }

    public void setTopB(String topB) {
        this.topB = topB;
    }

    public void setTopA(String topA) {
        this.topA = topA;
    }

    public int getInitPage() {
        return this.initPage;
    }

    public void setInitPage(int initPage) {
        this.initPage = initPage;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
