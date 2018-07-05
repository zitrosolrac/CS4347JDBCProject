package cs4347.jdbcProject.ecomm.dao.impl;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.util.DAOException;

import java.sql.*;

public class AddressDaoImpl implements AddressDAO
{

    private static final String insertSQL =
            "INSERT INTO Address (address1, address2, city, state, zipcode, customer_id) VALUES (?, ?, ?, ?, ?, ?);";

    private static final String retrieveSQL =
            "SELECT address1, address2, city, state, zipcode, customer_id FROM Address WHERE customer_id = ? LIMIT 1;";

    private static final String deleteSQL =
            "DELETE Address WHERE customer_id = ?;";


    @Override
    public Address create(Connection connection, Address address, Long customerID) throws SQLException, DAOException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertSQL);
            ps.setString(1, address.getAddress1());
            ps.setString(2, address.getAddress2());
            ps.setString(3, address.getCity());
            ps.setString(4, address.getState());
            ps.setString(5, address.getZipcode());
            ps.setLong(6, customerID);
            ps.executeUpdate();

            return address;
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
    public Address retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(retrieveSQL);
            ps.setLong(1, customerID);
            ResultSet result = ps.executeQuery();

            Address address = null;

            if (result.next()) {
                address.setAddress1(result.getString(1));
                address.setAddress2(result.getString(2));
                address.setCity(result.getString(3));
                address.setState(result.getString(4));
                address.setZipcode(result.getString(5));
            }

            return address;
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
    public void deleteForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(deleteSQL);
            ps.setLong(1, customerID);
            ps.executeUpdate();
        } finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }
}
