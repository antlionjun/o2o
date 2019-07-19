package com.xiao.o2o.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 主要用来解析路由并转发到相应的html中
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="local")
public class LocalController {
	
	/**
	 * 绑定账号页路由
	 * @return
	 */
	@RequestMapping(value="/accountbind", method = RequestMethod.GET)
	private String shopOperation() {
		//转发至店铺添加/编辑页面
		return "local/accountbind";
	}
	
	/**
	 * 修改密码路由页
	 * @return
	 */
	@RequestMapping(value="/changepsw", method = RequestMethod.GET)
	private String changepsw() {
		return "local/changepsw";
	}
	
	/**
	 * 登陆页路由
	 * @return
	 */
	@RequestMapping(value="/login", method = RequestMethod.GET)
	private String login() {
		return "local/login";
	}

	


}
