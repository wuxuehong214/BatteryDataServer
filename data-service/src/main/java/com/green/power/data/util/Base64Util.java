package com.green.power.data.util;

import java.io.UnsupportedEncodingException;

import sun.misc.*;

public class Base64Util {

	public static void main(String args[]) {
		byte[] data = new byte[] { 0x01, 0x02, (byte) 0xff, 0x00, (byte) 0xff,
				0x01 };
		String value = getBase64(data);
		System.out.println(getBase64(data));
		byte[] d = getFromBase64(value);
		System.out.println(HexByteStringUtil.byte2HexStr(d, d.length));
	}

	// 加密
	public static String getBase64(byte[] data) {
		String s = null;
		s = new BASE64Encoder().encode(data);
		return s;
	}

	// 解密
	public static byte[] getFromBase64(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return b;
	}

}
