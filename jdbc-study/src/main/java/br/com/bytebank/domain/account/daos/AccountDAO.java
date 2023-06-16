package br.com.bytebank.domain.account.daos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.bytebank.domain.account.models.Account;
import br.com.bytebank.infra.db.ConnectionFactory;

public class AccountDAO {

	private ConnectionFactory cf;	
	
	public AccountDAO() {
		super();
		cf = new ConnectionFactory();
	}



	public int create(Account account) {
		
		Connection connection = cf.getConnection();		
		
		String sql = "insert into Account (account, balance, name, cpf, email, agency) values (?,?,?,?,?,?)";
		
		try {
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, account.getAccount());
			statement.setBigDecimal(2, BigDecimal.ZERO);
			statement.setString(3, account.getName());
			statement.setString(4, account.getCpf());
			statement.setString(5, account.getEmail());
			statement.setInt(6, account.getAgency());
			
			int result = statement.executeUpdate();
			
			cf.closeConnection();
			
			return result;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
	
}


