package com.qrazy.form;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;

import com.qrazy.bean.QrManagerBean;
import com.qrazy.entity.EntityUser;
import com.qrazy.util.Const;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm extends ParentForm {

	public String page_name = Const.PAGE_USER;
	public String user_name = Const.EMPTY_TEXT;
	public String id = Const.EMPTY_TEXT;
	public String user_email = Const.EMPTY_TEXT;
	public String user_password = Const.EMPTY_TEXT;
	public String profile_img = Const.EMPTY_TEXT;
	public List<QrManagerBean> qrInfoList = new ArrayList<QrManagerBean>();

	public void setUserInfo(List<EntityUser> entityUserList, Model model) {

		this.id = "" + entityUserList.get(0).getId();
		this.user_name = entityUserList.get(0).getUser_name();
		this.user_email = entityUserList.get(0).getUser_email();
		this.user_password = entityUserList.get(0).getUser_password();
		this.profile_img = "data:image/png;base64," + entityUserList.get(0).getProfile_img();
	}
}