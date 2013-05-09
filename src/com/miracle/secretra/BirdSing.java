package com.miracle.secretra;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * birdsing,小鸟唱歌一样，回复
 * 
 * @author hyliu
 * 
 */
public class BirdSing {
	/**
	 * 告诉你一个秘密
	 * 
	 * @param fromUsername
	 * @param toUsername
	 * @return
	 */

	private static Logger log = Logger.getLogger(BirdSing.class);

	public static String DEFAULTMM = "http://myweixin.cloudfoundry.com/tmp/dt/games-animated-gif-002.gif";

	// 回一个秘密
	public static String tellSecret(String fromUsername, String toUsername, String time) {
		String ret = "";
		String textPicTemplate = " <xml> <ToUserName><![CDATA[%1$s]]></ToUserName> <FromUserName><![CDATA[%2$s]]></FromUserName> <CreateTime>%3$s</CreateTime> <MsgType><![CDATA[news]]></MsgType> "
				+ "<ArticleCount>1</ArticleCount> <Articles> <item> <Title><![CDATA[%4$s]]></Title>  <Description><![CDATA[%5$s]]></Description>"
				+ " <PicUrl><![CDATA[%6$s]]></PicUrl> <Url><![CDATA[%7$s]]></Url> </item>  </Articles> <FuncFlag>1</FuncFlag> </xml> ";

		String picUrl = getRandomMM(fromUsername);

		ret = String.format(textPicTemplate, fromUsername, toUsername, time, "好吧，告诉你一个秘密", "这个秘密告诉你", picUrl, picUrl);
		return ret;
	}

	// 随即获取一个秘密
	private static String getRandomMM(String fromUsername) {

		String ret = DBDog.getSecretPic(fromUsername);
		if (null == ret || ret.equals("")) {
			ret = DEFAULTMM;
		}
		return ret;
	}

	// 回复内容
	public static String singAsong(String fromUsername, String toUsername, String time) {

		String ret = "";
		String textTemplate = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName><CreateTime>%3$s</CreateTime><MsgType><![CDATA[%4$s]]></MsgType><Content><![CDATA[%5$s]]></Content><FuncFlag>0</FuncFlag></xml>";
		String msgType = "text";
		String contentStr = "欢迎来到交换秘密，发送一个秘密给我，我也告诉你一个秘密，fair enough ~\n 特别提醒秘密必须是“图片格式” ,有图有真相~\n";
		ret = String.format(textTemplate, fromUsername, toUsername, time, msgType, contentStr);
		return ret;
	}

	public static String showSecret(String fromUsername, String toUsername, String time) {
		List<MM> ls = DAOcat.queryMMbyUid(fromUsername);

		return null;
	}

}