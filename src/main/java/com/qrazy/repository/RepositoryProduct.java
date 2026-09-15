package com.qrazy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qrazy.entity.EntityProduct;

@Repository
public interface RepositoryProduct extends JpaRepository<EntityProduct, Integer> {

	public List<EntityProduct> findById(int id);

	public List<EntityProduct> findAll();

}
