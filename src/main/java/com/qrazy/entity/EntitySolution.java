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
@Table(name = "solution")
public class EntitySolution {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "category_en")
	private String category_en;

	@Column(name = "category_jp")
	private String category_jp;

	@Column(name = "problem_en")
	private String problem_en;

	@Column(name = "problem_jp")
	private String problem_jp;

	@Column(name = "solution_en")
	private String solution_en;

	@Column(name = "solution_jp")
	private String solution_jp;

}
