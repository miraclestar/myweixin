package info.xmark.core;
import java.io.File;
import java.util.Random;

import org.apache.log4j.Logger;

public class Tools {
	private static Logger log = Logger.getLogger(Tools.class);

	public static String path = "D:/myown/weixin/ROOT";

	public static void setPath(String path) {
		Tools.path = path;
		System.out.println("path set !");
	}

	// 随机获取图片名字
	public static String randomPic(String type) {
		String p = path.replace("ROOT", "myweixin/tmp/" + type + "/");
		File file = new File(p);
		String[] fls = file.list();

		int random = new Random().nextInt(fls.length);
		log.debug(random);
		// for (int i = 0; i < fls.length; i++) {
		// System.out.println(fls[i] + "," + i);
		// }

		return fls[random];
	}

	public static void main(String[] args) {
		randomPic("xinggan");
	}
}
