package com.edu.common.bean;

import java.util.List;

/**
 * 这是easyui datagrid组件中封装的数据
 */
public class EUDataGridResult {
    private Long total;
    private List<?> rows ;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
