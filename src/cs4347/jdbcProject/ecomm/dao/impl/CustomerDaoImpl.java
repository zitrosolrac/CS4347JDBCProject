package cs4347.jdbcProject.ecomm.dao.impl;

import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.util.DAOException;

import java.sql.*;
import java.util.List;

public class CustomerDaoImpl implements CustomerDAO
{
    private static final String insertSQL =
            "INSERT INTO Customer (firstName, lastName, gender, dob, email) VALUES (?, ?, ?, ?, ?);";

    @Override
    public Customer create(Connection connection, Customer customer) throws SQLException, DAOException {
        if (customer.getId() != null) {
            throw new DAOException("Trying to insert Customer with NON-NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getGender().toString());
            ps.setDate(4, customer.getDob());
            ps.setString(5, customer.getEmail());
            ps.executeUpdate();

            ResultSet keyRS = ps.getGeneratedKeys();
            keyRS.next();
            long lastKey = keyRS.getLong(1);
            customer.setId(lastKey);

            return customer;
        } finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    @Override
    public Customer retrieve(Connection connection, Long id) throws SQLException, DAOException {
        return null;
    }

    @Override
    public int update(Connection connection, Customer customer) throws SQLException, DAOException {
        return 0;
    }

    @Override
    public int delete(Connection connection, Long id) throws SQLException, DAOException {
        return 0;
    }

    @Override
    public List<Customer> retrieveByZipCode(Connection connection, String zipCode) throws SQLException, DAOException {
        return null;
    }

    @Override
    public List<Customer> retrieveByDOB(Connection connection, Date startDate, Date endDate) throws SQLException, DAOException {
        return null;
    }
}
