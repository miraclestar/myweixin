package info.xmark.core;

import java.io.File;
import java.util.Random;

import org.apache.log4j.Logger;

public class Tools {
	private static Logger log = Logger.getLogger(Tools.class);

	public static String path = "D:/myown/weixin/ROOT";

	public static void setPath(String path) {
		Tools.path = path;
		log.info("path set !---" + path);
	}

	// 随机获取图片名字
	public static String randomPic(String type) {
		log.debug("type: " + type);
		String ret = "";
		try {
			String p = path + "tmp/" + type;
			File file = new File(p);

			String[] fls = file.list();

			log.debug("last path: " + p);
			int random = 0;
			if (fls != null) {
				random = new Random().nextInt(fls.length);
				ret = fls[random];
			}

			log.debug("ret file name : " + ret);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("get random file error: ", e);
		}
		return ret;
	}

	public static void main(String[] args) {
		randomPic("tka");
	}
}
