package com.excilys.formation.cdb.pagination;

import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;

public class ComputerListPageSearch extends ComputerListPage {

    private ComputerService computerService;

    private final String searchWord;

    public ComputerListPageSearch(int pageNumber, String searchWord, ComputerService computerService) throws ServiceException {
        super(pageNumber);
        this.searchWord = searchWord;
        this.computerService = computerService;
    }

    public ComputerListPageSearch(String searchWord, ComputerService computerService) throws ServiceException {
        super();
        this.searchWord = searchWord;
        this.computerService = computerService;
    }

    @Override
    public int getComputerCount() throws ServiceException {
        return computerService.getComputerCount(searchWord);
    }

    @Override
    public int getLastPageNumber() throws ServiceException {
        return computerService.getListComputersPageCount(pageSize, searchWord);
    }

    @Override
    public int getListComputersPageCount(int pageSize) throws ServiceException {
        return computerService.getListComputersPageCount(pageSize, searchWord);
    }

    @Override
    public void refresh() throws ServiceException {
        if (searchWord == null) {
            elements = computerService.getListComputers(pageNumber, pageSize, column, ascendingSort);
        } else {
            elements = computerService.getListComputers(pageNumber, pageSize, column, ascendingSort, searchWord);
        }
    }

}
