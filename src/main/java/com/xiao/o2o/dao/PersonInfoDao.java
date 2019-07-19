package com.xiao.o2o.dao;



import com.xiao.o2o.entity.PersonInfo;

public interface PersonInfoDao {

	/**
	 * 通过用户Id查询用户
	 * @param userId
	 * @return
	 */
	PersonInfo queryPersonInfoById(long userId);

	/**
	 * 添加用户信息
	 * @param wechatAuth
	 * @return
	 */
	int insertPersonInfo(PersonInfo personInfo);

}
