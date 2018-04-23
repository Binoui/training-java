package com.excilys.formation.cdb.pagination;

import java.util.LinkedList;
import java.util.List;

import com.excilys.formation.cdb.services.ServiceException;

public abstract class Page<T> {

    protected int pageSize = 10;

    protected int pageNumber;
    protected List<T> elements;

    public Page() {
        pageNumber = 0;
    }

    public Page(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public abstract int getLastPageNumber() throws ServiceException;

    public List<T> getPage() throws ServiceException {
        refresh();
        return elements;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<T> goToFirst() throws ServiceException {
        pageNumber = 0;
        refresh();
        return elements;
    }

    public List<T> goToLast() throws ServiceException {
        pageNumber = getLastPageNumber() - 1;
        refresh();
        return elements;
    }

    public List<T> goToPage(int pageNumber) throws ServiceException {
        if ((pageNumber < 0) || (pageNumber >= getLastPageNumber())) {
            return new LinkedList<>();
        }

        this.pageNumber = pageNumber;
        refresh();

        return elements;
    }

    public List<T> next() throws ServiceException {
        if (pageNumber < getLastPageNumber()) {
            pageNumber++;
            refresh();
        }

        return elements;
    }

    public List<T> previous() throws ServiceException {
        if (pageNumber > 0) {
            pageNumber--;
            refresh();
        }

        return elements;
    }

    public abstract void refresh() throws ServiceException;

    public void setPageSize(int pageSize) throws ServiceException {
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }

        refresh();
    }
}
