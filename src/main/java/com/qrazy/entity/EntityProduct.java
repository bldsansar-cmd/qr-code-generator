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
@Table(name = "product")
public class EntityProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name_en")
	private String name_en;

	@Column(name = "name_jp")
	private String name_jp;

	@Column(name = "summary_en")
	private String summary_en;

	@Column(name = "summary_jp")
	private String summary_jp;

	@Column(name = "detail_en")
	private String detail_en;

	@Column(name = "detail_jp")
	private String detail_jp;

}
