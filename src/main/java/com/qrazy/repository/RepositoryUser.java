package com.qrazy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qrazy.entity.EntityUser;

@Repository
public interface RepositoryUser extends JpaRepository<EntityUser, Integer> {

	public List<EntityUser> findById(int id);

	public List<EntityUser> findAll();

}
