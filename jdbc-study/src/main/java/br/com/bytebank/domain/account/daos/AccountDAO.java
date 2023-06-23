package br.com.bytebank.domain.account.daos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

			statement.close();

			cf.closeConnection();

			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

	}

	public ArrayList<Account> getAll() {

		ArrayList<Account> accounts = new ArrayList<Account>();

		String sql = "select id, account, balance, name, cpf, email, agency, created_at from Account";

		try {

			Connection connection = cf.getConnection();

			PreparedStatement ps = connection.prepareStatement(sql);

			ResultSet result = ps.executeQuery();

			accounts = this.getAccountsFromResult(result);

			result.close();

			ps.close();

			cf.closeConnection();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

		return accounts;

	}

	public Account getByNumber(int accountNumber) {

		ArrayList<Account> accounts = new ArrayList<Account>();

		String sql = "select id, account, balance, name, cpf, email, agency, created_at from Account where account = ?";

		try {

			Connection connection = cf.getConnection();

			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setInt(1, accountNumber);

			ResultSet result = ps.executeQuery();

			accounts = this.getAccountsFromResult(result);

			result.close();

			ps.close();

			cf.closeConnection();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

		return accounts.get(0);
		
	}
	
	public Account getById(int id) {

		ArrayList<Account> accounts = new ArrayList<Account>();

		String sql = "select id, account, balance, name, cpf, email, agency, created_at from Account where id = ?";

		try {

			Connection connection = cf.getConnection();

			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet result = ps.executeQuery();

			accounts = this.getAccountsFromResult(result);

			result.close();

			ps.close();

			cf.closeConnection();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

		return accounts.get(0);
		
	}
	
	private ArrayList<Account> getAccountsFromResult(ResultSet result) {
		
		ArrayList<Account> accounts = new ArrayList<Account>();
		
		try {
			
			while (result.next()) {

				int id = result.getInt(1);
				int ac = result.getInt(2);
				BigDecimal balance = result.getBigDecimal(3);
				String name = result.getString(4);
				String cpf = result.getString(5);
				String email = result.getString(6);
				int agency = result.getInt(7);

				accounts.add(new Account(id, ac, balance, email, cpf, agency, name));

			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return accounts;
		
	}

}
