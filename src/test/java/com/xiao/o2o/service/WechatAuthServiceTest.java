package com.xiao.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiao.o2o.BaseTest;
import com.xiao.o2o.dto.WechatAuthExecution;
import com.xiao.o2o.entity.PersonInfo;
import com.xiao.o2o.entity.WechatAuth;
import com.xiao.o2o.enums.WechatAuthStateEnum;

public class WechatAuthServiceTest extends BaseTest {
	@Autowired
	private WechatAuthService wechatAuthService;
	
	@Test
	public void testRegister() {
		//新增一条微信账号
		WechatAuth wechatAuth = new WechatAuth();
		PersonInfo personInfo = new PersonInfo();
		String openId = "dafasgfdf";
		//给微信账号设置上用户信息，但不设置上用户Id
		//希望创建微信账号的时候自动创建用户信息
		personInfo.setCreateTime(new Date());
		personInfo.setName("测试一下");
		personInfo.setUserType(1);
		wechatAuth.setPersonInfo(personInfo);
		wechatAuth.setOpenId(openId);
		wechatAuth.setCreateTime(new Date());
		WechatAuthExecution wae = wechatAuthService.register(wechatAuth);
		assertEquals(WechatAuthStateEnum.SUCCESS.getState(), wae.getState());
		//通过openID找到新增的wechatAuth
		wechatAuth = wechatAuthService.getWechatAuthByOpenId("ddfghgdhw");
		System.out.println(wechatAuth);
	}

}
