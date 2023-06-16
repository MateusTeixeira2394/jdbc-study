package br.com.bytebank.domain.account.services;

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
	
}
