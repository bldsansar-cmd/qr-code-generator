package com.qrazy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@ComponentScan(basePackageClasses = QrController.class)
public class QrazyApplication extends SpringBootServletInitializer {

	//	@Override
	//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	//		return application.sources(QrazyApplication.class);
	//
	//	}

	public static void main(String[] args) {
		SpringApplication.run(QrazyApplication.class, args);
	}
}