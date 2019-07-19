package com.xiao.o2o.util;

public class PathUtil {
	// 要将根路径设置在工程以外的目录中，或者另外的服务器中
	// 返回对应操作系统的分隔符
	//返回项目图片的根路径
	private static String seperator = System.getProperty("file.separator");

	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "F:/projectdev/image";
		} else {
			basePath = "/home/work/image";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
	
	//依据不同的业务需求返回项目图片的子路径

	public static String getShopImagePath(long shopId) {
		String imagePath = "/upload/item/shop/" + shopId + "/";
		return imagePath.replace("/", seperator);
	}

}
