package com.tgb.lk.demo.appserver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SynDataServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("--------------get--------------");
		//处理中文乱码问题解决方法
		String name = new String(req.getParameter("name").getBytes("iso-8859-1"),"UTF-8");
		String age = req.getParameter("age");
		String classes = new String(req.getParameter("classes").getBytes("iso-8859-1"),"UTF-8");
		resp.setContentType("text/xml; charset=UTF-8");  
		
		PrintWriter out = resp.getWriter();
		out.println("GET method");
		out.print(name + "|" +age+ "|" + classes);
		out.flush();
		out.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("--------------post--------------");
		//处理中文乱码问题解决方法
		String name = new String(req.getParameter("name").getBytes("iso-8859-1"),"UTF-8");
		String age = req.getParameter("age");
		String classes = new String(req.getParameter("classes").getBytes("iso-8859-1"),"UTF-8");
		resp.setContentType("text/xml; charset=UTF-8");  
		System.out.println("name : "+name);
		System.out.println("age : "+age);
		System.out.println("classes : "+classes);
		
		PrintWriter out = resp.getWriter();
		out.println("POST method");
		out.print(name + "|||" +age+ "|||" + classes);
		out.flush();
		out.close();
	}

}
