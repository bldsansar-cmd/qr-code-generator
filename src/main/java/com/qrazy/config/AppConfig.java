package com.qrazy.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qrazy.service.QrService;
import com.qrazy.service.QrServiceImpl;

@Configuration
public class AppConfig {

	@Bean
	public MultiMessageSource messageSource() {

		MultiMessageSource messageSource = new MultiMessageSource();

		messageSource.setBasenames("messages/messages", "messages/validationMessages");

		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());

		return messageSource;

	}

	@Bean
	public QrService qrService() {

		return new QrServiceImpl();
	}

}
