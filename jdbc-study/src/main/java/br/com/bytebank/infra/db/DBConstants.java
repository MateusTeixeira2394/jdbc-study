package br.com.bytebank.infra.db;

public class DBConstants {

	public static final String jdbcUrl = "jdbc:mysql://localhost:" + System.getenv("db_port") + "/"
			+ System.getenv("db_name");
	
	public static final String username = System.getenv("db_user");
	
	public static final String password = System.getenv("db_pass");

}