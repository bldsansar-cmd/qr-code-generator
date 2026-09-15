package com.qrazy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qrazy.entity.EntityQrManager;

@Repository
public interface RepositoryQrManager extends JpaRepository<EntityQrManager, Integer> {

	public List<EntityQrManager> findById(int id);

	//	@Query(value = "SELECT DISTINCT dynamic_qr_manager FROM EntityDynamicQrManager dynamic_qr_manager WHERE dynamic_qr_manager.uniq_number LIKE :uniq_number") 

	//    EntityDynamicQrManager findByUniq_number(@Param("uniq_number") String uniq_number); 

}
