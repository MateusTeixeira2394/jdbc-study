package br.com.bytebank.domain.account.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bytebank.domain.account.models.Account;
import br.com.bytebank.domain.account.services.AccountService;

/**
 * Servlet implementation class AccountAllController
 */
@WebServlet("/account-all")
public class AccountAllController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private AccountService accountService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountAllController() {
        super();
        // TODO Auto-generated constructor stub
        accountService = new AccountService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ArrayList<Account> accounts = accountService.getAll();
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String json = objectMapper.writeValueAsString(accounts);
		
		response.setContentType("application/json");
		
		PrintWriter out = response.getWriter();
		
		out.print(json);
		
		out.flush();
	}

}
