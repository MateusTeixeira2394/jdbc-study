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

		if (accountParam == null && idParam == null) {
			
			accountHttpUtil.dispatchErrorResponse(resp, "Missing account or id query parameters");
			
			return;
		}

		Account account = this.accountService.getAccount(idParam, accountParam);

		if (account == null) {
			
			accountHttpUtil.dispatchErrorResponse(resp, "Account not found");
			
			return;

		} 
		
		accountHttpUtil.<Account>dispatchSuccessResponse(resp, account);

		
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

		boolean resp = accountService.create(account);

		if (resp == false) {
			
			accountHttpUtil.dispatchErrorResponse(response, "Something wen wrong");
			return;
		}

		accountHttpUtil.<AccountResponseDTO>dispatchSuccessResponse(response, new AccountResponseDTO("Product created successfully"));

	}

}
