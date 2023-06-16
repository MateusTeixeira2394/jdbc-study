package br.com.bytebank.domain.account.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

	private int account;
	private BigDecimal balance;
	private String name;
	private String cpf;
	private String email;
	private int agency;

	/**
	 * Constructs a new Account object with the given cpf, name, agency and account
	 * numbers.
	 *
	 * @param cpf       of the account owner
	 * @param name      of the account owner
	 * @param agency    of where the account belongs
	 * @param name
	 * @param account's number
	 */
	public Account(int account, BigDecimal balance, String cpf, String email, int agency, String name) {
		super();
		this.account = account;
		this.setBalance(balance);
		this.cpf = cpf;
		this.setEmail(email);
		this.name = name;
		this.agency = agency;
	}

	@JsonCreator
	public Account(@JsonProperty("account") int account, @JsonProperty("cpf") String cpf, @JsonProperty("email") String email,
			@JsonProperty("agency") int agency, @JsonProperty("name") String name) {
		super();
		this.account = account;
		this.setBalance(balance);
		this.cpf = cpf;
		this.setEmail(email);
		this.name = name;
		this.agency = agency;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAgency() {
		return agency;
	}

	public void setAgency(int agency) {
		this.agency = agency;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
