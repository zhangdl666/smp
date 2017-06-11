package com.platform.business.service;

import java.util.List;

import com.platform.business.pojo.Product;
import com.platform.core.bo.Page;

public interface ProductService {

	public Product saveProduct(Product product);

	public Product getProduct(String id);

	public Page queryProduct(String name, Page page);
	
	public List<Product> queryProduct(String name);
}
