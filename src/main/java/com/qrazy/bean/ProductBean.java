package com.qrazy.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBean {

	private int id;
	private String name;
	private String summary;
	private String detail;

}