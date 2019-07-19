package com.xiao.o2o.interceptor.shopadmin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiao.o2o.entity.PersonInfo;

public class ShopLoginInterceptor extends HandlerInterceptorAdapter {
	/**
	 * 主要做事前拦截，即用户操作发生前，改写preHandle里的逻辑，进行拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//从session中取出用户信息来
		Object userObj = request.getSession().getAttribute("user");
		if (userObj != null) {
			//若用户信息不为空则将session里的用户信息转换成PersonInfo实体类对象
			PersonInfo user = (PersonInfo) userObj;
			if (user != null && user.getUserId() != null && user.getUserId() > 0 && user.getEnableStatus() == 1)
				return true;
		}
		//若不满足登陆验证，则直接跳转到账号登陆页面
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<script>");
		out.println("window.open ('" + request.getContextPath() + "/local/login?usertype=2','_self')");
		out.println("</script>");
		out.println("</html>");
		return false;
	}
}
