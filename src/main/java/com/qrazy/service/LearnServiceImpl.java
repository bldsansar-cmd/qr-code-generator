package com.qrazy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.qrazy.bean.LearnBean;
import com.qrazy.entity.EntityLearn;
import com.qrazy.repository.RepositoryLearn;
import com.qrazy.util.Util;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LearnServiceImpl implements LearnService {

	@Autowired
	private RepositoryLearn learn;

	@Autowired
	private Util util;

	public List<EntityLearn> findAll() {

		return learn.findAll(Sort.by(Sort.Direction.ASC, "id"));

	}

	@Override
	public List<LearnBean> setDispValues(List<EntityLearn> findAll, Model model) {

		List<LearnBean> result = new ArrayList<LearnBean>();

		for (EntityLearn entity : findAll) {

			LearnBean single = new LearnBean();

			single.setId(entity.getId());

			if (util.isLocaleJA(model)) {

				single.setQuestion(entity.getQuestion_jp());

				single.setAnswer(entity.getAnswer_jp());

			} else {

				single.setQuestion(entity.getQuestion_en());

				single.setAnswer(entity.getAnswer_en());

			}

			result.add(single);

		}

		return result;

	}

}
