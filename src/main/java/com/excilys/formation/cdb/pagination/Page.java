package com.excilys.formation.cdb.pagination;

import java.util.List;

public abstract class Page<T> {

    protected static int pageSize = 10;

    protected int pageNumber;
    protected List<T> elements;

    public Page() {
        pageNumber = 0;
        refresh();
    }

    public Page(int pageNumber) {
        this.pageNumber = pageNumber;
        refresh();
    }

    public abstract void refresh();

    public abstract int getLastPageNumber();

    public List<T> previous() {
        if (pageNumber > 0) {
            pageNumber--;
            refresh();
        }

        return elements;
    }

    public List<T> next() {
        if (pageNumber < getLastPageNumber()) {
            pageNumber++;
            refresh();
        }

        return elements;
    }

    public List<T> getPage() {
        return elements;
    }

    public List<T> goToPage(int pageNumber) {
        if ((pageNumber < 0) || (pageNumber >= getLastPageNumber())) {
            return null;
        }

        this.pageNumber = pageNumber;
        refresh();

        return elements;
    }

    public List<T> goToFirst() {
        pageNumber = 0;
        refresh();
        return elements;
    }

    public List<T> goToLast() {
        pageNumber = getLastPageNumber();
        refresh();
        return elements;
    }

    public void setPageSize(int pageSize) {
        if (pageSize > 0) {
            Page.pageSize = pageSize;
        }

        refresh();
    }
}
