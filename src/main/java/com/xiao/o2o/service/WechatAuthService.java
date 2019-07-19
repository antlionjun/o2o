package com.xiao.o2o.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.xiao.o2o.dto.WechatAuthExecution;
import com.xiao.o2o.entity.WechatAuth;
import com.xiao.o2o.exception.WechatAuthOperationException;

public interface WechatAuthService {

	/**
	 * 通过openId查找平台对应的微信账号
	 * @param openId
	 * @return
	 */
	WechatAuth getWechatAuthByOpenId(String openId);

	/**
	 * 注册本平台的微信账号
	 * @param wechatAuth
	 * @param profileImg
	 * @return
	 * @throws RuntimeException
	 */
	WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException;

}
