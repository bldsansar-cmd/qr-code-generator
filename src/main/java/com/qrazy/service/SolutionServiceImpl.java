package com.qrazy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.qrazy.bean.SolutionBean;
import com.qrazy.entity.EntitySolution;
import com.qrazy.repository.RepositorySolution;
import com.qrazy.util.Util;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SolutionServiceImpl implements SolutionService {

	@Autowired
	private RepositorySolution solution;

	@Autowired
	private Util util;

	public List<EntitySolution> findAll() {
		return solution.findAll(Sort.by(Sort.Direction.ASC, "id"));

	}

	@Override
	public List<SolutionBean> setDispValues(List<EntitySolution> findAll, Model model) {

		List<SolutionBean> result = new ArrayList<SolutionBean>();

		for (EntitySolution entity : findAll) {

			SolutionBean single = new SolutionBean();

			single.setId(entity.getId());

			if (util.isLocaleJA(model)) {

				single.setCategory(entity.getCategory_jp());

				single.setProblem(entity.getProblem_jp());

				single.setSolution(entity.getSolution_jp());

			} else {

				single.setCategory(entity.getCategory_en());

				single.setProblem(entity.getProblem_en());

				single.setSolution(entity.getSolution_en());

			}

			result.add(single);

		}
		return result;
	}

	public List<EntitySolution> findById(int id) throws Exception {
		return solution.findById(id);
	}
}