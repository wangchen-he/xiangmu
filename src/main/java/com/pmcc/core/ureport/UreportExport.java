package com.pmcc.core.ureport;

import com.bstek.ureport.export.ExportConfigure;
import com.bstek.ureport.export.ExportManager;
import com.bstek.ureport.export.html.HtmlReport;

import java.util.Map;

/**
 * @Classname UreportExport
 * @Description TODO
 * @Version 1.0
 */

public class UreportExport implements ExportManager {

    /**
     * 导出Html报表
     * @param file 报表模版文件名
     * @param contextPath 当前项目的context path
     * @param parameters 参数
     * @return 返回一个HtmlReport对象，里面有报表产生的HTML及相关CSS
     */
    @Override
    public HtmlReport exportHtml(String s, String s1, Map<String, Object> map) {
        return null;
    }

    /**
     * 导出指定页码的Html报表
     * @param file 报表模版文件名
     * @param contextPath 当前项目的context path
     * @param parameters 参数
     * @param pageIndex 页码
     * @return 返回一个HtmlReport对象，里面有报表产生的HTML及相关CSS
     */
    @Override
    public HtmlReport exportHtml(String s, String s1, Map<String, Object> map, int i) {
        return null;
    }

    /**
     * 导出PDF报表
     * @param config 包含报表模版文件名、参数等信息的配置对象
     */
    @Override
    public void exportPdf(ExportConfigure exportConfigure) {

    }

    /**
     * 不分页导出Excel
     * @param config 包含报表模版文件名、参数等信息的配置对象
     */
    @Override
    public void exportExcel(ExportConfigure exportConfigure) {

    }

    @Override
    public void exportExcel97(ExportConfigure exportConfigure) {

    }

    /**
     * 分页导出Excel
     * @param config 包含报表模版文件名、参数等信息的配置对象
     */
    @Override
    public void exportExcelWithPaging(ExportConfigure exportConfigure) {

    }


    @Override
    public void exportExcel97WithPaging(ExportConfigure exportConfigure) {

    }


    /**
     * 分页分Sheet导出Excel
     * @param config 包含报表模版文件名、参数等信息的配置对象
     */
    @Override
    public void exportExcelWithPagingSheet(ExportConfigure exportConfigure) {

    }


    @Override
    public void exportExcel97WithPagingSheet(ExportConfigure exportConfigure) {

    }

    /**
     * 导出Word
     * @param config 包含报表模版文件名、参数等信息的配置对象
     */
    @Override
    public void exportWord(ExportConfigure config) {

    }
}
