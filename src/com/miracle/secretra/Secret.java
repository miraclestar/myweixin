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

		log.info(request.getCharacterEncoding() + " : " + response.getCharacterEncoding());
		//
		// response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String ret = SecretService.reply(request);
		out.print(ret);
		log.info(ret);
		out.flush();
		out.close();
	}

}
