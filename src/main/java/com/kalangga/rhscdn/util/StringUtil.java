package com.kalangga.rhscdn.util;


import java.util.regex.Pattern;

public class StringUtil {
	
	public static boolean checkEmailPatern(String email) {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(email).matches();
	}
	
	public static String getExtension(String fileName) {
		String[] strings = fileName.split(Pattern.quote("."));
		if (strings.length >= 2) {
			return strings[strings.length - 1];
		}
		return "";
	}

	public static String cdnDomain(String cdnProtocol, String cdnDomain, String bucketName) {
		String result = cdnProtocol + "://";
		result += bucketName + ".";
		result += cdnDomain + "/";
		return result;
	}
}
