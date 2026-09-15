package com.qrazy.service;

import java.util.List;

import org.springframework.ui.Model;

import com.qrazy.bean.SolutionBean;
import com.qrazy.entity.EntitySolution;

public interface SolutionService {

	List<EntitySolution> findById(int id) throws Exception;

	List<EntitySolution> findAll() throws Exception;

	List<SolutionBean> setDispValues(List<EntitySolution> findAll, Model model);

}
