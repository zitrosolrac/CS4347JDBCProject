package cs4347.jdbcProject.ecomm.dao.impl;

import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.util.DAOException;

import java.sql.*;


public class CreditCardDaoImpl implements CreditCardDAO
{

    private static final String insertSQL =
            "INSERT INTO CreditCard (name, number, expiration, securityCode, customer_id) VALUES (?, ?, ?, ?, ?);";

    private static final String retrieveSQL =
            "SELECT name, number, expiration, securityCode, customer_id FROM CreditCard WHERE customer_id = ? LIMIT 1;";

    private static final String deleteSQL =
            "DELETE CreditCard WHERE customer_id = ?;";

    @Override
    public CreditCard create(Connection connection, CreditCard creditCard, Long customerID) throws SQLException, DAOException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertSQL);
            ps.setString(1, creditCard.getName());
            ps.setString(2, creditCard.getCcNumber());
            ps.setString(3, creditCard.getExpDate()); //TODO: probably need to convert to java.sql.Date
            ps.setString(4, creditCard.getSecurityCode());
            ps.setLong(5, customerID);
            ps.executeUpdate();

            return creditCard;
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
    public CreditCard retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(retrieveSQL);
            ps.setLong(1, customerID);
            ResultSet result = ps.executeQuery();

            CreditCard card = null;

            if (result.next()) {
                card.setName(result.getString(1));
                card.setCcNumber(result.getString(2));
                card.setExpDate(result.getString(3));
                card.setSecurityCode(result.getString(4));
            }

            return card;
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
