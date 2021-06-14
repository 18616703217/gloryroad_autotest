package com.gloryroad.demo.Vo;

import java.util.List;

public class PageModel<T> {
    /** 排序字段 */
    public String sort = "createTime";
    /** 排序方向 */
    public boolean order = true;
    /** 分页页码 */
    private int pageNo = 1;
    /** 每页条数 */
    private int pageSize = 10;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean isOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }
    protected List<T> result = null;
    protected long totalItems = -1L;


    public List<T> getResult() {
        return this.result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public long getTotalItems() {
        return this.totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }
}
