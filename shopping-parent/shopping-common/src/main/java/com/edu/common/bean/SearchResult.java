package com.edu.common.bean;

import java.util.List;

/**
 * 这是用来封装搜索信息的bean
 */
public class SearchResult {
    private int page ;// 当前页
    private long rowCount ;// 总记录数
    private long pageCount ;// 总页数
    private List<Item> itemList ;// 搜索到域的信息

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getRowCount() {
        return rowCount;
    }

    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
