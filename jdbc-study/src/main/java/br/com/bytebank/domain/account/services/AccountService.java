package br.com.bytebank.domain.account.services;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.bytebank.domain.account.daos.AccountDAO;
import br.com.bytebank.domain.account.models.Account;

public class AccountService {

	private AccountDAO accountDAO;

	public AccountService() {
		accountDAO = new AccountDAO();
	}

	public void create(Account account) throws RuntimeException {

		accountDAO.create(account);

	}

	public ArrayList<Account> getAll() {

		return accountDAO.getAll();

	}

	public Account getAccount(String idParam, String accountParam) throws RuntimeException {

		if (idParam == null && accountParam == null) {
			throw new RuntimeException("Missing account or id query parameters");
		}

		if (idParam != null) {
			return this.accountDAO.getById(Integer.parseInt(idParam));
		}

		Account account = this.accountDAO.getByNumber(Integer.parseInt(accountParam));

		if (account == null) {
			throw new RuntimeException("Account not found");
		}

		return account;
	}

	public void deposit(Integer account, BigDecimal amount) throws RuntimeException {
		
		if (account == null || amount == null) {

			throw new RuntimeException("Missing account or value attributes of request body");
			
		}

		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			
			throw new RuntimeException("The value attribute can't be negative");
	
		}
		
		 this.accountDAO.updateBalance(account, amount);

	}

	public void withdraw(Integer account, BigDecimal amount) throws RuntimeException {
		
		if (account == null || amount == null) {

			throw new RuntimeException("Missing account or value attributes of request body");
			
		}

		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			
			throw new RuntimeException("The value attribute can't be negative");
	
		}
		
		 this.accountDAO.updateBalance(account, amount.negate());

	}

	public void transfer(Integer senderAccount, Integer receiverAccount, BigDecimal value) throws RuntimeException {

		if (value.compareTo(BigDecimal.ZERO) < 0) {
			throw new RuntimeException("Value attribute can't be negative");
		}

		this.accountDAO.transfer(senderAccount, receiverAccount, value);

	}

}
