package br.com.bytebank.domain.account.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bytebank.domain.account.dtos.AccountResponseDTO;
import br.com.bytebank.domain.account.models.Account;
import br.com.bytebank.domain.account.services.AccountService;
import br.com.bytebank.domain.account.utils.AccountHttpUtil;

/**
 * Servlet implementation class CreateAccountController
 */
@WebServlet("/account")
public class AccountController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private AccountService accountService;

	private AccountHttpUtil accountHttpUtil;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountController() {
		super();
		// TODO Auto-generated constructor stub
		accountService = new AccountService();
		accountHttpUtil = AccountHttpUtil.getIntance();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		resp.setContentType("application/json");

		String accountParam = req.getParameter("account");

		String idParam = req.getParameter("id");

		try {
			
			Account account = this.accountService.getAccount(idParam, accountParam);
			
			accountHttpUtil.<Account>dispatchSuccessResponse(resp, account);
			
		} catch(RuntimeException e) {
			
			accountHttpUtil.dispatchErrorResponse(resp, e.getMessage());
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("application/json");

		Account account = accountHttpUtil.<Account>getRequestBody(request, Account.class);

		try {
		
			accountService.create(account);

			accountHttpUtil.<AccountResponseDTO>dispatchSuccessResponse(response, new AccountResponseDTO("Product created successfully"));
			
		} catch (RuntimeException e) {
			
			accountHttpUtil.dispatchErrorResponse(response, e.getMessage());
			
		}
		

	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		resp.setContentType("application/json");
		
		String accountParam = req.getParameter("account"); 
		
		try {
			
			this.accountService.deleteByNumber(accountParam);
			
			this.accountHttpUtil.<AccountResponseDTO>dispatchSuccessResponse(resp, new AccountResponseDTO("Account deleted successfully"));
			
		} catch(RuntimeException e) {
			
			this.accountHttpUtil.dispatchErrorResponse(resp, e.getMessage());
			
		}
		
	}

}
