package com.excilys.formation.cdb.pagination;

import com.excilys.formation.cdb.dao.SortableComputerColumn;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;

public class ComputerListPage extends Page<Computer> {

    private ComputerService computerService;

    protected SortableComputerColumn column;
    protected boolean ascendingSort;

    protected ComputerListPage() throws ServiceException {
        super();
        column = SortableComputerColumn.ID;
        ascendingSort = true;
    }
    
    protected ComputerListPage(int pageNumber) throws ServiceException {
        super(pageNumber);
        column = SortableComputerColumn.ID;
        ascendingSort = true;
    }
    
    public ComputerListPage(ComputerService computerService) throws ServiceException {
        super();
        column = SortableComputerColumn.ID;
        ascendingSort = true;
        this.computerService = computerService;
    }

    public ComputerListPage(int pageNumber, ComputerService computerService) throws ServiceException {
        super(pageNumber);
        column = SortableComputerColumn.ID;
        ascendingSort = true;
        this.computerService = computerService;
    }

    public SortableComputerColumn getColumn() {
        return column;
    }

    public int getComputerCount() throws ServiceException {
        return computerService.getComputerCount();
    }

    @Override
    public int getLastPageNumber() throws ServiceException {
        return computerService.getListComputersPageCount(pageSize);
    }

    public int getListComputersPageCount(int pageSize) throws ServiceException {
        return computerService.getListComputersPageCount(pageSize);
    }

    public boolean isAscendingSort() {
        return ascendingSort;
    }

    @Override
    public void refresh() throws ServiceException {
        elements = computerService.getListComputers(pageNumber, pageSize, column, ascendingSort);
    }

    public void setAscendingSort(boolean ascendingSort) {
        this.ascendingSort = ascendingSort;
    }

    public void setColumn(SortableComputerColumn column) {
        this.column = column;
    }

}
