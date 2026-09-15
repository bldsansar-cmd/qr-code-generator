package com.qrazy.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.qrazy.bean.QrManagerBean;
import com.qrazy.bean.SessionBean;
import com.qrazy.config.MultiMessageSource;
import com.qrazy.entity.EntityQrManager;
import com.qrazy.repository.RepositoryQrManager;
import com.qrazy.util.Const;
import com.qrazy.util.Properties;
import com.qrazy.util.Util;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class QrManagerServiceImpl implements QrManagerService {

	@Autowired
	private Util util;

	@Autowired
	private MultiMessageSource messageSource;

	@Autowired
	private RepositoryQrManager repository;

	@Autowired
	private Properties prop;

	public List<EntityQrManager> findAll() {

		return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));

	}

	@Override
	public List<QrManagerBean> setDispValues(List<EntityQrManager> findAll, Model model) throws ParseException {

		SessionBean sessionBean = (SessionBean) model.getAttribute(Const.OBJECT_NAME_SESSION);

		SimpleDateFormat sdFormatDB = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		SimpleDateFormat sdFormatDashboard = new SimpleDateFormat("yyyy/MM/dd");

		List<QrManagerBean> result = new ArrayList<QrManagerBean>();

		for (EntityQrManager entity : findAll) {

			QrManagerBean single = new QrManagerBean();

			single.setId(entity.getId());

			single.setQr_name(entity.getQr_name());

			single.setQr_type(messageSource.getMessage("MENU_" + entity.getQr_type().toUpperCase(), sessionBean));

			single.setGenerate_type(messageSource.getMessage(entity.getGenerate_type().toUpperCase(), sessionBean));

			single.setQr_image_file_name(entity.getQr_image_file_name());

			single.setShort_url(entity.getShort_url());

			single.setRedirect_url(entity.getRedirect_url());

			single.setAccess_count(entity.getAccess_count());

			single.setUniq_number(entity.getUniq_number());

			File file = new File(prop.getImage_folder_path_qr_code() + entity.getQr_image_file_name());

			single.setQr_image("data:image/jpeg;base64," + util.fileToBase64(file));

			String createDateTime = entity.getCreate_date_time() == null ? "-"
					: sdFormatDashboard.format(sdFormatDB.parse(entity.getCreate_date_time())).toString();

			String updateDateTime = entity.getUpdate_date_time() == null ? "-"
					: sdFormatDashboard.format(sdFormatDB.parse(entity.getUpdate_date_time())).toString();

			single.setCreate_date_time(createDateTime);

			single.setUpdate_date_time(updateDateTime);

			result.add(single);

		}

		return result;

	}

	public List<EntityQrManager> findById(int id) throws Exception {

		return repository.findById(id);

	}

}