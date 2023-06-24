package br.com.bytebank.domain.account.controllers;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bytebank.domain.account.dtos.AccountResponseDTO;
import br.com.bytebank.domain.account.models.DepositBodyDTO;
import br.com.bytebank.domain.account.services.AccountService;
import br.com.bytebank.domain.account.utils.AccountHttpUtil;

/**
 * Servlet implementation class DepositController
 */
@WebServlet("/account/deposit")
public class DepositController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private AccountService accountService;
	
	private AccountHttpUtil accountHttpUtil;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DepositController() {
		super();
		// TODO Auto-generated constructor stub
		accountService = new AccountService();
		accountHttpUtil = AccountHttpUtil.getIntance();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json");

		boolean updated = false;
		
		DepositBodyDTO body = accountHttpUtil.<DepositBodyDTO>getRequestBody(request, DepositBodyDTO.class);

		if (body.getAccount() == null || body.getValue() == null) {

			accountHttpUtil.dispatchErrorResponse(response,
					"Missing account or value attributes of request body");
			return;
		}

		if (body.getValue().compareTo(BigDecimal.ZERO) < 0) {

			accountHttpUtil.dispatchErrorResponse(response, "The accound attribute can't be negative");
			return;
		}

		updated = this.accountService.deposit(body.getAccount(), body.getValue());

		if (!updated) {
			accountHttpUtil.dispatchErrorResponse(response, "I wasn't possible to make the deposit");
			return;
		}

		accountHttpUtil.<AccountResponseDTO>dispatchSuccessResponse(response,
				new AccountResponseDTO("Transfer done successfully"));
	}

}
