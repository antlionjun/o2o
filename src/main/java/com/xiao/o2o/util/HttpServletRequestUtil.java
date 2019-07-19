package com.xiao.o2o.util;

import javax.servlet.http.HttpServletRequest;

//解析request里面的参数
public class HttpServletRequestUtil {
	//将request对应可以的值转换为整型
	public static int getInt(HttpServletRequest request, String key) {
		try {
			return Integer.decode(request.getParameter(key));
		}catch(Exception e) {
			return -1;
		}	
	}
	
	//将request对应可以的值转换为长整型
	public static long getLong(HttpServletRequest request, String key) {
		try {
			return Long.valueOf(request.getParameter(key));
		}catch(Exception e) {
			return -1;
		}
	}
	//将request对应可以的值转换为布尔型
	public static boolean getBoolean(HttpServletRequest request, String key) {
		try {
			return Boolean.valueOf(request.getParameter(key));
		}catch(Exception e){
			return false;
		}
	}
	//将request对应可以的值转换为Double型
	public static double getDouble(HttpServletRequest request, String key) {
		try {
			return Double.valueOf(request.getParameter(key));
		}catch(Exception e) {
			return -1d;
		}
	}
	
	public static String getString(HttpServletRequest request, String key) {
		try {
			//try代码块执行完之后就会释放掉
			String result= request.getParameter(key);
			if(result != null) {
				//去掉字符串两端的空格
				result = result.trim();
			}
			if("".equals(result)) {
				result = null;
			}
			return result;
		}catch(Exception e) {
			return null;
		}
	}
	

}
