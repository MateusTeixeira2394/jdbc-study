package br.com.bytebank.domain.account.dtos;

public class AccountResponseDTO {
	
	private String message;

	public AccountResponseDTO(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
