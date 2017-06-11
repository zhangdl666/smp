package com.platform.business.service;

import java.util.List;

import com.platform.business.dao.ProductDao;
import com.platform.business.pojo.Product;
import com.platform.core.bo.Page;

public class ProductServiceImpl implements ProductService {

	private ProductDao productDao;
	
	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public Product saveProduct(Product product) {
		return productDao.saveProduct(product);
	}

	@Override
	public Product getProduct(String id) {
		return productDao.getProduct(id);
	}

	@Override
	public Page queryProduct(String name, Page page) {
		return productDao.queryProduct(name, page);
	}

	@Override
	public List<Product> queryProduct(String name) {
		return productDao.queryProduct(name);
	}

}
