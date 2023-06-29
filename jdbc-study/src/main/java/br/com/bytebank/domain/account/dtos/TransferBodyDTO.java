package br.com.bytebank.domain.account.dtos;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferBodyDTO {

	private Integer receiver;
	private Integer sender;
	private BigDecimal value;

	public TransferBodyDTO(@JsonProperty("receiver") Integer receiver, @JsonProperty("sender") Integer sender,
			@JsonProperty("value") BigDecimal value) {
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
