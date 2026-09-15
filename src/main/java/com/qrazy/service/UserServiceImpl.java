package com.qrazy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.qrazy.entity.EntityUser;
import com.qrazy.form.UserForm;
import com.qrazy.repository.RepositoryUser;
import com.qrazy.util.Const;
import com.qrazy.util.Util;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private Util util;

	@Autowired
	private QrManagerService qrManagerService;

	@Autowired
	private RepositoryUser repository;

	public List<EntityUser> findAll() {

		return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));

	}

	public List<EntityUser> findById(int id) throws Exception {

		return repository.findById(id);

	}

	@Transactional
	public void update(Model model) throws Exception {

		UserForm form = (UserForm) model.getAttribute(Const.OBJECT_FORM);

		EntityUser userEntity = new EntityUser();

		userEntity.setId(Integer.parseInt(form.getId()));

		userEntity.setUser_name(form.getUser_name());

		userEntity.setUser_email(form.getUser_email());

		userEntity.setUser_password(form.getUser_password());

		userEntity.setProfile_img(form.getProfile_img().split(Const.BASE64_INDICATR)[1]);

		repository.save(userEntity);

		form.setQrInfoList(qrManagerService.setDispValues(qrManagerService.findAll(), model));

		model.addAttribute(Const.OBJECT_FORM, form);

		util.setCurrentPageToSession(model, form.getPage_name());

		util.setUserToSession(model, userEntity);

	}

}
