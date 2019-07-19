package com.xiao.o2o.service;

import java.io.InputStream;

import com.xiao.o2o.dto.ImageHolder;
import com.xiao.o2o.dto.ShopExecution;
import com.xiao.o2o.entity.Shop;
import com.xiao.o2o.exception.ShopOperationException;

public interface ShopService {
	/**
	 * 根据shopCondition分页返回相应店铺列表
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	
	public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	
	/*
	 * 通过店铺Id获取店铺信息
	 * @param shopId
	 * @return
	 */
	Shop getByShopId(long shopId);
	
	/*
	 * 更新店铺信息，包括对图片的处理
	 */
	ShopExecution modifyShop(Shop shop,ImageHolder thumbnail)throws ShopOperationException;

	
	ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

	/**
	 * 更新店铺信息（从店家角度）
	 * 
	 * @param areaId
	 * @param shopAddr
	 * @param phone
	 * @param shopImg
	 * @param shopDesc
	 * @return
	 * @throws RuntimeException
	 */
	


}
