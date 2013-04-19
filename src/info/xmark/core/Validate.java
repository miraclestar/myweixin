package info.xmark.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Validate
 */
public class Validate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s = request.getParameter("echostr");
		response.getWriter().print(s);
		System.out.println("get echostr");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		// System.out.println(request.getCharacterEncoding());
		// response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		Service.responseMsg(request, out);

		out.flush();
		out.close();
	}

	@Override
	public void init() throws ServletException {
		Tools.setPath(getServletContext().getRealPath("/"));
		// System.out.println(getServletContext().getRealPath("/"));
		super.init();
	}
}
