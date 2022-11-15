package com.PTO;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Connect to Database
 */
public class ConnectionFactory {

	public static final String DB_URL = "jdbc:mysql://localhost:3306/transitorganizer?useSSL=false";
	public static final String DB_USER = "root";
	public static final String DB_PASSWORD = "0900xs0800";   

	    /**

	     * Get a connection to database

	     * @return Connection object

	     */

	    public static Connection getConnection()

	    {

	      try {

	          DriverManager.registerDriver(new Driver());	    	
         	  Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
	          return connection;
	         

	      } catch (SQLException ex) {

	          throw new RuntimeException("Error connecting to the database", ex);

	      }
	      

	    }	

}
