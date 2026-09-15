package com.qrazy.form;

import java.util.ArrayList;
import java.util.List;

import com.qrazy.bean.LearnBean;
import com.qrazy.bean.MapBean;
import com.qrazy.bean.ProductBean;
import com.qrazy.bean.SolutionBean;
import com.qrazy.util.Const;
import com.qrazy.validator.order.ValidOrder5;
import com.qrazy.validator.order.ValidOrder6;

import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrForm extends ParentForm {

	public String input_value_qr_name = Const.EMPTY_TEXT;

	public String input_value_web = Const.EMPTY_TEXT;

	public String input_value_text = Const.EMPTY_TEXT;

	public String input_value_wifi_id = Const.EMPTY_TEXT;

	public String input_value_wifi_pass = Const.EMPTY_TEXT;

	public String input_value_bulk = Const.EMPTY_TEXT;

	public List<String> inputValueBulkArray = new ArrayList<String>();

	public String page_name = Const.PAGE_INDEX;

	public String input_value_label = Const.EMPTY_TEXT;

	public String input_value_logo_filename = Const.EMPTY_TEXT;

	public String upload_file = Const.EMPTY_TEXT;

	public String qr_type = Const.DEFAULT_QR_TYPE;

	public String qr_path = Const.EMPTY_TEXT;

	public String img_type = Const.DEFAULT_IMG_TYPE;

	public String img_size = Const.DEFAULT_IMG_SIZE;

	public String bg_color = Const.DEFAULT_BG_COLOR;

	public String finder_color = Const.DEFAULT_FINDER_COLOR;

	public String pixel_color = Const.DEFAULT_PIXEL_COLOR;

	public String file_name = Const.EMPTY_TEXT;

	public String logo_needed = Const.LOGO_NEEDED_NO;

	public String product = Const.EMPTY_TEXT;

	public String solution = Const.EMPTY_TEXT;

	public String smartFlg = Const.EMPTY_TEXT;

	public String qr_generate_type = Const.QR_GENERATE_TYPE_STATIC;

	public String logo_size = "0";

	public List<MapBean> imageTypeList = new ArrayList<MapBean>();

	public List<MapBean> imageSizeList = new ArrayList<MapBean>();

	public List<MapBean> bgColorList = new ArrayList<MapBean>();

	public List<MapBean> finderColorList = new ArrayList<MapBean>();

	public List<MapBean> pixelColorList = new ArrayList<MapBean>();

	public List<MapBean> logoNeededList = new ArrayList<MapBean>();

	public List<MapBean> qrGenerateTypeList = new ArrayList<MapBean>();

	public List<LearnBean> learnList = new ArrayList<LearnBean>();

	public List<SolutionBean> solutionList = new ArrayList<SolutionBean>();

	public List<ProductBean> productList = new ArrayList<ProductBean>();

	//	public List<String> dynamicStaticOptionQrTypeList = new ArrayList<String>(); 

	//	public List<String> dynamicOnlyQrTypeList = new ArrayList<String>(); 

	public List<MapBean> dynamicStaticOptionQrTypeList = new ArrayList<MapBean>();

	public List<MapBean> dynamicOnlyQrTypeList = new ArrayList<MapBean>();

	public String selectedImageType = Const.DEFAULT_IMG_TYPE;

	public String selectedImageSize = Const.DEFAULT_IMG_SIZE;

	public String selectedBgColor = Const.DEFAULT_BG_COLOR;

	public String selectedFinderColor = Const.DEFAULT_FINDER_COLOR;

	public String selectedPixelColor = Const.DEFAULT_PIXEL_COLOR;

	public String selectedLogoNeeded = Const.LOGO_NEEDED_NO;

	public String selectedQrGenerateType = Const.QR_GENERATE_TYPE_STATIC;

	public boolean initial = true;

	public byte[] imageData = null;

	public List<String> base64DataList = new ArrayList<String>();

	public String orgBase64Data = Const.EMPTY_TEXT;

	public String plug_img = Const.EMPTY_TEXT;

	public String instruction = Const.EMPTY_TEXT;

	public String instruction2 = Const.EMPTY_TEXT;

	public String qr_id = Const.EMPTY_TEXT;

	public String selectedCustomizeNav = Const.DEFAULT_CUSTOMIZE_NAV;

	public String zipBase64Data = Const.EMPTY_TEXT;

	public String customize_pixel = Const.PIXEL_RECTANGLE;

	public String customize_finder = Const.FINDER_RECTANGLE;

	public String customize_frame = Const.DEFAULT_FRAME;

	public String customize_template = Const.DEFAULT_TEMPLATE;

	@AssertTrue(message = "MSG_NOT_BLANK", groups = ValidOrder5.class)
	public boolean isQrNameEmpty() {

		boolean result = true;

		if (this.operation_type.equals(Const.DEFAULT_OPERATION_TYPE)) {

			if (this.input_value_qr_name.equals(Const.EMPTY_TEXT)) {

				result = false;

			}

		}

		return result;

	}

	@AssertTrue(message = "MSG_NOT_BLANK", groups = ValidOrder6.class)
	public boolean isNotInput() {

		boolean result = true;

		if (this.operation_type.equals(Const.DEFAULT_OPERATION_TYPE)) {

			switch (this.qr_type) {

			case "text":

				if (this.input_value_text.equals("")) {

					result = false;

				}

				break;

			case "wifi":

				if (this.input_value_wifi_id.equals("")) {

					result = false;

				} else if (this.input_value_wifi_pass.equals("")) {

					result = false;

				}

				break;

			case "web":

				if (this.input_value_web.equals("")) {

					result = false;

				}

				break;

			case "bulk":

				if (this.input_value_bulk.equals("")) {

					result = false;

				}

				break;

			default:

				break;

			}

		}

		return result;
	}
}