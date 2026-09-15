package com.qrazy.service;

import java.util.List;

import org.springframework.ui.Model;

import com.qrazy.bean.ProductBean;
import com.qrazy.entity.EntityProduct;

public interface ProductService {

	List<EntityProduct> findById(int id) throws Exception;

	List<EntityProduct> findAll() throws Exception;

	List<ProductBean> setDispValues(List<EntityProduct> findAll, Model model);

}
