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
import br.com.bytebank.domain.account.dtos.DepositBodyDTO;
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

		DepositBodyDTO body = accountHttpUtil.<DepositBodyDTO>getRequestBody(request, DepositBodyDTO.class);

		try {

			this.accountService.deposit(body.getAccount(), body.getValue());

			accountHttpUtil.<AccountResponseDTO>dispatchSuccessResponse(response,
					new AccountResponseDTO("Deposit done successfully"));

		} catch (RuntimeException e) {

			accountHttpUtil.dispatchErrorResponse(response, e.getMessage());

		}

	}

}
