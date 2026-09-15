package com.qrazy.form;

import com.qrazy.bean.ProductBean;
import com.qrazy.util.Const;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm extends ParentForm {
	public String page_name = Const.PAGE_PRODUCT;
	public String product_id = Const.EMPTY_TEXT;
	public ProductBean detail = new ProductBean();
}