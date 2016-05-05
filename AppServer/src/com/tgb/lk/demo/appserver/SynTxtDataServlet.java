package com.tgb.lk.demo.appserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SynTxtDataServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8"));  
		// Êý¾Ý   
		String retData = null;  
		String responseData = "²âÊÔ";  
		while ((retData = in.readLine()) != null) {  
			responseData += retData;  
		}  
		in.close();  

		resp.setContentType("text/xml; charset=UTF-8");  
		PrintWriter out = resp.getWriter();  
		out.print("POST-method");
//		out.print(responseData);  
		out.flush();  
		out.close();
	}
}
