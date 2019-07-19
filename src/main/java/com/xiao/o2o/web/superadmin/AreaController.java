package com.xiao.o2o.web.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiao.o2o.entity.Area;
import com.xiao.o2o.service.AreaService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/superadmin")

public class AreaController {
	Logger logger = (Logger) LoggerFactory.getLogger(AreaController.class);
	
	@Autowired
	private AreaService areaService;
	//方式涉及到前端访问的都设置成小写；
	@RequestMapping(value = "/listarea",method = RequestMethod.GET)
	//自动将数据转换成json格式
	@ResponseBody
	private Map<String,Object> listArea(){
		logger.info("===start===");
		long startTime = System.currentTimeMillis();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		List<Area> list = new ArrayList<Area>();
		try {
			list = areaService.getAreaList();
			modelMap.put("rows", list);
			modelMap.put("total",list.size());
		}catch(Exception e) {
			e.printStackTrace();
			modelMap.put("success",false);
			modelMap.put("errMsg",e.toString());
		}
		logger.error("test error");
		long endTime = System.currentTimeMillis();
		logger.debug("costTime:[{}ms]",endTime - startTime);
		logger.info("===end===");
		return modelMap;
	}
	

}
