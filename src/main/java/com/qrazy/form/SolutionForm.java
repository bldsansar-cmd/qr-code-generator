package com.qrazy.form;

import com.qrazy.bean.SolutionBean;
import com.qrazy.util.Const;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolutionForm extends ParentForm {

	public String page_name = Const.PAGE_SOLUTION;
	public String solution_id = Const.EMPTY_TEXT;
	public SolutionBean detail = new SolutionBean();

}
