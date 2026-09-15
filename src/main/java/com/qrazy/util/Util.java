package com.qrazy.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.qrazy.bean.MapBean;
import com.qrazy.bean.SessionBean;
import com.qrazy.config.MultiMessageSource;
import com.qrazy.entity.EntityUser;
import com.qrazy.form.QrForm;

import jakarta.servlet.http.HttpServletResponse;

@Component

public class Util {

	@Autowired

	private Properties prop;

	@Autowired

	private MultiMessageSource messageSource;

	private SessionBean sessionBean;

	public List<MapBean> getSelectBoxList(String itemName, int defaultIdx, Model model) {

		sessionBean = (SessionBean) model.getAttribute(Const.OBJECT_NAME_SESSION);

		List<MapBean> resultList = new ArrayList<MapBean>();

		String messageProps = messageSource.getMessage(itemName, sessionBean);

		for (int i = 0; i < messageProps.split(Const.COMMA).length; i++) {

			MapBean single = new MapBean();

			single.setValue(messageProps.split(Const.COMMA)[i]);

			single.setText(messageProps.split(Const.COMMA)[i].toLowerCase().trim());

			if (i == defaultIdx) {

				resultList.add(0, single);

			} else {

				resultList.add(single);

			}

		}

		return resultList;

	}

	public List<MapBean> getMenuList(Model model, String key) {

		sessionBean = (SessionBean) model.getAttribute(Const.OBJECT_NAME_SESSION);

		List<MapBean> resultList = new ArrayList<MapBean>();

		String messageProps = messageSource.getMessage(key, sessionBean);

		for (int i = 0; i < messageProps.split(Const.COMMA).length; i++) {

			MapBean single = new MapBean();

			single.setValue(messageProps.split(Const.COMMA)[i].toLowerCase());

			switch (key) {

			case "INPUT_MENU":

				single.setText(messageSource.getMessage("MENU_" + messageProps.split(Const.COMMA)[i].toUpperCase(),
						sessionBean));

				break;

			case "USER_MENU":

				single.setText(messageSource.getMessage("USER_MENU_" + messageProps.split(Const.COMMA)[i].toUpperCase(),
						sessionBean));

				break;

			case "INPUT_NAV":

				single.setText(messageSource.getMessage("NAV_" + messageProps.split(Const.COMMA)[i].toUpperCase(),
						sessionBean));

				break;

			case "DYNAMIC_ONLY_QR_TYPES":

			case "DYNAMIC_STATIC_OPTION_QR_TYPES":

			case "INPUT_LOGO_NEEDED":

			case "INPUT_QR_GENERATE_TYPE":

				single.setText(messageProps.split(Const.COMMA)[i].trim());

				break;

			default:

				single.setText(messageSource.getMessage(key + "_" + messageProps.split(Const.COMMA)[i].toUpperCase(),
						sessionBean));

			}

			resultList.add(single);

		}

		return resultList;

	}

	public String getProperValue(QrForm form) {

		String result = Const.EMPTY_TEXT;

		String qrtype = form.getQr_type();

		switch (qrtype.toUpperCase()) {

		case Const.NAV_WEB:

			result = form.getInput_value_web();

			break;

		case Const.NAV_TEXT:

			result = form.getInput_value_text();

			break;

		case Const.NAV_WIFI:

			result = form.getInput_value_wifi_id() + Const.COLON + form.getInput_value_wifi_pass();

			break;

		}

		form.setInput_value_label(result);

		return result;

	}

	public boolean isCommonQrCode(QrForm form) {

		boolean result = false;

		String qrtype = form.getQr_type();

		switch (qrtype.toUpperCase()) {

		case Const.NAV_TEXT:

			result = true;

			break;

		case Const.NAV_WIFI:

		case Const.NAV_BULK:

			result = false;

			break;

		}

		return result;

	}

	public boolean isLocaleJA(Model model) {

		sessionBean = (SessionBean) model.getAttribute(Const.OBJECT_NAME_SESSION);

		if (sessionBean.getLocale() != null && sessionBean.getLocale() == Locale.JAPANESE) {

			return true;

		}

		return false;

	}

	public Field getField(Object obj, String name) throws Exception {

		Field field = obj.getClass().getField(name);

		return field;

	}

	public void setInputInstructions(Model model) {

		SessionBean sessionBean = (SessionBean) model.getAttribute(Const.OBJECT_NAME_SESSION);

		model.addAttribute(Const.INSTRUCTION_QR_NAME, messageSource.getMessage(Const.INSTRUCTION_QR_NAME, sessionBean));

		model.addAttribute(Const.INSTRUCTION_WEB, messageSource.getMessage(Const.INSTRUCTION_WEB, sessionBean));

		model.addAttribute(Const.INSTRUCTION_TEXT, messageSource.getMessage(Const.INSTRUCTION_TEXT, sessionBean));

		model.addAttribute(Const.INSTRUCTION_WIFI_NAME,
				messageSource.getMessage(Const.INSTRUCTION_WIFI_NAME, sessionBean));

		model.addAttribute(Const.INSTRUCTION_WIFI_PASSWORD,
				messageSource.getMessage(Const.INSTRUCTION_WIFI_PASSWORD, sessionBean));

		model.addAttribute(Const.INSTRUCTION_ID, messageSource.getMessage(Const.INSTRUCTION_ID, sessionBean));

		model.addAttribute(Const.INSTRUCTION_PASS, messageSource.getMessage(Const.INSTRUCTION_PASS, sessionBean));

		model.addAttribute(Const.INSTRUCTION_BULK, messageSource.getMessage(Const.INSTRUCTION_BULK, sessionBean));

		model.addAttribute(Const.INSTRUCTION_TEXT_LENGTH_LIMIT,
				messageSource.getMessage(Const.INSTRUCTION_TEXT_LENGTH_LIMIT, sessionBean));

		model.addAttribute(Const.INSTRUCTION_FILE, messageSource.getMessage(Const.INSTRUCTION_FILE, sessionBean));

		model.addAttribute(Const.DESCRIPTION_GENERATE_TYPE_DYNAMIC,
				messageSource.getMessage(Const.DESCRIPTION_GENERATE_TYPE_DYNAMIC, sessionBean));

		model.addAttribute(Const.DESCRIPTION_GENERATE_TYPE_DYNAMIC_ONLY,
				messageSource.getMessage(Const.DESCRIPTION_GENERATE_TYPE_DYNAMIC_ONLY, sessionBean));

		model.addAttribute(Const.DESCRIPTION_GENERATE_TYPE_STATIC,
				messageSource.getMessage(Const.DESCRIPTION_GENERATE_TYPE_STATIC, sessionBean));

		model.addAttribute(Const.DESCRIPTION_DEFAULT, messageSource.getMessage(Const.DESCRIPTION_DEFAULT, sessionBean));

		model.addAttribute(Const.DESCRIPTION_WEB, messageSource.getMessage(Const.DESCRIPTION_WEB, sessionBean));

		model.addAttribute(Const.DESCRIPTION_TEXT, messageSource.getMessage(Const.DESCRIPTION_TEXT, sessionBean));

		model.addAttribute(Const.DESCRIPTION_WIFI, messageSource.getMessage(Const.DESCRIPTION_WIFI, sessionBean));

		model.addAttribute(Const.DESCRIPTION_BULK, messageSource.getMessage(Const.DESCRIPTION_BULK, sessionBean));

		model.addAttribute(Const.INSTRUCTION_FILE, messageSource.getMessage(Const.INSTRUCTION_FILE, sessionBean));

		model.addAttribute(Const.LINK_DETAIL, messageSource.getMessage(Const.LINK_DETAIL, sessionBean));

		model.addAttribute(Const.ABOUT_STATIC_DYNAMIC,
				messageSource.getMessage(Const.ABOUT_STATIC_DYNAMIC, sessionBean));

		model.addAttribute(Const.DESCRIPTION_STATIC, messageSource.getMessage(Const.DESCRIPTION_STATIC, sessionBean));

		model.addAttribute(Const.DESCRIPTION_STATIC_DETAIL,
				messageSource.getMessage(Const.DESCRIPTION_STATIC_DETAIL, sessionBean));

		model.addAttribute(Const.DESCRIPTION_DYNAMIC, messageSource.getMessage(Const.DESCRIPTION_DYNAMIC, sessionBean));

		model.addAttribute(Const.DESCRIPTION_DYNAMIC_DETAIL,
				messageSource.getMessage(Const.DESCRIPTION_DYNAMIC, sessionBean));

		model.addAttribute(Const.DESCRIPTION_DYNAMIC_ONLY,
				messageSource.getMessage(Const.DESCRIPTION_DYNAMIC_ONLY, sessionBean));

		model.addAttribute(Const.DESCRIPTION_DYNAMIC_ONLY_DETAIL,
				messageSource.getMessage(Const.DESCRIPTION_DYNAMIC_ONLY_DETAIL, sessionBean));

	}

	public List<String> getRealDataLIst(String[] valueArray) {

		List<String> result = new ArrayList<String>();

		for (int i = 0; i < valueArray.length; i++) {

			if (!valueArray[i].isEmpty() && !valueArray[i].isBlank()) {

				result.add(valueArray[i]);

			}

		}

		return result;

	}

	public void doDownload(HttpServletResponse response, QrForm form) throws IOException {

		byte[] outputStreamByteArray;

		try (OutputStream os = response.getOutputStream();) {

			List<String> orgBase64List = getRealDataLIst(form.getOrgBase64Data().split(Const.BASE64_INDICATOR));

			String file_name = System.currentTimeMillis() + Const.DOT + form.getImg_type();

			// Base64データからファイル作成 

			outputStreamByteArray = Base64.getDecoder().decode(orgBase64List.get(0));

			response.setHeader("Content-Disposition", "attachment; filename=" + file_name);

			response.setContentType(form.getImg_type().equals(Const.DEFAULT_IMG_TYPE) ? "image/png" : "image/svg+xml");

			os.write(outputStreamByteArray);

			os.flush();

		}

	}

	public void doDownloadZip(HttpServletResponse response, QrForm form) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		String zipFileName = System.currentTimeMillis() + Const.DOT + "zip";

		BufferedOutputStream bos = new BufferedOutputStream(baos);

		ZipOutputStream zip = new ZipOutputStream(bos);

		String fileNamePart = System.currentTimeMillis() + "";

		int idx = 1;

		List<String> orgBase64List = getRealDataLIst(form.getOrgBase64Data().split(Const.BASE64_INDICATOR));

		for (String orgBase : orgBase64List) {

			byte[] qrByteInfo = Base64.getDecoder().decode(orgBase);

			ZipEntry entry = new ZipEntry(fileNamePart + "_" + idx + Const.DOT + form.getImg_type());

			entry.setSize(qrByteInfo.length);

			zip.putNextEntry(entry);

			zip.write(qrByteInfo);

			zip.closeEntry();

			idx++;

		}

		zip.close();

		try (OutputStream os = response.getOutputStream();) {

			response.setContentType("application/zip");

			response.setHeader("Content-Disposition", "attachment; filename=" + zipFileName);

			response.setContentLength(baos.toByteArray().length);

			os.write(baos.toByteArray());

			os.flush();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public void setCurrentPageToSession(Model model, String pageName) {

		((SessionBean) model.getAttribute(Const.OBJECT_NAME_SESSION)).setCurrentPage(pageName);

	}

	public void setUserToSession(Model model, EntityUser entityUser) {

		entityUser.setProfile_img(entityUser.getProfile_img());

		((SessionBean) model.getAttribute(Const.OBJECT_NAME_SESSION)).setLoginUser(entityUser);

	}

	public void setFormToSession(Model model, Object form) {

		SessionBean sessionBean = ((SessionBean) model.getAttribute(Const.OBJECT_NAME_SESSION));

		if (sessionBean.getFuncFormList().size() >= 1) {

			sessionBean.getFuncFormList().set(sessionBean.getFuncFormList().size() - 1, form);

		} else {

			sessionBean.getFuncFormList().set(sessionBean.getFuncFormList().size(), form);

		}

		String currentPange = getObjectValue(form, Const.OBJECT_NAME_PAGE_NAME).toString();

		sessionBean.setCurrentPage(currentPange);

		model.addAttribute(Const.OBJECT_FORM, form);

	}

	public void addFormToSession(Model model, Object form) {

		SessionBean sessionBean = ((SessionBean) model.getAttribute(Const.OBJECT_NAME_SESSION));

		String currentPange = getObjectValue(form, Const.OBJECT_NAME_PAGE_NAME).toString();

		sessionBean.setCurrentPage(currentPange);

		sessionBean.getFuncNameList().add(currentPange);

		sessionBean.getFuncFormList().add(form);

		model.addAttribute(Const.OBJECT_FORM, form);

	}

	public void deleteFormFromSession(Model model) {

		SessionBean sessionBean = ((SessionBean) model.getAttribute(Const.OBJECT_NAME_SESSION));

		if (sessionBean.getFuncNameList().size() > 1) {

			sessionBean.getFuncNameList().remove(sessionBean.getFuncNameList().size() - 1);

			sessionBean.getFuncFormList().remove(sessionBean.getFuncFormList().size() - 1);

			String currentPange =

					getObjectValue(sessionBean.getFuncFormList().get(

							sessionBean.getFuncFormList().size() - 1),

							Const.OBJECT_NAME_PAGE_NAME).toString();

			sessionBean.setCurrentPage(currentPange);

		}

	}

	public Object getObjectValue(Object obj, String parameterName) {

		Object result = null;

		Field field;

		try {

			field = obj.getClass().getField(parameterName);

			field.setAccessible(true);

			result = field.get(obj);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return result;

	}

	public String fileToBase64(File file) {

		String base64 = Const.EMPTY_TEXT;

		try {

			base64 = encodeFileToBase64(file);

			System.out.println("Base64 Encoded String: " + base64);

		} catch (IOException e) {

			e.printStackTrace();

		}

		return base64;

	}

	public String encodeFileToBase64(File file) throws IOException {

		try (FileInputStream fileInputStream = new FileInputStream(file)) {

			byte[] fileBytes = new byte[(int) file.length()];

			fileInputStream.read(fileBytes);

			return Base64.getEncoder().encodeToString(fileBytes);

		}

	}

	public Object getLastFormFromSession(SessionBean sessionBean) {

		return sessionBean.getFuncFormList().get(sessionBean.getFuncFormList().size() - 1);

	}

	public List<MapBean> getImageMapList(Model model, String key) {

		List<MapBean> resultList = new ArrayList<MapBean>();

		File folder = new File(prop.getImage_folder_path_appication_customize() + key);

		for (File file : folder.listFiles()) {

			if (!file.isDirectory()) {

				MapBean single = new MapBean();

				single.setValue(file.getName().split("\\.")[0]);

				single.setText("images/common/customize/" + key + "/" + file.getName());

				resultList.add(single);

			}

		}

		return resultList;

	}

	public void doDownloadSvg(String svg, HttpServletResponse response, QrForm form) throws IOException {

		try {

			response.setContentType("image/svg+xml");

			response.setHeader("Content-Disposition", "attachment; filename=\"qrcode.svg\"");

			response.setCharacterEncoding("UTF-8");

			response.getWriter().write(svg);

			response.getWriter().flush(); // ここで即座に書き出し 

		} catch (Exception e) {

			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "QRコード生成に失敗しました");

		}

	}

}