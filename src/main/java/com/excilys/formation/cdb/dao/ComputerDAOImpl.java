package com.excilys.formation.cdb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer.ComputerBuilder;

@Repository("ComputerDAO")
public class ComputerDAOImpl implements ComputerDAO {

    private static final String SELECT_ALL_COMPUTERS = "select cu_id, cu_name, cu_introduced, cu_discontinued, computer.ca_id, ca_name from computer left join company on computer.ca_id = company.ca_id;";
    private static final String SELECT_ALL_COMPUTERS_PAGE = "select cu_id, cu_name, cu_introduced, cu_discontinued, computer.ca_id, ca_name from computer left join company on computer.ca_id = company.ca_id order by %s %s limit ? offset ?;";
    private static final String SELECT_ALL_COMPUTERS_PAGE_WITH_SEARCH = "select cu_id, cu_name, cu_introduced, cu_discontinued, computer.ca_id, ca_name from computer left join company on computer.ca_id = company.ca_id where cu_name like ? or ca_name like ? order by %s %s limit ? offset ?;";
    private static final String SELECT_COUNT_COMPUTERS = "select count(cu_id) from computer;";
    private static final String SELECT_COUNT_COMPUTERS_WITH_SEARCH = "select count(cu_id) from computer left join company on computer.ca_id = company.ca_id where ca_name LIKE ? or cu_name LIKE ?;";
    private static final String SELECT_COMPUTER = "select cu_id, cu_name, cu_introduced, cu_discontinued, computer.ca_id, ca_name from computer left join company on computer.ca_id = company.ca_id where cu_id = ?;";
    private static final String INSERT_COMPUTER = "insert into computer (cu_name, cu_introduced, cu_discontinued, ca_id) values (?, ?, ?, ?);";
    private static final String UPDATE_COMPUTER = "update computer set cu_name = ?, cu_introduced = ?, cu_discontinued = ?, ca_id = ? where cu_id = ?;";
    private static final String DELETE_COMPUTER = "delete from computer where cu_id in (?);";

    private static final Logger Logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
    
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long createComputer(Computer c) throws DAOException {
        Logger.info("create computer");
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement statement = conn.prepareStatement(INSERT_COMPUTER, Statement.RETURN_GENERATED_KEYS);
                populateStatementFromComputer(c, statement);
                return statement;
            }
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
        Computer c = null;

        try (Connection conn = getConnection(); PreparedStatement st = conn.prepareStatement(SELECT_COMPUTER);) {

            if (computer.getId() != null) {
                st.setLong(1, computer.getId());
            } else {
                st.setNull(1, java.sql.Types.BIGINT);
            }

            try (ResultSet rs = st.executeQuery();) {
                if (rs.next()) {
                    c = ComputerMapper.createComputer(rs);
                }
            }

        } catch (SQLException e) {
            Logger.debug(e.getMessage());
            throw new DAOException("Couldn't find computer with ID : " + computer.getId());
        }

        return Optional.ofNullable(c);
    }

    @Override
    public Optional<Computer> getComputer(long id) throws DAOException {
        return getComputer(new ComputerBuilder().withId(id).build());
    }

    @Override
    public int getComputerCount() throws DAOException {
        int computerCount = 0;

        try (Connection conn = getConnection();
                PreparedStatement st = conn.prepareStatement(SELECT_COUNT_COMPUTERS);
                ResultSet rs = st.executeQuery()) {
            rs.next();
            computerCount = rs.getInt(1);

        } catch (SQLException e) {
            Logger.debug(e.getMessage());
            throw new DAOException("Couldn't get computer count");
        }

        return computerCount;
    }

    @Override
    public int getComputerCount(String searchWord) throws DAOException {
        Logger.info("getComputerCount with searchWord : ", searchWord);
        int computerCount = 0;

        try (Connection conn = getConnection();
                PreparedStatement st = conn.prepareStatement(SELECT_COUNT_COMPUTERS_WITH_SEARCH)) {

            searchWord = '%' + searchWord + '%';
            st.setString(1, searchWord);
            st.setString(2, searchWord);

            try (ResultSet rs = st.executeQuery()) {
                rs.next();
                computerCount = rs.getInt(1);
            }

        } catch (SQLException e) {
            Logger.debug(e.getMessage());
            throw new DAOException("Couldn't get computer count");
        }

        return computerCount;
    }

    @Override
    public List<Computer> getListComputers() throws DAOException {
        Logger.info("list computers no args");
        ArrayList<Computer> computers = new ArrayList<>();

        try (Connection conn = getConnection();
                Statement st = conn.createStatement();
                ResultSet computerList = st.executeQuery(SELECT_ALL_COMPUTERS)) {

            while (computerList.next()) {
                computers.add(ComputerMapper.createComputer(computerList));
            }

        } catch (SQLException e) {
            Logger.debug(e.getMessage());
            throw new DAOException("Couldn't get computer list");
        }

        return computers;
    }

    @Override
    public List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column,
            boolean ascending) throws DAOException {
        Logger.info("list computers with pages / orderBy");
        ArrayList<Computer> computers = new ArrayList<>();

        String newRequest = String.format(SELECT_ALL_COMPUTERS_PAGE, column.getColumn(), ascending ? "ASC" : "DESC");

        try (Connection conn = getConnection(); PreparedStatement st = conn.prepareStatement(newRequest);) {

            st.setInt(1, pageSize);
            st.setInt(2, pageSize * pageNumber);

            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    computers.add(ComputerMapper.createComputer(rs));
                }
            }
        } catch (SQLException e) {
            Logger.debug("error when trying to get computer list {}", e);
            throw new DAOException("Couldn't get computer list");
        }

        return computers;
    }

    @Override
    public List<Computer> getListComputers(int pageNumber, int pageSize, SortableComputerColumn column,
            boolean ascending, String searchWord) throws DAOException {
        Logger.info("list computers with pages / orderBy / search");

        if (searchWord == null) {
            Logger.info("search word was null, using regular list computer");
            return getListComputers(pageNumber, pageSize, column, ascending);
        }

        ArrayList<Computer> computers = new ArrayList<>();
        String newRequest = String.format(SELECT_ALL_COMPUTERS_PAGE_WITH_SEARCH, column.getColumn(),
                ascending ? "ASC" : "DESC");

        try (Connection conn = getConnection(); PreparedStatement st = conn.prepareStatement(newRequest);) {

            searchWord = '%' + searchWord + '%';
            st.setString(1, searchWord);
            st.setString(2, searchWord);
            st.setInt(3, pageSize);
            st.setInt(4, pageSize * pageNumber);

            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    computers.add(ComputerMapper.createComputer(rs));
                }
            }
        } catch (SQLException e) {
            Logger.debug("error when trying to get computer list {}", e);
            throw new DAOException("Couldn't get computer list");
        }

        return computers;
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

    private void populateStatementFromComputer(Computer c, PreparedStatement st) throws SQLException {

        st.setString(1, c.getName());

        if (c.getIntroduced() != null) {
            st.setDate(2, Date.valueOf(c.getIntroduced()));
        } else {
            st.setNull(2, java.sql.Types.DATE);
        }

        if (c.getDiscontinued() != null) {
            st.setDate(3, Date.valueOf(c.getDiscontinued()));
        } else {
            st.setNull(3, java.sql.Types.DATE);
        }

        if ((c.getCompany() != null) && (c.getCompany().getId() != null)) {
            st.setLong(4, c.getCompany().getId());
        } else {
            st.setNull(4, java.sql.Types.BIGINT);
        }
    }

    @Override
    public void updateComputer(Computer c) throws DAOException {
        Logger.info("update computer");
        try (Connection conn = getConnection(); PreparedStatement st = conn.prepareStatement(UPDATE_COMPUTER);) {

            populateStatementFromComputer(c, st);
            st.setLong(5, c.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            Logger.debug(e.getMessage());
            throw new DAOException("Couldn't update computer with id : " + c.getId());
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
    }
}
