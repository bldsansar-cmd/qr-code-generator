package com.qrazy.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.qrazy.bean.SessionBean;

import jakarta.servlet.http.HttpServletRequest;

public class MultiMessageSource extends ResourceBundleMessageSource {

	@Autowired
	HttpServletRequest request;

	public String getMessage(String key, Object... params) {

		Locale localeInSession = ((SessionBean) params[0]).getLocale();

		//		Browser言語設定による場合、下記実装使用 

		//		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver(); 

		//		localeResolver.setDefaultLocale(localeInSession); 

		//		localeResolver.setSupportedLocales(List.of(localeInSession)); 

		//		localeResolver.setSupportedLocales(List.of(Locale.ENGLISH, Locale.JAPANESE)); 

		//		return super.getMessage(key, params, localeResolver.resolveLocale(request)); 

		return super.getMessage(key, params, localeInSession);

	}

}
