package com.qrazy.service;

import java.util.List;

import org.springframework.ui.Model;

import com.qrazy.bean.LearnBean;
import com.qrazy.entity.EntityLearn;

public interface LearnService {

	List<EntityLearn> findAll() throws Exception;

	List<LearnBean> setDispValues(List<EntityLearn> findAll, Model model);

}
