package com.excilys.formation.cdb.pagination;

import com.excilys.formation.cdb.dao.SortableComputerColumn;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;

public class ComputerListPage extends Page<Computer> {

    private static ComputerService service = ComputerService.INSTANCE;

    protected SortableComputerColumn column;
    protected boolean ascendingSort;

    public ComputerListPage() throws ServiceException {
        super();
        column = SortableComputerColumn.ID;
        ascendingSort = true;
    }

    public ComputerListPage(int pageNumber) throws ServiceException {
        super(pageNumber);
        column = SortableComputerColumn.ID;
        ascendingSort = true;
    }

    public SortableComputerColumn getColumn() {
        return column;
    }

    public int getComputerCount() throws ServiceException {
        return service.getComputerCount();
    }

    @Override
    public int getLastPageNumber() throws ServiceException {
        return service.getListComputersPageCount(pageSize);
    }

    public int getListComputersPageCount(int pageSize) throws ServiceException {
        return service.getListComputersPageCount(pageSize);
    }

    public boolean isAscendingSort() {
        return ascendingSort;
    }

    @Override
    public void refresh() throws ServiceException {
        elements = service.getListComputers(pageNumber, pageSize, column, ascendingSort);
    }

    public void setAscendingSort(boolean ascendingSort) {
        this.ascendingSort = ascendingSort;
    }

    public void setColumn(SortableComputerColumn column) {
        this.column = column;
    }

}
