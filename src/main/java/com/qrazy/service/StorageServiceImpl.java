package com.qrazy.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qrazy.util.Properties;

@Service
public class StorageServiceImpl implements StorageService {

	@Autowired
	private Properties prop;

	@Override
	public void store(MultipartFile file) throws Exception {

		try {

			if (!file.isEmpty()) {

				Path destinationFile = FileSystems.getDefault()

						.getPath(prop.getImage_folder_path_appication_customize_logo()
								+ Paths.get(file.getOriginalFilename()))

						.normalize().toAbsolutePath();

				try (InputStream inputStream = file.getInputStream()) {

					Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);

				}

			}

		} catch (IOException e) {

			throw new Exception();

		}

	}

}