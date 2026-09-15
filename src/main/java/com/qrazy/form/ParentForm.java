package com.qrazy.form;

import java.util.ArrayList;
import java.util.List;

import com.qrazy.bean.MapBean;
import com.qrazy.util.Const;
import com.qrazy.validator.order.ValidOrder1;
import com.qrazy.validator.order.ValidOrder2;
import com.qrazy.validator.order.ValidOrder3;
import com.qrazy.validator.order.ValidOrder4;

import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentForm {

	public List<MapBean> navList = new ArrayList<MapBean>();
	public List<MapBean> menuList = new ArrayList<MapBean>();
	public List<MapBean> menuListPc = new ArrayList<MapBean>();
	public List<MapBean> langList = new ArrayList<MapBean>();
	public List<MapBean> userMenuList = new ArrayList<MapBean>();
	public List<MapBean> pixelList = new ArrayList<MapBean>();
	public List<MapBean> finderList = new ArrayList<MapBean>();
	public List<MapBean> frameList = new ArrayList<MapBean>();
	public List<MapBean> templateList = new ArrayList<MapBean>();
	public String input_value_login_id = Const.EMPTY_TEXT;
	public String input_value_login_pass = Const.EMPTY_TEXT;
	public String operation_type = Const.DEFAULT_OPERATION_TYPE;
	public String locale = Const.EMPTY_TEXT;

	@AssertTrue(message = "MSG_NOT_BLANK", groups = ValidOrder1.class)
	public boolean isLoginUserIdEmpty() {

		if (!this.operation_type.equals(Const.DEFAULT_OPERATION_TYPE) &&

				this.input_value_login_id.equals(Const.EMPTY_TEXT)
				&& !this.input_value_login_pass.equals(Const.EMPTY_TEXT)) {

			return false;

		}

		return true;

	}

	@AssertTrue(message = "MSG_NOT_BLANK", groups = ValidOrder2.class)
	public boolean isLoginUserPassEmpty() {

		if (!this.operation_type.equals(Const.DEFAULT_OPERATION_TYPE) &&

				!this.input_value_login_id.equals(Const.EMPTY_TEXT)
				&& this.input_value_login_pass.equals(Const.EMPTY_TEXT)) {

			return false;

		}

		return true;

	}

	@AssertTrue(message = "MSG_NOT_BLANK", groups = ValidOrder3.class)
	public boolean isLoginUserIdPassEmpty() {

		if (!this.operation_type.equals(Const.DEFAULT_OPERATION_TYPE) &&

				this.input_value_login_id.equals(Const.EMPTY_TEXT)
				&& this.input_value_login_pass.equals(Const.EMPTY_TEXT)) {

			return false;

		}

		return true;

	}

	@AssertTrue(message = "MSG_NOT_BLANK", groups = ValidOrder4.class)
	public boolean isLoginUserExisted() {

		if (!this.operation_type.equals(Const.DEFAULT_OPERATION_TYPE) &&

				this.input_value_login_id.equals(Const.EMPTY_TEXT)
				&& this.input_value_login_pass.equals(Const.EMPTY_TEXT)) {

			return false;

		}

		return true;

	}

}