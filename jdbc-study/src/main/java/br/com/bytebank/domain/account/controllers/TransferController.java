package br.com.bytebank.domain.account.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.bytebank.domain.account.dtos.AccountResponseDTO;
import br.com.bytebank.domain.account.dtos.TransferBodyDTO;
import br.com.bytebank.domain.account.services.AccountService;
import br.com.bytebank.domain.account.utils.AccountHttpUtil;

/**
 * Servlet implementation class TransferController
 */
@WebServlet("/account/transfer")
public class TransferController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private AccountService accountService;

	private AccountHttpUtil accountHttpUtil;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TransferController() {
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
		
		TransferBodyDTO body = accountHttpUtil.getRequestBody(request, TransferBodyDTO.class);
		
		try {
			
			accountService.transfer(body.getSender(), body.getReceiver(), body.getValue());
			
			accountHttpUtil.dispatchSuccessResponse(response, new AccountResponseDTO("Transfer done successfully"));
			
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			
			accountHttpUtil.dispatchErrorResponse(response, e.getMessage());
			
		}
		
	}

}
