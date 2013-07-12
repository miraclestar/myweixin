package com.miracle.tool;

import info.xmark.base.DBPool;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class Test
 */
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(Test.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Test ");

		PrintWriter out = response.getWriter();
		out.print("ok test");
		String ret = null;
		Connection conn = DBPool.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from secret  order by create_dt desc LIMIT 20";
		log.debug("sql : " + sql);
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				out.print(rs.getString("secret") + "<br>");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("~~~~~~~~~~~~~~~ query secret error ! ", e);
		} finally {
			DBPool.getInstance().close(pstmt, rs, conn);
		}

		out.print("中文？？？: " + ret);
		out.flush();
		out.close();
	}

}
