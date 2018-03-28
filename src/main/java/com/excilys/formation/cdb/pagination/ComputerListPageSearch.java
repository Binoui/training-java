package com.excilys.formation.cdb.pagination;

import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.services.ComputerService;
import com.excilys.formation.cdb.services.ServiceException;

public class ComputerListPageSearch extends ComputerListPage {

    private static ComputerService service = ComputerService.INSTANCE;
    
    private final String searchWord;

    public ComputerListPageSearch(String searchWord) throws ServiceException {
        super();
        this.searchWord = searchWord;
    }

    public ComputerListPageSearch(int pageNumber, String searchWord) throws ServiceException {
        super(pageNumber);
        this.searchWord = searchWord;
    }

    @Override
    public int getLastPageNumber() throws ServiceException {
        return service.getListComputersPageCount(pageSize, searchWord);
    }

    @Override
    public void refresh() throws ServiceException {
        if (searchWord == null) {
            elements = service.getListComputers(pageNumber, pageSize);
        } else {
            elements = service.getListComputers(pageNumber, pageSize, searchWord);
        }
    }
    
    @Override
    public int getListComputersPageCount(int pageSize) throws ServiceException {
        return service.getListComputersPageCount(pageSize, searchWord);
    }

    @Override
    public int getComputerCount() throws ServiceException {
        return service.getComputerCount(searchWord);
    }

}
