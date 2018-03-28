package com.excilys.formation.cdb.pagination;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;

public class ComputerListPage extends Page<Computer> {

    private static ComputerService service = ComputerService.INSTANCE;

    public ComputerListPage() throws ServiceException {
        super();
    }

    public ComputerListPage(int pageNumber) throws ServiceException {
        super(pageNumber);
    }

    @Override
    public int getLastPageNumber() throws ServiceException {
        return service.getListComputersPageCount(pageSize);
    }

    @Override
    public void refresh() throws ServiceException {
        elements = service.getListComputers(pageNumber, pageSize);
    }

    public int getListComputersPageCount(int pageSize) throws ServiceException {
        return service.getListComputersPageCount(pageSize);
    }

    public int getComputerCount() throws ServiceException {
        return service.getComputerCount();
    }

}
