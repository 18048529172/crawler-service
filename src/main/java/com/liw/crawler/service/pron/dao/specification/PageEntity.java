package com.liw.crawler.service.pron.dao.specification;

import java.io.Serializable;
import java.util.List;

public class PageEntity<T> implements Serializable{

    private List<T> rows;

    private long totalPage;

    private long total;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
