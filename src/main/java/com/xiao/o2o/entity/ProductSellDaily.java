package com.xiao.o2o.entity;

import java.util.Date;

//顾客消费的商品映射
public class ProductSellDaily {
	
	//那天的销量，精确到天
	private Date createtime;
	//销量
	private Integer total;
	//商品信息实体类
	private Product product;
	//店铺信息实体类
	private Shop shop;
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	

}
