package com.miracle.secretra;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class Secret extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(Secret.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s = request.getParameter("echostr");
		response.getWriter().print(s);
		log.debug("validate weixin token ,get echostr: " + s);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		// System.out.println(request.getCharacterEncoding());
		// response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		out.print(SecretService.reply(request));

		out.flush();
		out.close();
	}

}
