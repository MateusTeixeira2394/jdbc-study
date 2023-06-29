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

	public boolean create(Account account) {

		int resp = accountDAO.create(account);

		if (resp == 0) {
			return false;
		}

		return true;

	}

	public ArrayList<Account> getAll() {

		return accountDAO.getAll();

	}

	public Account getAccount(String idParam, String accountParam) {

		if (idParam != null) {
			return this.accountDAO.getById(Integer.parseInt(idParam));
		}

		return this.accountDAO.getByNumber(Integer.parseInt(accountParam));
	}

	public boolean deposit(Integer account, BigDecimal balance) {

		return this.accountDAO.updateBalance(account, balance) >= 1;

	}

	public boolean withdraw(Integer account, BigDecimal balance) {

		return this.accountDAO.updateBalance(account, balance.negate()) >= 1;

	}
	
	public void transfer(Integer senderAccount, Integer receiverAccount, BigDecimal value) throws RuntimeException {
		
		if(value.compareTo(BigDecimal.ZERO) < 0) {
			throw new RuntimeException("Value attribute can't be negative");
		}
		
		this.accountDAO.transfer(senderAccount, receiverAccount, value);
		
	}

}
