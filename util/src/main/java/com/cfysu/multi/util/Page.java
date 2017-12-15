package com.cfysu.multi.util;

import java.util.Iterator;
import java.util.List;

public class Page<T> implements Iterable<T>{

    private int curPage = 1;// 当前页
    private int nextPage;// 下一页
    private int prePage;// 前一页
    private int totalRow;// 总条数
    private int pageSize = 10;// 每页条数
    private int totalPage;// 总页数
    private int start;// 开始条数
    private List<T> resultList;
    private int recordsFiltered;//DataTable插件默认字段
    private int recordsTotal;//DataTable插件默认字段

    public Page() {
    }

    public Page(int curPage) {
        this.curPage = curPage;
    }

    public Page(int curPage, int pageSize) {
        this.curPage = curPage;
        this.pageSize = pageSize;
    }

    public void repaginateByCurPage() {
        if(pageSize<=0){
            pageSize=10;
        }
        totalPage = (totalRow + pageSize - 1) / pageSize;
        if (totalRow == 0)
            curPage = 1;
        else if (curPage > totalPage)
            curPage = totalPage;

        if (curPage < 1) {
            curPage = 1;
        }
        nextPage = (curPage < totalPage ? curPage + 1 : totalPage);
        prePage = (curPage - 1 > 1 ? curPage - 1 : 1);
        start = (curPage - 1) * pageSize;
        if (totalRow == 0) {
            curPage = 1;
            totalPage = 1;
        }
        // if (currentPage > pageCount) currentPage = pageCount;
    }

    public void repaginateByStart(){
        if(pageSize<=0){
            pageSize=10;
        }
        totalPage = (totalRow + pageSize - 1) / pageSize;
        if (totalRow == 0)
            curPage = 1;
        else if (curPage > totalPage)
            curPage = totalPage;

        curPage = start/pageSize + 1;
        nextPage = (curPage < totalPage ? curPage + 1 : totalPage);
        prePage = (curPage - 1 > 1 ? curPage - 1 : 1);
        if (totalRow == 0) {
            curPage = 1;
            totalPage = 1;
        }
    }
    public void setResultList(List<T> elements) {
        if (elements == null)
            throw new IllegalArgumentException("'result' must be not null");
        this.resultList = elements;
    }

    /**
     * 当前页包含的数据
     *
     * @return 当前页数据源
     */
    public List<T> getResultList() {
        return resultList;
    }

    /**
     * 实现Iterable接口, 可以for(Object item : page)遍历使用
     */
    @Override
    public Iterator<T> iterator() {
        return resultList.iterator();
    }

    /**
     * 是否还有下一页.
     */
    public boolean hasNextPage() {
        return (getPageSize() + 1 <= getTotalPage());
    }

    /**
     * 是否最后一页.
     */
    public boolean isLastPage() {
        return !hasNextPage();
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
        if (curPage < 1) {
            this.curPage = 1;
        }
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
        this.recordsFiltered = totalRow;
        this.recordsTotal = totalRow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return (int) Math.ceil((double) totalRow / (double) getPageSize());
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = getTotalPage();
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }
}