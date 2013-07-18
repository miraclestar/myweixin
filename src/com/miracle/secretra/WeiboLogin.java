package com.miracle.secretra;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import weibo4j.Account;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

/**
 * Servlet implementation class WeiboLogin
 */
public class WeiboLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(WeiboLogin.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		// HttpSession session = request.getSession();

		String userid = "-1";
		String code = request.getParameter("code");
		Oauth oauth = new Oauth();
		try {
			log.debug(oauth.authorize("code", "UTF-8"));
			log.info("code:" + code);
			AccessToken accessToken = oauth.getAccessTokenByCode(code);
			String token = accessToken.getAccessToken();

			WeiboTool.token = token;

			log.debug("WeiboLogin   token:" + token);

			Account am = new Account();
			am.client.setToken(token);
			JSONObject uid = null;
			try {
				uid = am.getUid();
				Users um = new Users();
				um.client.setToken(token);
				try {
					userid = uid.getString("uid");
					User u = um.showUserById(uid.getString("uid"));

				} catch (WeiboException e) {
					e.printStackTrace();
					log.error("weibo Exception:", e);
				} catch (JSONException e) {
					e.printStackTrace();
					log.error("JSONException:", e);
				}
				log.debug("uid:" + uid);

			} catch (WeiboException e) {
				e.printStackTrace();
				log.error("auth error : ", e);
			}

			String status = WeiboTool.sendweibo("交换秘密，内测ing. 微信公众账号:secretra", token);

			log.debug("send weibo status to " + status);

		} catch (WeiboException e) {
			e.printStackTrace();
			log.error("weibo error: ", e);
		}
		response.sendRedirect("/tradesecret");
	}

}
