package com.qrazy.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolutionBean {

	public int id;
	public String category;
	public String problem;
	public String solution;

}
