package com.qrazy.form;

import com.qrazy.util.Const;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferForm extends ParentForm {

	public String page_name = Const.PAGE_OFFER;
	public String offer_no = Const.EMPTY_TEXT;

}