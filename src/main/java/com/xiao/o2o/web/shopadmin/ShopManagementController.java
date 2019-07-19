package com.xiao.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiao.o2o.dto.ImageHolder;
import com.xiao.o2o.dto.ShopExecution;
import com.xiao.o2o.entity.Area;
import com.xiao.o2o.entity.PersonInfo;
import com.xiao.o2o.entity.Shop;
import com.xiao.o2o.entity.ShopCategory;
import com.xiao.o2o.enums.ShopStateEnum;
import com.xiao.o2o.service.AreaService;
import com.xiao.o2o.service.ShopCategoryService;
import com.xiao.o2o.service.ShopService;
import com.xiao.o2o.util.CodeUtil;
import com.xiao.o2o.util.HttpServletRequestUtil;

//负责实现店铺管理相关的逻辑
@Controller
//指定其访问路径
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopManagementInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId <= 0) {
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if(currentShopObj == null) {
				modelMap.put("redirect", true);
				modelMap.put("url", "/o2o/shopadmin/shoplist");
			}else {
				Shop currentShop = (Shop) currentShopObj;
				modelMap.put("redirect", false);
				modelMap.put("shopId", currentShop.getShopId());
			}	
		}else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect", false);
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();

		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
			modelMap.put("shopList", se.getShopList());
			//列出店铺成功后，将店铺放入session中作为权限验证依据，即该账号只能操作它自己的店铺
			request.getSession().setAttribute("shopList", se.getShopList());
			modelMap.put("user", user);
			modelMap.put("success", true);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopById(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId > -1) {
			try {
				Shop shop = shopService.getByShopId(shopId);
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
			}catch(Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg",e.toString());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}
	
	@RequestMapping(value="/getshopinitinfo",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
		
	}
	
	/**
	 * 添加店铺方法
	 * @param request
	 * @return
	 */
	//指定方法的访问路径
	@RequestMapping(value="/registershop",method = RequestMethod.POST)
	//将键值对自动转换为json格式
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//1.接受并转化相应的参数，包括店铺信息以及图片信息
		//获取前端传递过来的信息并转化为相应的实体类
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		//获取前端传递过来的文件流，存储到shopImg里
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//判断文件流文件是否为空
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传的图片不能为空");
			return modelMap;
		}
		//2.注册店铺
		//3.返回结果
		//判断店铺和文件流信息是否为空
		if(shop != null && shopImg != null) {
			//从Session中获取操作用户信息
			PersonInfo owner = (PersonInfo)request.getSession().getAttribute("user");
			//把操作用户对象作为店铺对象的属性存入
			shop.setOwner(owner);
			ShopExecution se;
			try {
				//图片信息进行封装成ImageHolder对象
				ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
				System.out.println(shopImg.getOriginalFilename());
				//传入ImageHolder对象和店铺信息调用添加店铺方法
				se = shopService.addShop(shop, imageHolder);
				//判断添加店铺返回的结果是否与预期相符
				if(se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					//从Session中获取shopList对象
					List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
					//如果能过获取到shopList对象则把添加店铺返回的店铺返回的店铺信息添加进shopList并把shopList存入Session中
					if(shopList == null || shopList.size() == 0) {
						shopList = new ArrayList <Shop>();
					}
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList", shopList);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}	
	}

	//指定方法的访问路径
	@RequestMapping(value="/modifyshop",method = RequestMethod.POST)
	//将键值对自动转换为json格式
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//1.接受并转化相应的参数，包括店铺信息以及图片信息
		//获取前端传递过来的信息并转化为相应的实体类
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		//获取前端传递过来的文件流，存储到shopImg里
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		}
		//2.修改店铺信息
		//3.返回结果
		if(shop != null && shop.getShopId() != null) {
			ShopExecution se;
			try {
				System.out.println(shopImg.getOriginalFilename());
				if(shopImg == null) {
					se = shopService.modifyShop(shop, null);

				}else {
					ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
					se = shopService.modifyShop(shop, imageHolder);

				}

				if(se.getState() == ShopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺Id");
			return modelMap;
		}	
	}
	

}
