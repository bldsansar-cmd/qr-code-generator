package com.qrazy.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

import com.qrazy.entity.EntityUser;
import com.qrazy.util.Const;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionBean {

	public String sessionId = Const.EMPTY_TEXT;
	public Locale locale = null;
	public FixedMsgBean fixedMsgBean = null;
	public String currentPage = Const.EMPTY_TEXT;
	public boolean authorizedUser = false;
	public EntityUser loginUser = new EntityUser();
	public List<String> funcNameList;
	public List<Object> funcFormList;
	public List<Object> funcBeanList;

	public SessionBean() throws Exception {

		locale = LocaleContextHolder.getLocale();

		init();

	}

	public void init() throws Exception {

		sessionId = Const.EMPTY_TEXT;
		fixedMsgBean = new FixedMsgBean();
		funcNameList = new ArrayList<String>();
		funcFormList = new ArrayList<Object>();
		funcBeanList = new ArrayList<Object>();

	}

	public void resetUser() {

		this.loginUser = new EntityUser();

	}

}
