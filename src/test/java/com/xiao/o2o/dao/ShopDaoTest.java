package com.xiao.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiao.o2o.BaseTest;
import com.xiao.o2o.entity.Area;
import com.xiao.o2o.entity.PersonInfo;
import com.xiao.o2o.entity.Shop;
import com.xiao.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	@Autowired
	private ShopDao shopDao;
	
	
	@Test
	public void testQueryShopListAndCount() {
		Shop shopCondition = new Shop();
		ShopCategory childCategory = new ShopCategory();
		ShopCategory parentCategory = new ShopCategory();
		parentCategory.setShopCategoryId(3L);
		childCategory.setParent(parentCategory);
		shopCondition.setShopCategory(childCategory);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 6);
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println("店铺列表的大小：" + shopList.size());
		System.out.println("店铺总数量：" + count);
		
		
		
		
		
//		Shop shopCondition = new Shop();
//		PersonInfo owner = new PersonInfo();
//		owner.setUserId(1L);
//		shopCondition.setOwner(owner);
//		List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
//		int count = shopDao.queryShopCount(shopCondition);
//		System.out.println("店铺列表的大小：" + shopList.size());
//		System.out.println("店铺总数量：" + count);
		
//		ShopCategory sc = new ShopCategory();
//		sc.setShopCategoryId(2L);
//		shopCondition.setShopCategory(sc);
//		shopList = shopDao.queryShopList(shopCondition, 0, 2);
//		 count = shopDao.queryShopCount(shopCondition);
//		System.out.println("xin店铺列表的大小：" + shopList.size());
//		System.out.println("xin店铺总数量：" + count);
		
	}
	@Ignore
	@Test
	public void testQueryByShopId() {
		long shopId = 10;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println(shop.getShopCategory().getShopCategoryId());
		System.out.println(shop.getShopCategory().getShopCategoryName());
	}
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("中国蓝");
		shop.setShopDesc("测试测试");
		shop.setShopAddr("广东汕尾");
		shop.setPhone("124562");
		shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1,effectedNum);
	}
	
	@Test
	@Ignore
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopDesc("测试描述");
		shop.setShopAddr("测试地址");
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1,effectedNum);
	}
}
