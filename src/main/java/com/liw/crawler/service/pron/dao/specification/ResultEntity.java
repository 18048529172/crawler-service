package com.liw.crawler.service.pron.dao.specification;

import java.io.Serializable;
import java.util.List;

public class ResultEntity<T> implements Serializable {

    private List<T> rows;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

}
