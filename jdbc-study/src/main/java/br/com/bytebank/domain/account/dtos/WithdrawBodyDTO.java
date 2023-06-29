package br.com.bytebank.domain.account.dtos;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WithdrawBodyDTO extends DepositBodyDTO {

	public WithdrawBodyDTO(@JsonProperty("account") Integer account, @JsonProperty("value") BigDecimal value) {
		super(account, value);
		// TODO Auto-generated constructor stub
	}

}
