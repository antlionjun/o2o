package com.xiao.o2o.service;

import com.xiao.o2o.dto.PersonInfoExecution;
import com.xiao.o2o.entity.PersonInfo;

public interface PersonInfoService {

	/**
	 * 
	 * @param userId
	 * @return
	 */
	PersonInfo getPersonInfoById(Long userId);

//	/**
//	 * 
//	 * @param personInfo
//	 * @return
//	 */
//	PersonInfoExecution addPersonInfo(PersonInfo personInfo);

	

}
