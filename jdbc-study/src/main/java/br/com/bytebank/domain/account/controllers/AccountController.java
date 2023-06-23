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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = resp.getWriter();

		String json = "";

		ObjectMapper objectMapper = new ObjectMapper();

		String accountParam = req.getParameter("account");

		String idParam = req.getParameter("id");

		if (accountParam == null && idParam == null) {

			json = objectMapper.writeValueAsString(new AccountResponseDTO("Missing account or id query param"));

			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

			out.print(json);

			out.flush();
		}

		Account account = this.accountService.getAccount(idParam, accountParam);

		if (account == null) {

			json = objectMapper.writeValueAsString(new AccountResponseDTO("Account not found"));

			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		} else {

			json = objectMapper.writeValueAsString(account);

		}

		out.print(json);

		out.flush();

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

			PrintWriter out = response.getWriter();

			String json = objectMapper.writeValueAsString(new AccountResponseDTO("Product created successfully"));

			if (resp == false) {

				json = objectMapper.writeValueAsString(new AccountResponseDTO("Something went wrong"));

				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

			}

			out.print(json);

			out.flush();

		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

	}

}
