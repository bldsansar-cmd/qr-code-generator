package com.qrazy.bean;

import java.util.ArrayList;
import java.util.List;

import com.qrazy.util.Const;

import lombok.Data;

@Data
public class AjaxResponseBody {
	public String qr_path;

	public String file_name;

	public String orgBase64Data;

	public String plug_img;

	public List<String> base64DataList = new ArrayList<String>();

	public boolean syncResult = false;

	public String zipBase64Data = Const.EMPTY_TEXT;

}