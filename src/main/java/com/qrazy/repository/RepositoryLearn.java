package com.qrazy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qrazy.entity.EntityLearn;

@Repository
public interface RepositoryLearn extends JpaRepository<EntityLearn, Integer> {

	public List<EntityLearn> findById(int id);

}
