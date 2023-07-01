package br.com.bytebank.domain.account.daos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.management.RuntimeErrorException;

import br.com.bytebank.domain.account.models.Account;
import br.com.bytebank.infra.db.ConnectionFactory;

public class AccountDAO {

	private ConnectionFactory cf;

	public AccountDAO() {
		super();
		cf = new ConnectionFactory();
	}

	public void create(Account account) {

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

			statement.executeUpdate();

			statement.close();

			cf.closeConnection();

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

		String sql = "select id, account, balance, name, cpf, email, agency, created_at from Account where account = ?";

		try {

			Connection connection = cf.getConnection();

			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setInt(1, accountNumber);

			ResultSet result = ps.executeQuery();

			Account account = this.getAccountsFromResult(result).get(0);

			result.close();

			ps.close();

			cf.closeConnection();

			if (account == null) {
				throw new RuntimeException("Account not found");
			}

			return account;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

	}

	public void deleteByNumber(int accountNumber) throws RuntimeException {

		Account account = this.getByNumber(accountNumber);
		
		int comparison = account.getBalance().compareTo(BigDecimal.ZERO);
		
		if (comparison < 0) {
			throw new RuntimeException("It's not allowed delete an account with nagative balance");
		}
		
		if (comparison > 0) {
			throw new RuntimeException("It's not allowed delete an account with positive balance");
		}
		
		deleteByNumberQuery(accountNumber);

	}

	private void deleteByNumberQuery(int accountNumber) {

		String sql = "delete from Account where account = ?";

		try {

			Connection connection = cf.getConnection();

			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setInt(1, accountNumber);
			ps.executeUpdate();

			ps.close();
			connection.close();

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	public Account getById(int id) {

		String sql = "select id, account, balance, name, cpf, email, agency, created_at from Account where id = ?";

		try {

			Connection connection = cf.getConnection();

			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet result = ps.executeQuery();

			Account account = this.getAccountsFromResult(result).get(0);

			result.close();

			ps.close();

			cf.closeConnection();

			if (account == null) {
				throw new RuntimeException("Account not found");
			}

			return account;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

	}

	public void updateBalance(Integer account, BigDecimal value) {

		String sql = "update Account SET balance=balance + ? where account = ?";

		Connection connection = cf.getConnection();

		try {

			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setBigDecimal(1, value);
			ps.setInt(2, account);
			int result = ps.executeUpdate();

			ps.close();
			cf.closeConnection();

			if (result == 0) {
				throw new RuntimeException("It wasn't possible to make this transaction");
			}

		} catch (SQLException e) {

			throw new RuntimeException(e);

		}

	}

	public void transfer(Integer senderAccount, Integer receiverAccount, BigDecimal amount) {

		Connection connection = cf.getConnection();

		try {

			connection.setAutoCommit(false);

			decreaseBalance(connection, senderAccount, amount);

			increaseBalance(connection, receiverAccount, amount);

			connection.commit();

			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			try {
				connection.rollback();
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				throw new RuntimeException(ex.getMessage());
			}

			throw new RuntimeException(e.getMessage());
		}

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

	private void decreaseBalance(Connection connection, Integer senderAccount, BigDecimal amount) throws SQLException {

		String senderSql = "update Account SET balance = balance - ? WHERE account = ?";

		try (PreparedStatement senderStatement = connection.prepareStatement(senderSql)) {
			senderStatement.setBigDecimal(1, amount);
			senderStatement.setInt(2, senderAccount);
			int result = senderStatement.executeUpdate();

			if (result <= 0) {
				throw new SQLException("Not found the sender account");
			}
		}

	}

	private void increaseBalance(Connection connection, Integer receiverAccount, BigDecimal amount)
			throws SQLException {

		String receiverSql = "update Account SET balance = balance + ? WHERE account = ?";

		try (PreparedStatement receiverStatement = connection.prepareStatement(receiverSql)) {

			receiverStatement.setBigDecimal(1, amount);
			receiverStatement.setInt(2, receiverAccount);
			int result = receiverStatement.executeUpdate();

			if (result <= 0) {
				throw new SQLException("Not found the receiver account");
			}

		}

	}

}
