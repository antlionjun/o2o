package com.xiao.o2o.exception;

public class ShopOperationException extends RuntimeException{

	/**
	 * 需要序列化，生成随机序列化的ID
	 */
	private static final long serialVersionUID = -7391825322662155562L;
	
	public ShopOperationException(String msg) {
		super(msg);
	}
}
