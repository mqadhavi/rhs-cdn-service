package com.kalangga.rhscdn.util;

public class ImageUtil {
	
	public static String getMimeType(String img) {
		if (img.charAt(0) == '/') {
			if (img.charAt(1) == '9') 
				return "jpg";
		} else if (img.charAt(0) == 'R') {
			return "bmp";
		} else if (img.charAt(0) == 'i' || img.charAt(0) == 'A') {
			return "png";
		}
		return null;
	}
}
