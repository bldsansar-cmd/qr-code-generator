package com.qrazy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "qr_manager")
public class EntityQrManager {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "qr_name")
	private String qr_name;

	@Column(name = "qr_type")
	private String qr_type;

	@Column(name = "generate_type")
	private String generate_type;

	@Column(name = "short_url")
	private String short_url;

	@Column(name = "redirect_url")
	private String redirect_url;

	@Column(name = "qr_image_file_name")
	private String qr_image_file_name;

	@Column(name = "access_count")
	private int access_count;

	@Column(name = "uniq_number")
	private String uniq_number;

	@Column(name = "create_date_time")
	private String create_date_time;

	@Column(name = "update_date_time")
	private String update_date_time;

}
