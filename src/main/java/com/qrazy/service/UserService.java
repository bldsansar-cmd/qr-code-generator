package com.qrazy.service;

import java.util.List;

import org.springframework.ui.Model;

import com.qrazy.entity.EntityUser;

public interface UserService {

	List<EntityUser> findById(int id) throws Exception;

	List<EntityUser> findAll() throws Exception;

	void update(Model model) throws Exception;

}
