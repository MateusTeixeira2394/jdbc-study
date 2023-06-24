package br.com.bytebank.domain.account.models;

import java.math.BigDecimal;

public class TransferBodyDTO {

	private Integer receiver;
	private Integer sender;
	private BigDecimal value;
	
	public TransferBodyDTO(Integer receiver, Integer sender, BigDecimal value) {
		super();
		this.receiver = receiver;
		this.sender = sender;
		this.value = value;
	}

	public Integer getReceiver() {
		return receiver;
	}

	public void setReceiver(Integer receiver) {
		this.receiver = receiver;
	}

	public Integer getSender() {
		return sender;
	}

	public void setSender(Integer sender) {
		this.sender = sender;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	

}
