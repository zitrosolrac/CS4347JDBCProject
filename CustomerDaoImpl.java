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

		private static final String	retrieveSQL = 
						"SELECT (firstName, lastName, gender, dob, email) FROM Customer WHERE id = ?;"; 

		private static final String deleteSQL = 
						"DELETE Address WHERE customer_id = ?;";

		private static final String updateSQL = 
						"UPDATE Customer SET firstName = ?, lastName = ?, gender = ?, dob = ?, email = ? WHERE id=? ;";

		private static final String zipSQL = 
						"Select (firstName, lastName, gender, dob, email) FROM Customer INNER JOIN Address on Customer.ID = Address.customer_id WHERE Address.zipcode = ?;";  


		private static final String dobSQL = 
						"SELECT firstName, lastName, gender, dob, email FROM Customer WHERE dob BETWEEN ? and ?;"; 


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
        
			PreparedStatement ps = null; 

			try {
					ps = connection.prepareStatement(retrieveSQL);
					ps.setLong(1, id); 
					ResultSet result = ps.executeQuery();

					Customer customer = null; 

					if(result.next()) {
						customer.setFirstName(result.getString(1));
						customer.setLastName(result.getString(2));
						customer.setGender(result.getString(3));
						customer.setDob(result.getString(4));
						customer.setEmail(result.getString(5));
					}

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
    public int update(Connection connection, Customer customer) throws SQLException, DAOException {
		PreparedStatement ps = null; 

		try { 
			ps = connection.prepareStatement(updateSQL); 
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setString(3, customer.getGender().toString());
			ps.setString(4, customer.getDob());
			ps.setString(5, customer.getEmail());
			ps.setInt(6, customer.getId());
			
		int rows = ps.executeUpdate(); 
		}finally{
			if(ps != null && !ps.isClosed()) {
				ps.close();
			}
			if(connection != null && !connection.isClosed()){
				connection.close();
			}

		}
        return rows;
}


    @Override
    public int delete(Connection connection, Long id) throws SQLException, DAOException {
        PreparedStatement ps = null; 

				try { 
					ps = connection.prepareStatement(deleteSQL);
					ps.setLong(1, id);
					int rows = ps.executeUpdate();
				} finally {
						if (ps != null && !ps.isClosed()) {
								ps.close();
							}
						if (connection != null && !conneection.isCLosed()) { 
								connection.close();
							}
						}
				return rows; 
    }

    @Override
    public List<Customer> retrieveByZipCode(Connection connection, String zipCode) throws SQLException, DAOException {
        
				if (zipCode == null) {
					throw new DAOException("Trying to retrieve Customer with null zip code"); 
					}

				List<Customer> resultSet = new ArrayList<Customer>(); 

				PreparedStatement ps = null; 

					ps = connection.prepareStatement(zipSQL); 
					
					ps.setString(1, zipCode);

					ResultSet resultProduced  = ps.executeQuery(); 

					while(resultProduced.next()) {

						Customer tempCust = new Customer(); //create a customer instance for each row of the result set 
						tempCust.setFirstName(resultProduced.getString("firstname"));
						tempCust.setLastName(resultProduced.getString("lastname")); 
						tempCust.setGender(resultProduced.getString("gender").charAt(0));
						tempCust.setDob(resultProduced.getString("dob"));
						tempCust.setEmail(resultProduced.getString("email"));

						resultSet.add(tempCust);

					}

					return resultSet;
    }

    @Override
    public List<Customer> retrieveByDOB(Connection connection, Date startDate, Date endDate) throws SQLException, DAOException {

				if(startDate == null || endDate == null) 
					throw new DAOException("Trying to retrieve customer with null dob"); 

					List<Customer> resultProduced = new ArrayList<Customer>(); 

					PreparedStatement ps = null; 

						ps = connection.prepareStatement(dobSQL);

						ps.setDate(1, startDate);
						ps.setDate(2, endDate); 
					
					ResultSet rs = ps.executeQuery(); 

					//keep adding instances of customers that match the criteria until there are not any left 

					while(rs.next()) { 

						Customer tempCust = new Customer(); 
						
						tempCust.setFirstName(rs.getString("firstname")); 
						tempCust.setLastName(rs.getString("lastname"));
						tempCust.setGender(rs.getString("gender").charAt(0));
						tempCust.setDob(rs.getDate("dob"));
						tempCust.setEmail(rs.getString("email"));
						resultProduced.add(tempCust); 
					
					}

				return resultProduced; 

        return null;
    }
