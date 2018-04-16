package com.excilys.formation.cdb.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.mapper.RowComputerMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

@Repository("ComputerDAO")
public class ComputerDAOImpl implements ComputerDAO {

    private static final String SELECT_COMPUTERS = "select cu_id, cu_name, cu_introduced, cu_discontinued, computer.ca_id, ca_name from computer left join company on computer.ca_id = company.ca_id;";
    private static final String SELECT_COMPUTERS_PAGE = "select cu_id, cu_name, cu_introduced, cu_discontinued, computer.ca_id, ca_name from computer left join company on computer.ca_id = company.ca_id order by %s %s limit ? offset ?;";
    private static final String SELECT_ALL_COMPUTERS_PAGE_WITH_SEARCH = "select cu_id, cu_name, cu_introduced, cu_discontinued, computer.ca_id, ca_name from computer left join company on computer.ca_id = company.ca_id where cu_name like ? or ca_name like ? order by %s %s limit ? offset ?;";
    private static final String SELECT_COUNT_COMPUTERS = "select count(cu_id) from computer;";
    private static final String SELECT_COUNT_COMPUTERS_WITH_SEARCH = "select count(cu_id) from computer left join company on computer.ca_id = company.ca_id where ca_name LIKE ? or cu_name LIKE ?;";
    private static final String SELECT_COMPUTER = "select cu_id, cu_name, cu_introduced, cu_discontinued, computer.ca_id, ca_name from computer left join company on computer.ca_id = company.ca_id where cu_id = ?;";
    private static final String INSERT_COMPUTER = "insert into computer (cu_name, cu_introduced, cu_discontinued, ca_id) values (?, ?, ?, ?);";
    private static final String UPDATE_COMPUTER = "update computer set cu_name = ?, cu_introduced = ?, cu_discontinued = ?, ca_id = ? where cu_id = ?;";
    private static final String DELETE_COMPUTER = "delete from computer where cu_id in (?);";

    private static final Logger Logger = LoggerFactory.getLogger(ComputerDAOImpl.class);

    private JdbcTemplate jdbcTemplate;

    @Override
    public Long createComputer(Computer computer) throws DAOException {
        Logger.info("create computer");
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update((conn) -> {
            PreparedStatement statement = conn.prepareStatement(INSERT_COMPUTER, Statement.RETURN_GENERATED_KEYS);
            populateStatementFromComputer(computer, statement);
            return statement;
        }, holder);

        return holder.getKey().longValue();
    }

    @Override
    public void deleteComputer(Computer c) throws DAOException {
        Logger.info("delete computer");
        jdbcTemplate.update(DELETE_COMPUTER, c.getId());
    }

    @Override
    public void deleteComputers(List<Long> idsToDelete) throws DAOException {
        jdbcTemplate.batchUpdate(DELETE_COMPUTER, new BatchPreparedStatementSetter() {

            @Override
            public int getBatchSize() {
                return idsToDelete.size();
            }

            @Override
            public void setValues(PreparedStatement st, int i) throws SQLException {
                st.setLong(1, idsToDelete.get(i));
            }

        });
    }

    @Override
    public Optional<Computer> getComputer(Computer computer) throws DAOException {
        if ((computer == null) || (computer.getId() == null)) {
            return Optional.empty();
        }
        return getComputer(computer.getId());
    }

    @Override
    public Optional<Computer> getComputer(long id) throws DAOException {
        try {
            return Optional
                    .of(jdbcTemplate.queryForObject(SELECT_COMPUTER, new Object[] { id }, new RowComputerMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int getComputerCount() throws DAOException {
        return jdbcTemplate.queryForObject(SELECT_COUNT_COMPUTERS, Integer.class);
    }

    @Override
    public int getComputerCount(String searchWord) throws DAOException {
        Logger.info("getComputerCount with searchWord : ", searchWord);
        searchWord = '%' + searchWord + '%';
        return jdbcTemplate.queryForObject(SELECT_COUNT_COMPUTERS_WITH_SEARCH, new Object[] { searchWord, searchWord },
                Integer.class);
    }

    @Override
    public List<Computer> getListComputers() throws DAOException {
        Logger.info("list computers no args");
        return jdbcTemplate.query(SELECT_COMPUTERS, new RowComputerMapper());
    }

    @Override
    public List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column,
            boolean ascending) throws DAOException {
        Logger.info("list computers with pages / orderBy");
        String newRequest = String.format(SELECT_COMPUTERS_PAGE, column.getColumn(), ascending ? "ASC" : "DESC");
        return jdbcTemplate.query(newRequest, new Object[] { pageSize, pageSize * pageNumber },
                new RowComputerMapper());
    }

    @Override
    public List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column,
            boolean ascending, String searchWord) throws DAOException {
        Logger.info("list computers with pages / orderBy / search");

        if (StringUtils.isBlank(searchWord)) {
            Logger.info("search word was null, using regular list computer");
            return getListComputers(pageNumber, pageSize, column, ascending);
        }

        String newRequest = String.format(SELECT_ALL_COMPUTERS_PAGE_WITH_SEARCH, column.getColumn(),
                ascending ? "ASC" : "DESC");
        searchWord = '%' + searchWord + '%';

        return jdbcTemplate.query(newRequest, new Object[] { searchWord, searchWord, pageSize, pageSize * pageNumber },
                (ResultSet rs, int row) -> {
                    return ComputerMapper.createComputer(rs);
                });
    }

    @Override
    public int getListComputersPageCount(int pageSize) throws DAOException {
        int pageCount = 0;
        int computerCount = getComputerCount();
        pageCount = ((computerCount + pageSize) - 1) / pageSize;
        return pageCount;
    }

    @Override
    public int getListComputersPageCount(int pageSize, String searchWord) throws DAOException {
        int pageCount = 0;

        int computerCount = getComputerCount(searchWord);
        pageCount = ((computerCount + pageSize) - 1) / pageSize;

        return pageCount;
    }

    private void populateStatementFromComputer(Computer computer, PreparedStatement statement) throws SQLException {

        statement.setString(1, computer.getName());

        if (computer.getIntroduced().isPresent()) {
            statement.setDate(2, Date.valueOf(computer.getIntroduced().get()));
        } else {
            statement.setNull(2, java.sql.Types.DATE);
        }

        if (computer.getDiscontinued().isPresent()) {
            statement.setDate(3, Date.valueOf(computer.getDiscontinued().get()));
        } else {
            statement.setNull(3, java.sql.Types.DATE);
        }

        if ((computer.getCompany().isPresent()) && (computer.getCompany().get().getId() != null)) {
            statement.setLong(4, computer.getCompany().get().getId());
        } else {
            statement.setNull(4, java.sql.Types.BIGINT);
        }
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void updateComputer(Computer computer) throws DAOException {
        Logger.info("update computer");
        computer.getIntroduced().map(Date::valueOf).orElse(null);

        jdbcTemplate.update(UPDATE_COMPUTER,
                new Object[] { computer.getName(), computer.getIntroduced().map(Date::valueOf).orElse(null),
                        computer.getDiscontinued().map(Date::valueOf).orElse(null),
                        computer.getCompany().map(Company::getId).orElse(null), computer.getId() });
    }

}
