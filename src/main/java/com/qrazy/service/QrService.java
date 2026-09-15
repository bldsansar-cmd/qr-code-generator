package com.qrazy.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.qrazy.bean.AjaxResponseBody;
import com.qrazy.form.QrForm;

import jakarta.servlet.http.HttpServletResponse;

public interface QrService {

	void init(Model model) throws Exception;

	void create(Model model, MultipartFile file) throws Exception;

	void product(Model model) throws Exception;

	void solution(Model model) throws Exception;

	void user(Model model) throws Exception;

	void download(Model model, HttpServletResponse response, MultipartFile file) throws Exception;

	void language(Model model, MultipartFile file) throws Exception;

	void back(Model model) throws Exception;

	String getDirectUrl(String short_url) throws Exception;

	void login(Model model, MultipartFile file) throws Exception;

	void logout(Model model, MultipartFile file) throws Exception;

	void validationErrorHandling(Model model, QrForm form, BindingResult bindingResult, MultipartFile file)
			throws Exception;

	//	AjaxResponseBody createByAsyncRequest(AjaxRequestBody requestBody, Model model) throws Exception; 
	AjaxResponseBody createByAsyncRequest(QrForm form, Model model, MultipartFile file) throws Exception;

	void change(Model model, MultipartFile file) throws Exception;

	AjaxResponseBody saveByAsyncRequest(Model model, MultipartFile file) throws Exception;

	void save(Model model, HttpServletResponse response, MultipartFile file) throws Exception;

}