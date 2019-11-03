package com.wms.vo.form.pda;

import com.wms.constant.Constant;

public class PageForm {

    private String version;

    private int pageNum;

    private int start;

    private int pageSize;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getPageSize() {
        return Constant.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {

        if (pageNum <= 0) return 1;
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getStart() {
        return (getPageNum() - 1) * getPageSize();
    }

    public void setStart(int start) {
        this.start = start;
    }
}
