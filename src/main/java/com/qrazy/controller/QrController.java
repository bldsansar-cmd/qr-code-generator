package com.qrazy.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.qrazy.bean.AjaxResponseBody;
import com.qrazy.bean.SessionBean;
import com.qrazy.form.QrForm;
import com.qrazy.service.QrService;
import com.qrazy.util.Const;
import com.qrazy.validator.order.ValidOrderGroup;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@SessionAttributes(types = SessionBean.class)
public class QrController {

	@Autowired(required = true)
	private QrService qrService;

	HttpServletRequest httpServletRequest = null;

	@ModelAttribute("sessionBean")
	public SessionBean sessionBean() throws Exception {

		return new SessionBean();

	}

	@GetMapping({ "/" })
	public String index(Model model, @ModelAttribute("form") QrForm form, @ModelAttribute SessionBean sessionBean)
			throws Exception {

		sessionBean.init();

		qrService.init(model);

		return Const.PAGE_INDEX;

	}

	@GetMapping("/dynamic/{uniq_number}")
	public String getProductById(@PathVariable("uniq_number") String uniq_number, Model model) throws Exception {

		UriComponentsBuilder builder = UriComponentsBuilder.newInstance();

		URI location = builder.scheme("https").host(qrService.getDirectUrl(uniq_number)).path("").build().toUri();

		return "redirect:" + location.toString();

	}

	@PostMapping("reset")
	public String reset(Model model, @ModelAttribute("form") QrForm form, @RequestParam("file") MultipartFile file,
			@ModelAttribute SessionBean sessionBean) throws Exception {

		sessionBean.init();

		qrService.init(model);

		return Const.PAGE_INDEX;

	}

	@PostMapping("change")
	public String change(Model model, @ModelAttribute("form") QrForm form, @RequestParam("file") MultipartFile file,
			@ModelAttribute SessionBean sessionBean) throws Exception {

		sessionBean.init();

		qrService.change(model, file);

		return Const.PAGE_INDEX;

	}

	@PostMapping("createByAsync")
	public ResponseEntity<?> createByAsync(Model model, @ModelAttribute("form") QrForm form,
			@RequestParam("file") MultipartFile file) throws Exception {

		AjaxResponseBody responseBody = qrService.createByAsyncRequest(form, model, file);

		return ResponseEntity.ok(responseBody);

	}

	@PostMapping("create")
	public String create(Model model, @RequestParam("file") MultipartFile file, @ModelAttribute SessionBean sessionBean,

			@ModelAttribute("form") @Validated(ValidOrderGroup.class) QrForm form, BindingResult bindingResult)
			throws Exception {

		if (bindingResult.hasErrors()) {

			qrService.validationErrorHandling(model, form, bindingResult, file);

			return Const.PAGE_INDEX;

		}

		try {

			form.setInitial(false);

			qrService.create(model, file);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return Const.PAGE_INDEX;

	}

	@PostMapping("saveByAsync")
	public ResponseEntity<?> saveByAsync(Model model, @ModelAttribute("form") QrForm form, MultipartFile file)
			throws Exception {

		AjaxResponseBody responseBody = qrService.saveByAsyncRequest(model, file);

		return ResponseEntity.ok(responseBody);

	}

	@PostMapping("download")
	public void downloadraw(Model model, @RequestParam("file") MultipartFile file,

			@ModelAttribute("form") QrForm form, HttpServletResponse response, @ModelAttribute SessionBean sessionBean)
			throws Exception {

		qrService.download(model, response, file);

		return;

	}

	@PostMapping("save")
	public void save(Model model, @RequestParam("file") MultipartFile file,

			@ModelAttribute("form") QrForm form, HttpServletResponse response, @ModelAttribute SessionBean sessionBean)
			throws Exception {

		qrService.save(model, response, file);

		return;

	}

	@PostMapping("back")
	public String back(Model model, HttpServletResponse response, @ModelAttribute SessionBean sessionBean)

			throws Exception {

		qrService.back(model);

		return sessionBean.getFuncNameList().get(sessionBean.getFuncNameList().size() - 1);

	}

	@PostMapping("product")
	public String product(Model model, @ModelAttribute("form") QrForm form,

			@ModelAttribute SessionBean sessionBean) throws Exception {

		qrService.product(model);

		return Const.PAGE_PRODUCT;

	}

	@PostMapping("solution")
	public String solution(Model model, @ModelAttribute("form") QrForm form,

			@ModelAttribute SessionBean sessionBean) throws Exception {

		qrService.solution(model);

		return Const.PAGE_SOLUTION;

	}

	@PostMapping("language")
	public String language(Model model, @ModelAttribute("form") QrForm form,

			@RequestParam("file") MultipartFile file,

			@ModelAttribute SessionBean sessionBean) throws Exception {

		qrService.language(model, file);

		return sessionBean.getFuncNameList().get(sessionBean.getFuncNameList().size() - 1);

	}

	@PostMapping("user")
	public String user(Model model, @ModelAttribute("form") QrForm form,

			@ModelAttribute SessionBean sessionBean) throws Exception {

		String pageName = Const.EMPTY_TEXT;

		if (sessionBean == null) {

			pageName = form.page_name;

			qrService.init(model);

		} else {

			pageName = Const.PAGE_USER;

			qrService.user(model);

		}

		return pageName;

	}

}