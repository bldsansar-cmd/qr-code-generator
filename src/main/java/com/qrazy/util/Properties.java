package com.qrazy.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@Configuration

@NoArgsConstructor

public class Properties {

	@Value("${image_folder_path_qr_code}")

	private String image_folder_path_qr_code;

	@Value("${image_folder_path_appication_customize}")

	private String image_folder_path_appication_customize;

	@Value("${image_folder_path_appication_customize_logo}")

	private String image_folder_path_appication_customize_logo;

	@Value("${image_folder_path_appication_common}")

	private String image_folder_path_appication_common;

}
