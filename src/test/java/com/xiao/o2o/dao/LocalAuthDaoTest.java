package com.xiao.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiao.o2o.BaseTest;
import com.xiao.o2o.entity.LocalAuth;
import com.xiao.o2o.entity.PersonInfo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class LocalAuthDaoTest extends BaseTest {
	
	@Autowired
	private LocalAuthDao localAuthDao;
	
	private static final String username = "testusername";
	private static final String password = "testpassword";
	
	@Test
	public void testAInsertLocalAuth() throws Exception{
		//新增一条平台账号
		LocalAuth localAuth = new LocalAuth();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1L);
		//给平台账号绑定上用户信息
		localAuth.setPersonInfo(personInfo);
		//设置上用户名和密码
		localAuth.setUserName(username);
		localAuth.setPassword(password);
		localAuth.setCreateTime(new Date());
		int effectedNum = localAuthDao.insertLocalAuth(localAuth);
		assertEquals(1, effectedNum);
	}
	@Test
	public void testBQueryLocalByNameAndPwd() throws Exception{
		//按照账号和密码查询用户信息
		LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd(username, password);
		assertEquals("测试", localAuth.getPersonInfo().getName());
	}
	
	@Test
	public void testCQueryLocalByUserId() throws Exception{
		//按照用户Id查询平台账号，进而获取用户信息
		LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
		assertEquals("测试", localAuth.getPersonInfo().getName());
	}
	@Test
	public void testDUpdateLocalAuth() throws Exception{
		//依据用户Id,平台账号，以及旧密码修改平台账号密码
		Date now = new Date();
		int effectedNum = localAuthDao.updateLocalAuth(1L, username, password, password +"new", now);
		assertEquals(1, effectedNum);
		//查询出该条平台账号的最新信息
		LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
		//输出新密码
		System.out.println(localAuth.getPassword());
	}

}
