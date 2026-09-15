package com.qrazy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.qrazy.bean.SessionBean;
import com.qrazy.form.QrForm;
import com.qrazy.form.UserForm;
import com.qrazy.service.QrService;
import com.qrazy.service.UserService;
import com.qrazy.util.Const;
import com.qrazy.validator.order.ValidOrderGroup;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes(types = SessionBean.class)
public class UserController {

	@Autowired
	private QrService qrService;

	@Autowired
	private UserService userService;

	HttpServletRequest httpServletRequest = null;

	@PostMapping("login")
	public String login(Model model, @ModelAttribute SessionBean sessionBean,
			@ModelAttribute("form") @Validated(ValidOrderGroup.class) QrForm form, BindingResult bindingResult,
			@RequestParam("file") MultipartFile file) throws Exception {

		if (bindingResult.hasErrors()) {

			qrService.validationErrorHandling(model, form, bindingResult, file);

			return Const.PAGE_INDEX;

		}

		sessionBean.setAuthorizedUser(true);

		qrService.login(model, file);

		return Const.PAGE_INDEX;

	}

	@PostMapping("logout")
	public String logout(Model model, @ModelAttribute("form") QrForm form, @ModelAttribute SessionBean sessionBean,
			@RequestParam("file") MultipartFile file) throws Exception {

		sessionBean.setAuthorizedUser(false);

		sessionBean.resetUser();

		qrService.logout(model, file);

		return Const.PAGE_INDEX;

	}

	@PostMapping("userUpdate")
	public String userUpdate(Model model, @ModelAttribute("form") UserForm form,
			@ModelAttribute SessionBean sessionBean) throws Exception {

		userService.update(model);

		return Const.PAGE_USER;

	}
}