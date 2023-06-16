package br.com.bytebank.domain.account.controllers;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bytebank.domain.account.daos.AccountDAO;
import br.com.bytebank.domain.account.dtos.AccountResponseDTO;
import br.com.bytebank.domain.account.models.Account;
import br.com.bytebank.domain.account.services.AccountService;
import br.com.bytebank.infra.db.ConnectionFactory;

/**
 * Servlet implementation class CreateAccountController
 */
@WebServlet("/account")
public class AccountController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private AccountService accountService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountController() {
		super();
		// TODO Auto-generated constructor stub
		accountService = new AccountService();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String requestBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

		ObjectMapper objectMapper = new ObjectMapper();
		
		try {			
			
			Account account = objectMapper.readValue(requestBody, Account.class);									

			boolean resp = accountService.create(account);															
			
			if(resp == false) {							
				
				String json = objectMapper.writeValueAsString(new AccountResponseDTO("Something went wrong"));
				
				response.setStatus(500);
				
				response.getWriter().write(json);
				
			}						
			
			String json = objectMapper.writeValueAsString(new AccountResponseDTO("Product created successfully"));
			
			response.getWriter().write(json);
			
		} catch(JsonProcessingException  e) {
			
			e.printStackTrace();
		}

		
	}

}
