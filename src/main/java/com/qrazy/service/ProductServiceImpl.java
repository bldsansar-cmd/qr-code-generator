package com.qrazy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.qrazy.bean.ProductBean;
import com.qrazy.entity.EntityProduct;
import com.qrazy.repository.RepositoryProduct;
import com.qrazy.util.Util;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private RepositoryProduct product;

	@Autowired
	private Util util;

	public List<EntityProduct> findAll() {
		return product.findAll(Sort.by(Sort.Direction.ASC, "id"));

	}

	@Override
	public List<ProductBean> setDispValues(List<EntityProduct> findAll, Model model) {

		List<ProductBean> result = new ArrayList<ProductBean>();

		for (EntityProduct entity : findAll) {

			ProductBean single = new ProductBean();

			single.setId(entity.getId());

			if (util.isLocaleJA(model)) {

				single.setName(entity.getName_jp());

				single.setSummary(entity.getSummary_jp());

				single.setDetail(entity.getDetail_jp());

			} else {

				single.setName(entity.getName_en());

				single.setSummary(entity.getSummary_en());

				single.setDetail(entity.getDetail_en());

			}

			result.add(single);

		}

		return result;

	}

	@Override
	public List<EntityProduct> findById(int id) throws Exception {

		return product.findById(id);

	}
}