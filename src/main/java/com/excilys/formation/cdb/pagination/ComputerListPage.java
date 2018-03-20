package com.excilys.formation.cdb.pagination;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.ComputerService;

public class ComputerListPage extends Page<Computer> {

    private static ComputerService service = ComputerService.INSTANCE;

    public ComputerListPage() {
        super();
    }

    public ComputerListPage(int pageNumber) {
        super(pageNumber);
    }

    @Override
    public void refresh() {
        elements = service.getListComputers(pageNumber, pageSize);
    }

    @Override
    public int getLastPageNumber() {
        return service.getListComputersPageCount(pageSize);
    }

}
