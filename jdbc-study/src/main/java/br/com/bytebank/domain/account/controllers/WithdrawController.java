package br.com.bytebank.domain.account.controllers;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.bytebank.domain.account.dtos.AccountResponseDTO;
import br.com.bytebank.domain.account.dtos.DepositBodyDTO;
import br.com.bytebank.domain.account.dtos.WithdrawBodyDTO;
import br.com.bytebank.domain.account.services.AccountService;
import br.com.bytebank.domain.account.utils.AccountHttpUtil;

/**
 * Servlet implementation class WithdrawController
 */
@WebServlet("/account/withdraw")
public class WithdrawController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private AccountService accountService;

	private AccountHttpUtil accountHttpUtil;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WithdrawController() {
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

		DepositBodyDTO body = accountHttpUtil.<WithdrawBodyDTO>getRequestBody(request, WithdrawBodyDTO.class);

		if (body.getAccount() == null || body.getValue() == null) {

			accountHttpUtil.dispatchErrorResponse(response, "Missing account or value attributes of request body");
			return;
		}

		if (body.getValue().compareTo(BigDecimal.ZERO) < 0) {

			accountHttpUtil.dispatchErrorResponse(response, "The value attribute can't be negative");
			return;
		}

		updated = this.accountService.withdraw(body.getAccount(), body.getValue());

		if (!updated) {
			accountHttpUtil.dispatchErrorResponse(response, "It wasn't possible to make the withdraw");
			return;
		}

		accountHttpUtil.<AccountResponseDTO>dispatchSuccessResponse(response,
				new AccountResponseDTO("Withdraw done successfully"));

	}

}
