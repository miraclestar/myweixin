package com.miracle.secretra;

import info.xmark.core.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.miracle.tool.DownPic;

public class SecretService {

	private static Logger log = Logger.getLogger(SecretService.class);

	public static String reply(HttpServletRequest request) {
		String ret = "";
		// 1、分析用户请求
		String postStr = null;
		try {
			postStr = Service.readPostXML(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("接收到： " + postStr);

		if (null != postStr && !postStr.isEmpty()) {
			Document document = null;
			try {
				document = DocumentHelper.parseText(postStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (null == document || document.equals("")) {
				log.info(" document is empty !!!");
				return "";
			}

			Element root = document.getRootElement();
			String fromUsername = root.elementText("FromUserName");
			String toUsername = root.elementText("ToUserName");
			String msgType = root.elementText("MsgType");
			String time = root.elementText("CreateTime");
			if (msgType != null) {
				if (msgType.equals("image")) {
					String picUrl = root.elementText("PicUrl");

					String md5 = "";
					try {
						md5 = DownPic.calcMD5(picUrl);
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					if (DBDog.checkmd5(md5)) {
						ret = BirdSing.singAsong("这个图片秘密已经上传过了，请不要重复上传，谢谢~", fromUsername, toUsername, time);
					} else {
						// 用户的秘密，需要回复给他一个秘密,保存秘密
						// 保存
						DBDog.saveSecret(fromUsername, picUrl, md5);
						// 回复一个秘密
						ret = BirdSing.tellSecret(fromUsername, toUsername, time);
					}

				} else if (msgType.equals("text")) {

					String content = root.elementText("Content");

					if (content.startsWith("WD")) {
						// 查看我的秘密
						ret = BirdSing.showSecret(fromUsername, toUsername, time);
					} else if (content.startsWith("CK")) {
						// 查看我留过言的 秘密
					} else if (content.startsWith("LY")) {
						// 留言

					} else if (content.startsWith("HF")) {
						// 回复
					} else {
						// 非命令，提醒用户使用方法
						ret = BirdSing.singAsong(fromUsername, toUsername, time);
					}
				} else if (msgType.equals("event")) {
					// 新用户订阅，提醒用户使用方法
					ret = BirdSing.singAsong(fromUsername, toUsername, time);
				}

			}
		}
		log.info("回复内容： " + ret);
		try {
			ret = new String(ret.getBytes("UTF-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		log.info("回复内容 iso-8859-1 ： " + ret);
		return ret;
	}
}
