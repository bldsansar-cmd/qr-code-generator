package com.qrazy.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.ui.Model;

import com.qrazy.bean.QrManagerBean;
import com.qrazy.entity.EntityQrManager;

public interface QrManagerService {

	List<EntityQrManager> findById(int id) throws Exception;

	List<EntityQrManager> findAll() throws Exception;

	List<QrManagerBean> setDispValues(List<EntityQrManager> findAll, Model model) throws ParseException;

}
