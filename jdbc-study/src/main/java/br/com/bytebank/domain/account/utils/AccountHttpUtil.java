package br.com.bytebank.domain.account.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bytebank.domain.account.dtos.AccountResponseDTO;
import br.com.bytebank.domain.account.dtos.DepositBodyDTO;

public class AccountHttpUtil {

	private ObjectMapper objectMapper;
	
	private static AccountHttpUtil instance;

	public AccountHttpUtil() {
		objectMapper = new ObjectMapper();
	}
	
	public static AccountHttpUtil getIntance() {
		
		if(instance == null) {
			instance = new AccountHttpUtil();
		}
		
		return instance;
		
	}
	
	public void dispatchErrorResponse(HttpServletResponse response, String message) {

		PrintWriter out;

		try {

			out = response.getWriter();

			String json = objectMapper
					.writeValueAsString(new AccountResponseDTO(message));

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

			out.print(json);

			out.flush();

		} catch (IOException e) {

			throw new RuntimeException(e);

		}

	}

	public <T> void dispatchSuccessResponse(HttpServletResponse response, T dto) {

		PrintWriter out;

		try {

			out = response.getWriter();

			String json = objectMapper.writeValueAsString(dto);

			response.setStatus(HttpServletResponse.SC_OK);

			out.print(json);

			out.flush();

		} catch (IOException e) {

			throw new RuntimeException(e);

		}

	}
	
	public <T> T getRequestBody(HttpServletRequest request, Class<T> typeClass) {
		
		try {
			
			String jsonBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
			
			T body = objectMapper.readValue(jsonBody, typeClass);
			
			return body;
			
		} catch (IOException e) {			
			throw new RuntimeException(e); 
		}
		
	}

}
