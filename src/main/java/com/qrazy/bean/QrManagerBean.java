package com.qrazy.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrManagerBean {

	public int id;
	public String short_url;
	public String qr_name;
	public String qr_type;
	public String generate_type;
	public String qr_image_file_name;
	public String redirect_url;
	public String qr_image;
	public int access_count;
	private String uniq_number;
	private String create_date_time;
	private String update_date_time;

}
