package cs4347.jdbcProject.ecomm.dao.impl;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.util.DAOException;

import java.sql.*;

public class AddressDaoImpl implements AddressDAO
{

    private static final String insertSQL =
            "INSERT INTO CUSTOMER (first_name, last_name, dob, gender, email) VALUES (?, ?, ?, ?, ?);";


    @Override
    public Address create(Connection connection, Address address, Long customerID) throws SQLException, DAOException {
        if (address.getId() != null) {
            throw new DAOException("Trying to insert Address with NON-NULL ID");
        }

        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getFirstName());
            // Set other statement attributes here...
            ps.executeUpdate();

            // REQUIREMENT: Copy the auto-increment primary key to the customer ID.
            ResultSet keyRS = ps.getGeneratedKeys();
            keyRS.next();
            int lastKey = keyRS.getInt(1);
            customer.setId((long) lastKey);

            return customer;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    @Override
    public Address retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
        return null;
    }

    @Override
    public void deleteForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {

    }
}
