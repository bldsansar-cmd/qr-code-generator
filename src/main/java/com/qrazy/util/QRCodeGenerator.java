package com.qrazy.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import com.qrazy.bean.Star;
import com.qrazy.bean.UtilBean;
import com.qrazy.form.QrForm;

@Component

public class QRCodeGenerator {

	public byte[] generateQRCode(QrForm form, String value, MultipartFile file) throws Exception {

		//	public BufferedImage generateQRCode(QrForm form, String value, MultipartFile file) throws Exception { 

		Map<EncodeHintType, Object> hintMap = new EnumMap<>(EncodeHintType.class);

		hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		UtilBean utilBean = getActualQrValue(form, value.trim());

		final Map<EncodeHintType, Object> encodingHints = new HashMap<>();

		encodingHints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		QRCode code = Encoder.encode(utilBean.getStrValue1(), ErrorCorrectionLevel.H, encodingHints);

		// カラ設定 

		BufferedImage qrImage = renderQRImage(code, Integer.parseInt(form.getImg_size()),
				Integer.parseInt(form.getImg_size()), 1, form);

		// ロゴ設定 

		if (form.getLogo_needed().equals(Const.LOGO_NEEDED_YES)) {

			setQrCodeLogo(qrImage, form, file);

		}

		// QR画像バイト配列情報作成 

		return createQrCodeByteData(qrImage, form);

		//		return qrImage; 

	}

	//	public static byte[] generateSvg(QrForm form, String value, MultipartFile file) { 

	//		 

	//		return QRCode.from(value) 

	//				.withSize(form.img_size, form.img_size) 

	//		        .to(ImageType.SVG) 

	//		        .stream() 

	//		        .toByteArray(); 

	//	} 

	private UtilBean getActualQrValue(QrForm form, String value) {

		UtilBean utilBean = new UtilBean();

		utilBean.setStrValue1(value);

		if (Const.QR_GENERATE_TYPE_DYNAMIC.equals(form.getQr_generate_type())) {

			utilBean.setLongValue1(System.currentTimeMillis());

			utilBean.setStrValue1("http://localhost:8080/urqr/dynamic/" + utilBean.getLongValue1());

		}

		return utilBean;

	}

	private byte[] createQrCodeByteData(BufferedImage qrImage, QrForm form) throws IOException {

		ByteArrayOutputStream baos = null;

		baos = new ByteArrayOutputStream();

		ImageIO.write(qrImage, form.getImg_type(), baos);

		return baos.toByteArray();

	}

	public byte[] getQRCodeImage(String filepath, int width, int height, String imageType)

			throws WriterException, IOException {

		QRCodeWriter qrCodeWriter = new QRCodeWriter();

		BitMatrix bitMatrix = qrCodeWriter.encode(filepath, BarcodeFormat.QR_CODE, width, height);

		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

		MatrixToImageConfig con = new MatrixToImageConfig(0xFF000002, 0xFFFFC041);

		MatrixToImageWriter.writeToStream(bitMatrix, imageType, pngOutputStream, con);

		return pngOutputStream.toByteArray();

	}

	private void setQrCodeLogo(BufferedImage qrImage, QrForm form, MultipartFile file) throws IOException {

		int logoRatio = form.getLogo_size().equals("0") ? 10 : 5;

		BufferedImage logoImage = ImageIO.read(file.getInputStream());

		Image scaledLogo = logoImage.getScaledInstance(Integer.parseInt(form.getImg_size()) / logoRatio,
				Integer.parseInt(form.getImg_size()) / logoRatio, Image.SCALE_SMOOTH);

		int x = Integer.parseInt(form.getImg_size()) / 2 - (Integer.parseInt(form.getImg_size()) / logoRatio / 2);

		int y = x;

		// ロゴをQRコードに追加 

		Graphics2D g = (Graphics2D) qrImage.getGraphics();

		g.drawImage(scaledLogo, x, y, null);

	}

	private static BufferedImage renderQRImage(QRCode code, int width, int height, int quietZone, QrForm form) {

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D graphics = image.createGraphics();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		graphics.setBackground(HexToColor(form.getBg_color().toUpperCase()));

		graphics.clearRect(0, 0, width, height);

		graphics.setColor(HexToColor(form.getPixel_color().toUpperCase()));

		Star star = new Star();

		ByteMatrix input = code.getMatrix();

		if (input == null) {

			throw new IllegalStateException();

		}

		int inputWidth = input.getWidth();

		int inputHeight = input.getHeight();

		int qrWidth = inputWidth + (quietZone * 2);

		int qrHeight = inputHeight + (quietZone * 2);

		int outputWidth = Math.max(width, qrWidth);

		int outputHeight = Math.max(height, qrHeight);

		int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);

		int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;

		int topPadding = (outputHeight - (inputHeight * multiple)) / 2;

		int qrSingleUnitSize = (int) (multiple * Const.QR_SCALE_DOWN_FACTOR);

		for (int inputY = 0, outputY = topPadding; inputY < inputHeight; inputY++, outputY += multiple) {

			for (int inputX = 0, outputX = leftPadding; inputX < inputWidth; inputX++, outputX += multiple) {

				if (input.get(inputX, inputY) == 1) {

					if (!(inputX <= Const.FINDER_SIZE && inputY <= Const.FINDER_SIZE ||

							inputX >= inputWidth - Const.FINDER_SIZE && inputY <= Const.FINDER_SIZE ||

							inputX <= Const.FINDER_SIZE && inputY >= inputHeight - Const.FINDER_SIZE)) {

						if (form.customize_pixel.equals(Const.PIXEL_RECTANGLE)) {

							graphics.fillRect(outputX, outputY, qrSingleUnitSize, qrSingleUnitSize);

						} else if (form.customize_pixel.equals(Const.PIXEL_CIRCLE)) {

							graphics.fillOval(outputX, outputY, qrSingleUnitSize, qrSingleUnitSize);

						} else if (form.customize_pixel.equals(Const.PIXEL_STAR)) {

							graphics.draw(star.atLocation(inputX, inputY));

						} else if (form.customize_pixel.equals(Const.PIXEL_RECTANGLE_ROTATE)) {

							Rectangle rect2 = new Rectangle(outputX, outputY, qrSingleUnitSize, qrSingleUnitSize);

							graphics.rotate(Math.toRadians(45));

							graphics.draw(rect2);

							graphics.fill(rect2);

						}

					}

				}

			}

		}

		int finderSize = multiple * Const.FINDER_SIZE;

		drawFinderStyle(graphics, leftPadding, topPadding, finderSize, form);

		drawFinderStyle(graphics, leftPadding + (inputWidth - Const.FINDER_SIZE) * multiple, topPadding, finderSize,
				form);

		drawFinderStyle(graphics, leftPadding, topPadding + (inputHeight - Const.FINDER_SIZE) * multiple, finderSize,
				form);

		return image;

	}

	private static void drawFinderStyle(Graphics2D graphics, int x, int y, int finderSize, QrForm form) {

		final int FINDER_WHITE_PART_SIZE = finderSize * 5 / 7;

		final int FINDER_WHITE_PART_OFFSET = finderSize / 7;

		final int MIDDLE_DOT_DIAMETER = finderSize * 3 / 7;

		final int MIDDLE_DOT_OFFSET = finderSize * 2 / 7;

		graphics.setColor(HexToColor(form.getFinder_color().toUpperCase()));

		if (form.customize_finder.equals("1")) {

			graphics.fillRect(x, y, finderSize, finderSize);

		} else if (form.customize_finder.equals("2")) {

			graphics.fillRect(x, y, finderSize, finderSize);

		} else if (form.customize_finder.equals("3")) {

			graphics.fillOval(x, y, finderSize, finderSize);

		} else if (form.customize_finder.equals("4")) {

			graphics.fillOval(x, y, finderSize, finderSize);

		} else {

			graphics.fillRect(x, y, finderSize, finderSize);

		}

		graphics.setColor(HexToColor(form.getBg_color().toUpperCase()));

		if (form.customize_finder.equals("1")) {

			graphics.fillRect(x + FINDER_WHITE_PART_OFFSET, y + FINDER_WHITE_PART_OFFSET, FINDER_WHITE_PART_SIZE,
					FINDER_WHITE_PART_SIZE);

		} else if (form.customize_finder.equals("2")) {

			graphics.fillRect(x + FINDER_WHITE_PART_OFFSET, y + FINDER_WHITE_PART_OFFSET, FINDER_WHITE_PART_SIZE,
					FINDER_WHITE_PART_SIZE);

		} else if (form.customize_finder.equals("3")) {

			graphics.fillOval(x + FINDER_WHITE_PART_OFFSET, y + FINDER_WHITE_PART_OFFSET, FINDER_WHITE_PART_SIZE,
					FINDER_WHITE_PART_SIZE);

		} else if (form.customize_finder.equals("4")) {

			graphics.fillOval(x + FINDER_WHITE_PART_OFFSET, y + FINDER_WHITE_PART_OFFSET, FINDER_WHITE_PART_SIZE,
					FINDER_WHITE_PART_SIZE);

		} else {

			graphics.fillRect(x + FINDER_WHITE_PART_OFFSET, y + FINDER_WHITE_PART_OFFSET, FINDER_WHITE_PART_SIZE,
					FINDER_WHITE_PART_SIZE);

		}

		graphics.setColor(HexToColor(form.getFinder_color().toUpperCase()));

		if (form.customize_finder.equals("1")) {

			graphics.fillRect(x + MIDDLE_DOT_OFFSET, y + MIDDLE_DOT_OFFSET, MIDDLE_DOT_DIAMETER, MIDDLE_DOT_DIAMETER);

		} else if (form.customize_finder.equals("2")) {

			graphics.fillOval(x + MIDDLE_DOT_OFFSET, y + MIDDLE_DOT_OFFSET, MIDDLE_DOT_DIAMETER, MIDDLE_DOT_DIAMETER);

		} else if (form.customize_finder.equals("3")) {

			graphics.fillRect(x + MIDDLE_DOT_OFFSET, y + MIDDLE_DOT_OFFSET, MIDDLE_DOT_DIAMETER, MIDDLE_DOT_DIAMETER);

		} else if (form.customize_finder.equals("4")) {

			graphics.fillOval(x + MIDDLE_DOT_OFFSET, y + MIDDLE_DOT_OFFSET, MIDDLE_DOT_DIAMETER, MIDDLE_DOT_DIAMETER);

		} else {

			graphics.fillRect(x + MIDDLE_DOT_OFFSET, y + MIDDLE_DOT_OFFSET, MIDDLE_DOT_DIAMETER, MIDDLE_DOT_DIAMETER);

		}

	}

	public static Color HexToColor(String hex) {

		hex = hex.replace("#", "");

		switch (hex.length()) {

		case 6:

			return new Color(

					Integer.valueOf(hex.substring(0, 2), 16),

					Integer.valueOf(hex.substring(2, 4), 16),

					Integer.valueOf(hex.substring(4, 6), 16));

		case 8:

			return new Color(

					Integer.valueOf(hex.substring(0, 2), 16),

					Integer.valueOf(hex.substring(2, 4), 16),

					Integer.valueOf(hex.substring(4, 6), 16),

					Integer.valueOf(hex.substring(6, 8), 16));

		}

		return null;

	}

}