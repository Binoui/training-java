package com.excilys.formation.cdb.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;

@Component
public class ComputerListPageSearch extends ComputerListPage {

    @Autowired
    private ComputerService service;

    private final String searchWord;

    public ComputerListPageSearch(int pageNumber, String searchWord) throws ServiceException {
        super(pageNumber);
        this.searchWord = searchWord;
    }

    public ComputerListPageSearch(String searchWord) throws ServiceException {
        super();
        this.searchWord = searchWord;
    }

    @Override
    public int getComputerCount() throws ServiceException {
        return service.getComputerCount(searchWord);
    }

    @Override
    public int getLastPageNumber() throws ServiceException {
        return service.getListComputersPageCount(pageSize, searchWord);
    }

    @Override
    public int getListComputersPageCount(int pageSize) throws ServiceException {
        return service.getListComputersPageCount(pageSize, searchWord);
    }

    @Override
    public void refresh() throws ServiceException {
        if (searchWord == null) {
            elements = service.getListComputers(pageNumber, pageSize, column, ascendingSort);
        } else {
            elements = service.getListComputers(pageNumber, pageSize, column, ascendingSort, searchWord);
        }
    }

}
