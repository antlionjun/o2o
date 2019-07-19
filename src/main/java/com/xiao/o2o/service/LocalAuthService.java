package com.xiao.o2o.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.xiao.o2o.dto.LocalAuthExecution;
import com.xiao.o2o.entity.LocalAuth;

public interface LocalAuthService {
	/**
	 * 通过账号和密码获取平台账号信息
	 * @param userName
	 * @return
	 */
	LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);

	/**
	 * 通过userId获取平台信息
	 * @param userId
	 * @return
	 */
	LocalAuth getLocalAuthByUserId(long userId);

	/**
	 * 注册平台账号
	 * @param localAuth
	 * @param profileImg
	 * @return
	 * @throws RuntimeException
	 */
//	LocalAuthExecution register(LocalAuth localAuth,
//			CommonsMultipartFile profileImg) throws RuntimeException;

	/**
	 * 绑定微信，生成平台专属账号
	 * @param localAuth
	 * @return
	 * @throws RuntimeException
	 */
	LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
			throws RuntimeException;

	/**
	 * 修改平台登录账号的密码
	 * @param localAuthId
	 * @param userName
	 * @param password
	 * @param newPassword
	 * @param lastEditTime
	 * @return
	 */
	LocalAuthExecution modifyLocalAuth(Long userId, String userName,
			String password, String newPassword);
}
