package com.xiao.o2o.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.xiao.o2o.dto.HeadLineExecution;
import com.xiao.o2o.entity.HeadLine;

public interface HeadLineService {
	public static final String HLLISTKEY = "headlinelist";

	/**
	 * 根据传入的条件返回指定的头条列表
	 * @param headLineCondition
	 * @return
	 * @throws IOException
	 */
	List<HeadLine> getHeadLineList(HeadLine headLineCondition)
			throws IOException;


}
