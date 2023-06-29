package br.com.bytebank.domain.account.dtos;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepositBodyDTO {

	private Integer account;
	private BigDecimal value;

	public DepositBodyDTO(@JsonProperty("account") Integer account, @JsonProperty("value") BigDecimal value) {
		this.account = account;
		this.value = value;
	}

	public Integer getAccount() {
		return account;
	}

	public void setAccount(Integer account) {
		this.account = account;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}
