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
@Table(name = "learn")
public class EntityLearn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "question_en")
	private String question_en;

	@Column(name = "question_jp")
	private String question_jp;

	@Column(name = "answer_en")
	private String answer_en;

	@Column(name = "answer_jp")
	private String answer_jp;

}
