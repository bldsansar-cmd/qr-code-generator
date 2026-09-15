package com.qrazy.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.qrazy.bean.AjaxResponseBody;
import com.qrazy.bean.FixedMsgBean;
import com.qrazy.bean.SessionBean;
import com.qrazy.bean.UtilBean;
import com.qrazy.config.MultiMessageSource;
import com.qrazy.entity.EntityQrManager;
import com.qrazy.form.ProductForm;
import com.qrazy.form.QrForm;
import com.qrazy.form.SolutionForm;
import com.qrazy.form.UserForm;
import com.qrazy.repository.RepositoryQrManager;
import com.qrazy.util.Const;
import com.qrazy.util.Properties;
import com.qrazy.util.QRCodeGenerator;
import com.qrazy.util.Util;

import jakarta.servlet.http.HttpServletResponse;

@Service

public class QrServiceImpl implements QrService {

	@Autowired

	private Properties prop;

	@Autowired

	private Util util;

	@Autowired

	private MultiMessageSource messageSource;

	@Autowired

	private LearnService learn;

	@Autowired

	private SolutionService solution;

	@Autowired

	private ProductService product;

	@Autowired

	private QRCodeGenerator generator;

	@Autowired

	private QrManagerService qrManagerService;

	@Autowired

	private UserService user;

	@Autowired

	private RepositoryQrManager repositoryQrManager;

	@Override

	public void init(Model model) throws Exception {

		QrForm form = new QrForm();

		setSelectedValues(form);

		setFixedValues(form, model);

		util.deleteFormFromSession(model);

		util.addFormToSession(model, form);

	}

	@Override

	public void change(Model model, MultipartFile file) throws Exception {

		QrForm form = (QrForm) model.getAttribute(Const.OBJECT_FORM);

		QrForm newForm = new QrForm();

		newForm.setQr_type(form.getQr_type());

		setSelectedValues(newForm);

		setFixedValues(newForm, model);

		util.deleteFormFromSession(model);

		util.addFormToSession(model, newForm);

	}

	@Override

	public void language(Model model, MultipartFile file) throws Exception {

		QrForm form = (QrForm) model.getAttribute(Const.OBJECT_FORM);

		SessionBean sessionBean = (SessionBean) model.getAttribute(Const.OBJECT_NAME_SESSION);

		if (form.getLocale().equals(Const.JA)) {

			sessionBean.setLocale(Locale.JAPANESE);

		} else {

			sessionBean.setLocale(Locale.ENGLISH);

		}

		setSelectedValues(form);

		setFixedValues(form, model);

		setQrImage(form, file);

		util.setFormToSession(model, form);

	}

	@Override

	public void login(Model model, MultipartFile file) throws Exception {

		QrForm form = (QrForm) model.getAttribute(Const.OBJECT_FORM);

		util.setUserToSession(model, user.findById(1).get(0));

		form.input_value_login_id = Const.EMPTY_TEXT;

		form.input_value_login_pass = Const.EMPTY_TEXT;

		form.operation_type = Const.DEFAULT_OPERATION_TYPE;

		setSelectedValues(form);

		setFixedValues(form, model);

		setQrImage(form, file);

		util.setFormToSession(model, form);

	}

	private void setQrImage(QrForm form, MultipartFile file) throws Exception {

		if (form.getQr_type().toUpperCase().equals(Const.NAV_BULK)) {

			List<String> inputValueList = util.getRealDataLIst(form.getInput_value_bulk().split(Const.LINE_SEPARATOR));

			form.inputValueBulkArray = inputValueList;

			if (inputValueList.size() > Const.MAX_BULK_NUMBER) {

				inputValueList = inputValueList.subList(0, Const.MAX_BULK_NUMBER);

			}

			for (String inputValueBulk : inputValueList) {

				createNormalQr(form, inputValueBulk, file);

			}

			if (inputValueList.size() > 1) {

				File zipImageFile = new File(prop.getImage_folder_path_appication_common() + Const.ZIP_FILE_NAME);

				form.setZipBase64Data(
						"data:image/" + form.getImg_type() + ";base64," + util.fileToBase64(zipImageFile));

			} else {

				form.setZipBase64Data("");

			}

		} else {

			form.getBase64DataList().add("data:image/" + form.getImg_type() + ";base64," + form.orgBase64Data);

		}

	}

	private void createNormalQr(QrForm form, String inputValue, MultipartFile file) throws Exception {

		byte[] imageData = null;

		String base64Data = null;

		imageData = generator.generateQRCode(form, inputValue, file);

		base64Data = Base64.getEncoder().encodeToString(imageData);

		form.getBase64DataList().add("data:image/" + form.getImg_type() + ";base64," + base64Data);

		//		form.orgBase64Data += Const.BASE64_INDICATOR + base64Data; 

		form.orgBase64Data += base64Data;

	}

	@Override

	public void logout(Model model, MultipartFile file) throws Exception {

		QrForm form = (QrForm) model.getAttribute(Const.OBJECT_FORM);

		form.input_value_login_id = Const.EMPTY_TEXT;

		form.input_value_login_pass = Const.EMPTY_TEXT;

		setSelectedValues(form);

		setFixedValues(form, model);

		setQrImage(form, file);

		util.setFormToSession(model, form);

	}

	@Override

	public void back(Model model) throws Exception {

		util.deleteFormFromSession(model);

		SessionBean sessionBean = (SessionBean) model.getAttribute(Const.OBJECT_NAME_SESSION);

		QrForm form = (QrForm) sessionBean.getFuncFormList().get(sessionBean.getFuncFormList().size() - 1);

		setSelectedValues(form);

		setFixedValues(form, model);

		model.addAttribute(Const.OBJECT_FORM, form);

	}

	@Override

	public AjaxResponseBody createByAsyncRequest(QrForm form, Model model, MultipartFile file) throws Exception {

		AjaxResponseBody result = null;

		util.setFormToSession(model, form);

		String inputValue = "";

		try {

			if (form.getQr_type().toUpperCase().equals(Const.NAV_WEB)) {

				inputValue = form.getInput_value_web();

			} else if (form.getQr_type().toUpperCase().equals(Const.NAV_TEXT)) {

				inputValue = form.getInput_value_text();

			} else if (form.getQr_type().toUpperCase().equals(Const.NAV_WIFI)) {

				inputValue = "WIFI:S:" + form.getInput_value_wifi_id() + ":T:WPA;P:" + form.getInput_value_wifi_pass()
						+ ";;";

			} else if (form.getQr_type().toUpperCase().equals(Const.NAV_BULK)) {

				inputValue = form.getInput_value_bulk();

			}

			result = createQrByAsync(form, inputValue, file);

			result.setSyncResult(true);

		} catch (WriterException | IOException e) {

			e.printStackTrace();

		}

		return result;

	}

	@Override

	public AjaxResponseBody saveByAsyncRequest(Model model, MultipartFile file) throws Exception {

		AjaxResponseBody result = new AjaxResponseBody();

		SessionBean sessionBean = (SessionBean) model.getAttribute(Const.OBJECT_NAME_SESSION);

		QrForm form = (QrForm) sessionBean.getFuncFormList().get(sessionBean.getFuncFormList().size() - 1);

		Date date = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		for (int i = 0; i < form.getBase64DataList().size(); i++) {

			String file_name = System.currentTimeMillis() + Const.DOT + form.getImg_type();

			byte[] decodedBytes = Base64.getDecoder().decode(form.getBase64DataList().get(i));

			// Write the decoded bytes to a file 

			try (FileOutputStream fos = new FileOutputStream(prop.getImage_folder_path_qr_code() + file_name)) {

				fos.write(decodedBytes);

			}

			UtilBean utilBean = new UtilBean();

			EntityQrManager entity = new EntityQrManager();

			entity.setQr_name(form.getInput_value_qr_name());

			entity.setQr_type(form.getQr_type());

			entity.setGenerate_type(form.getQr_generate_type());

			entity.setQr_image_file_name(form.getFile_name());

			entity.setShort_url(utilBean.getStrValue1());

			entity.setRedirect_url(form.getInput_value_web());

			if (Const.EMPTY_TEXT.equals(form.getQr_id())) {

				entity.setUniq_number(utilBean.getLongValue1() + "");

				entity.setCreate_date_time(sdf.format(date));

				repositoryQrManager.save(entity);

			} else {

				entity.setId(Integer.parseInt(form.getQr_id()));

				entity.setUpdate_date_time(sdf.format(date));

				repositoryQrManager.save(entity);

			}

			result.setSyncResult(true);

		}

		return result;

	}

	@Override

	public void create(Model model, MultipartFile file) throws Exception {

		QrForm form = (QrForm) model.getAttribute(Const.OBJECT_FORM);

		form.operation_type = Const.DEFAULT_OPERATION_TYPE;

		util.setFormToSession(model, form);

		createPngImg(form, file);

		setSelectedValues(form);

		setFixedValues(form, model);

	}

	@Override

	public void download(Model model, HttpServletResponse response, MultipartFile file) throws Exception {

		QrForm form = (QrForm) model.getAttribute(Const.OBJECT_FORM);

		if (form.getImg_type().equals(Const.IMG_TYPE_SVG)) {

			createDownloadSvg(model, response, file);

		} else {

			createDownloadPng(model, response, file);

		}

	}

	private void createDownloadSvg(Model model, HttpServletResponse response, MultipartFile file) throws Exception {

		QrForm form = (QrForm) model.getAttribute(Const.OBJECT_FORM);

		String svg = createSvgImg(form, file);

		util.doDownloadSvg(svg, response, form);

	}

	private String createSvgImg(QrForm form, MultipartFile file) throws Exception {

		QRCodeWriter qrCodeWriter = new QRCodeWriter();

		BitMatrix bitMatrix = qrCodeWriter.encode(getValue(form), BarcodeFormat.QR_CODE,
				Integer.parseInt(form.getImg_size()), Integer.parseInt(form.getImg_size()));

		StringBuilder svgBuilder = new StringBuilder();

		svgBuilder.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"")

				.append(form.getImg_size()).append("\" height=\"").append(form.getImg_size())
				.append("\" shape-rendering=\"crispEdges\">");

		svgBuilder.append("<rect width=\"100%\" height=\"100%\" fill=\"white\"/>");

		for (int y = 0; y < Integer.parseInt(form.getImg_size()); y++) {

			for (int x = 0; x < Integer.parseInt(form.getImg_size()); x++) {

				if (bitMatrix.get(x, y)) {

					svgBuilder.append("<rect x=\"").append(x)

							.append("\" y=\"").append(y)

							.append("\" width=\"1\" height=\"1\" fill=\"black\"/>");

				}

			}

		}

		svgBuilder.append("</svg>");

		return svgBuilder.toString();

	}

	private void createDownloadPng(Model model, HttpServletResponse response, MultipartFile file) throws Exception {

		QrForm form = (QrForm) model.getAttribute(Const.OBJECT_FORM);

		createPngImg(form, file);

		if (form.base64DataList.size() > 1) {

			util.doDownloadZip(response, form);

		} else {

			util.doDownload(response, form);

		}

	}

	@Override

	public void save(Model model, HttpServletResponse response, MultipartFile file) throws Exception {

		QrForm form = (QrForm) model.getAttribute(Const.OBJECT_FORM);

		if (form.getImg_type().equals(Const.IMG_TYPE_SVG)) {

			//        	createSvgImg(qrImage); 

		} else {

			createPngImg(form, file);

		}

		List<String> orgBase64List = util.getRealDataLIst(form.getOrgBase64Data().split(Const.BASE64_INDICATOR));

	}

	private void createPngImg(QrForm form, MultipartFile file) throws Exception {

		String inputValue;

		try {

			if (!form.getQr_type().toUpperCase().equals(Const.NAV_BULK)) {

				inputValue = getValue(form);

				createNormalQr(form, inputValue, file);

			} else {

				setQrImage(form, file);

			}

		} catch (WriterException | IOException e) {

			e.printStackTrace();

		}

	}

	private String getValue(QrForm form) throws IOException {

		String inputValue = null;

		if (form.getQr_type().toUpperCase().equals(Const.NAV_WEB)) {

			inputValue = form.getInput_value_web();

		} else if (form.getQr_type().toUpperCase().equals(Const.NAV_TEXT)) {

			inputValue = form.getInput_value_text();

		} else if (form.getQr_type().toUpperCase().equals(Const.NAV_WIFI)) {

			inputValue = "WIFI:S:" + form.getInput_value_wifi_id() + ":T:WPA;P:" + form.getInput_value_wifi_pass()
					+ ";;";

		} else {

		}

		return inputValue;

	}

	private AjaxResponseBody createQrByAsync(QrForm form, String inputValue, MultipartFile file) throws Exception {

		AjaxResponseBody result = new AjaxResponseBody();

		if (form.getQr_type().toUpperCase().equals(Const.NAV_BULK)) {

			setQrImage(form, file);

		} else {

			createNormalQr(form, inputValue, file);

		}

		result.setOrgBase64Data(form.getOrgBase64Data());

		result.setFile_name(form.getFile_name());

		result.setQr_path(form.getQr_path());

		result.setPlug_img(form.getPlug_img());

		result.setZipBase64Data(form.getZipBase64Data());

		if (form.getBase64DataList().size() > 1) {

			result.setBase64DataList(form.getBase64DataList());

		} else {

			result.setOrgBase64Data(form.getOrgBase64Data());

		}

		return result;

	}

	@Override

	public void solution(Model model) throws Exception {

		QrForm form = (QrForm) model.getAttribute(Const.OBJECT_FORM);

		SolutionForm formNew = new SolutionForm();

		formNew.setSolution_id(form.getSolution());

		formNew.setDetail(
				solution.setDispValues(solution.findById(Integer.parseInt(form.getSolution())), model).get(0));

		util.addFormToSession(model, formNew);

	}

	@Override

	public void product(Model model) throws Exception {

		QrForm form = (QrForm) model.getAttribute(Const.OBJECT_FORM);

		ProductForm formNew = new ProductForm();

		formNew.setProduct_id(form.getProduct());

		formNew.setDetail(product.setDispValues(product.findById(Integer.parseInt(form.getProduct())), model).get(0));

		util.addFormToSession(model, formNew);

	}

	@Override

	public void user(Model model) throws Exception {

		UserForm formNew = new UserForm();

		formNew.setUserInfo(
				user.findById(((SessionBean) model.getAttribute(Const.OBJECT_NAME_SESSION)).getLoginUser().getId()),
				model);

		formNew.setQrInfoList(qrManagerService.setDispValues(qrManagerService.findAll(), model));

		util.addFormToSession(model, formNew);

	}

	private void setFixedValues(QrForm form, Model model) throws Exception {

		setListValues(form, model);

		setFixedMsgValues(form, model);

	}

	private void setFixedMsgValues(QrForm form, Model model) throws Exception {

		Object bean = new FixedMsgBean();

		SessionBean sessionBean = (SessionBean) model.getAttribute(Const.OBJECT_NAME_SESSION);

		for (Field field : bean.getClass().getDeclaredFields()) {

			field.setAccessible(true);

			field.set(bean, messageSource.getMessage(field.getName(), sessionBean));

		}

		sessionBean.setFixedMsgBean((FixedMsgBean) bean);

	}

	private void setListValues(QrForm form, Model model) throws Exception {

		form.setNavList(util.getMenuList(model, Const.INPUT_NAV));

		form.setLangList(util.getMenuList(model, Const.LANGUAGE));

		form.setMenuList(util.getMenuList(model, Const.INPUT_MENU));

		form.setUserMenuList(util.getMenuList(model, Const.USER_MENU));

		form.setPixelList(util.getImageMapList(model, Const.CUSTOMIZE_PIXEL));

		form.setFinderList(util.getImageMapList(model, Const.CUSTOMIZE_FINDER));

		form.setFrameList(util.getImageMapList(model, Const.CUSTOMIZE_FRAME));

		form.setTemplateList(util.getImageMapList(model, Const.CUSTOMIZE_TEMPLATE));

		form.setDynamicOnlyQrTypeList(util.getMenuList(model, Const.DYNAMIC_ONLY_QR_TYPES));

		form.setDynamicStaticOptionQrTypeList(util.getMenuList(model, Const.DYNAMIC_STATIC_OPTION_QR_TYPES));

		form.setImageTypeList(util.getSelectBoxList(Const.INPUT_IMG_TYPE, 0, model));

		form.setImageSizeList(util.getSelectBoxList(Const.INPUT_IMG_SIZE, 0, model));

		form.setBgColorList(util.getSelectBoxList(Const.INPUT_COLORS, 0, model));

		form.setFinderColorList(util.getSelectBoxList(Const.INPUT_COLORS, 1, model));

		form.setPixelColorList(util.getSelectBoxList(Const.INPUT_COLORS, 1, model));

		form.setLogoNeededList(util.getMenuList(model, Const.INPUT_LOGO_NEEDED));

		form.setQrGenerateTypeList(util.getMenuList(model, Const.INPUT_QR_GENERATE_TYPE));

		form.setLearnList(learn.setDispValues(learn.findAll(), model));

		form.setSolutionList(solution.setDispValues(solution.findAll(), model));

		form.setProductList(product.setDispValues(product.findAll(), model));

		util.setInputInstructions(model);

	}

	private void setSelectedValues(QrForm form) {

		form.setSelectedImageType(form.getImg_type());

		form.setSelectedImageSize(form.getImg_size());

		form.setSelectedBgColor(form.getBg_color());

		form.setSelectedFinderColor(form.getPixel_color());

		form.setSelectedPixelColor(form.getPixel_color());

		form.setSelectedLogoNeeded(form.getLogo_needed());

	}

	@Override

	public String getDirectUrl(String uniq_number) throws Exception {

		String result = null;

		List<EntityQrManager> dynamicList = new ArrayList<EntityQrManager>();

		dynamicList = qrManagerService.findAll();

		for (EntityQrManager single : dynamicList) {

			if (single.getUniq_number().equals(uniq_number)) {

				result = single.getRedirect_url();

				break;

			}

		}

		return result;

	}

	@Override

	public void validationErrorHandling(Model model, QrForm form, BindingResult bindingResult, MultipartFile file)
			throws Exception {

		SessionBean sessionBean = (SessionBean) model.getAttribute(Const.OBJECT_NAME_SESSION);

		String defaultMsg = bindingResult.getFieldErrors().get(0).getDefaultMessage();

		String defaultMsgTxt = messageSource.getMessage(defaultMsg, sessionBean);

		model.addAttribute(Const.ERROR_MESSAGE, defaultMsgTxt);

		setSelectedValues(form);

		setFixedValues(form, model);

		if (!form.getOrgBase64Data().equals(Const.EMPTY_TEXT) && (bindingResult.hasFieldErrors("loginUserIdEmpty") ||

				bindingResult.hasFieldErrors("loginUserPassEmpty") ||

				bindingResult.hasFieldErrors("loginUserIdPassEmpty") ||

				bindingResult.hasFieldErrors("loginUserExisted"))) {

			setQrImage(form, file);

		}

		util.setFormToSession(model, form);

	}

}