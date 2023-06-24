package br.com.bytebank.domain.account.models;

import java.math.BigDecimal;

public class WithdrawBodyDTO extends DepositBodyDTO {

	public WithdrawBodyDTO(Integer account, BigDecimal value) {
		super(account, value);
		// TODO Auto-generated constructor stub
	}

}
