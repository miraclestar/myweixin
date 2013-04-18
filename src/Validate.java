import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Servlet implementation class Validate
 */
public class Validate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Validate() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s = request.getParameter("echostr");
		response.getWriter().print(s);
		System.out.println("get echostr");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		this.responseMsg(request, out);
		out.flush();
		out.close();
	}

	private String readPostXML(HttpServletRequest request) throws IOException {
		BufferedReader sis = request.getReader();
		char[] buf = new char[1024];
		int len = 0;
		StringBuffer sb = new StringBuffer();
		while ((len = sis.read(buf)) != -1) {
			sb.append(buf, 0, len);
		}
		sb.toString();
		return sb.toString();
	}

	public void responseMsg(HttpServletRequest request, PrintWriter out) {
		System.out.println("responseMsg");
		String postStr = null;
		try {
			postStr = this.readPostXML(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("why? " + postStr);
		if (null != postStr && !postStr.isEmpty()) {
			Document document = null;
			try {
				document = DocumentHelper.parseText(postStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (null == document) {
				// out.print(" document is null ");
				System.out.println(" document is null !!!");
				return;
			}
			Element root = document.getRootElement();
			String fromUsername = root.elementText("FromUserName");

			String toUsername = root.elementText("ToUserName");
			String keyword = root.elementTextTrim("Content");
			String time = new Date().getTime() + "";
			String textTpl = "<xml>" + "<ToUserName><![CDATA[%1$s]]></ToUserName>" + "<FromUserName><![CDATA[%2$s]]></FromUserName>"
					+ "<CreateTime>%3$s</CreateTime>" + "<MsgType><![CDATA[%4$s]]></MsgType>" + "<Content><![CDATA[%5$s]]></Content>"
					+ "<FuncFlag>0</FuncFlag>" + "</xml>";
			System.out.println("fromUser: " + fromUsername + ", toUser: " + toUsername);
			if (null != keyword && !keyword.equals("")) {
				String msgType = "text";
				String contentStr = "Welcome to wechat world!";
				String resultStr = textTpl.format(textTpl, fromUsername, toUsername, time, msgType, contentStr);
				out.print(resultStr);
				System.out.println("resultStr: " + resultStr);
			} else {
				out.print("Input something...");
			}

		} else {
			out.print("");
		}
	}
}
