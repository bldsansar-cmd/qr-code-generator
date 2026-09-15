package com.qrazy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qrazy.entity.EntitySolution;

@Repository
public interface RepositorySolution extends JpaRepository<EntitySolution, Integer> {

	public List<EntitySolution> findById(int id);

	public List<EntitySolution> findAll();

}
