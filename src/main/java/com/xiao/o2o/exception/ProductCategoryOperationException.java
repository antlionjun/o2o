package com.xiao.o2o.exception;

public class ProductCategoryOperationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2147606789416153376L;

	/**
	 * 需要序列化，生成随机序列化的ID
	 */
	
	public ProductCategoryOperationException(String msg) {
		super(msg);
	}
}



