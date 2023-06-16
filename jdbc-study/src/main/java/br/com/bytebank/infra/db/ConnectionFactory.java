package br.com.bytebank.infra.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.cj.jdbc.Driver;

public class ConnectionFactory {
	
	private static ConnectionFactory instance;
	
	private Connection connection;
	
	private String jdbcUrl = DBConstants.jdbcUrl;
    private String username = DBConstants.username;
    private String password = DBConstants.password; 
	
	public static ConnectionFactory getInstance() {
		
		if(instance == null) {
			instance = new ConnectionFactory();
		}
		
		return instance;
	}
	
	public Connection getConnection() {				   

        try {
        	
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");            

            if(connection == null || connection.isClosed()) {
            	// Open a connection
                connection = DriverManager.getConnection(jdbcUrl, username, password);
            }
            
            System.out.println("Opened connection");
                        
            return connection;
            
        } catch (Exception e) {
            
        	throw new RuntimeException(e);
        	
        }
		
		
	}
	
	public void closeConnection() {
		
		try {
			
			connection.close();			
			System.out.println("Closed connection");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

    
}
