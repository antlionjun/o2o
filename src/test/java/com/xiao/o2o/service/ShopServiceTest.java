package com.xiao.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiao.o2o.BaseTest;
import com.xiao.o2o.dto.ImageHolder;
import com.xiao.o2o.dto.ShopExecution;
import com.xiao.o2o.entity.Area;
import com.xiao.o2o.entity.PersonInfo;
import com.xiao.o2o.entity.Shop;
import com.xiao.o2o.entity.ShopCategory;
import com.xiao.o2o.enums.ShopStateEnum;
import com.xiao.o2o.exception.ShopOperationException;

//表明ShopServiceTest需要spring去管理的

public class ShopServiceTest extends BaseTest{
	//初始化ShopServiceTest的时候就讲ShopService接口的实现类动态的注入进来
	@Autowired
	private ShopService shopService;
	
	@Test
	public void testGetShopList() {
		Shop shopCondition = new Shop();
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(2L);
		shopCondition.setShopCategory(sc);
		ShopExecution se = shopService.getShopList(shopCondition, 2, 4);
		System.out.println("店铺列表数为：" + se.getShopList().size());
		System.out.println("店铺总数：" + se.getCount());
				
	}
	@Ignore
	@Test
	public void testModifyShop() throws ShopOperationException,FileNotFoundException{
		Shop shop = new Shop();
		shop.setShopId(15L);
		shop.setShopName("不修改的店铺名称");
		File shopImg = new File("F:\\projectdev\\image\\dabai.jpg");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder("dabai.jpg", is);
		ShopExecution shopExecution = shopService.modifyShop(shop, imageHolder);
		System.out.println("新的图片地址为："+shopExecution.getShop().getShopImg());
	}
	@Ignore
	@Test
	public void testAddShop() throws FileNotFoundException {
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
		shop.setShopName("测试的店铺3");
		shop.setShopDesc("test3");
		shop.setShopAddr("test3");
		shop.setPhone("test3");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		File shopImg = new File("F:\\xiaohuangren .jpg");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder(shopImg.getName(), is);
		ShopExecution se = shopService.addShop(shop, imageHolder);
		assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
		
	}

}
