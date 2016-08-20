package com.nepian.home.utils;

public class StringUtil {
	
	/**
	 * 文字列を連結する
	 * @param strings 連結する文字列を格納した配列
	 * @return 全ての文字列を連結させた文字列
	 */
	public static String link(String...strings) {
		StringBuilder sb = new StringBuilder("");
		for (String str : strings) {
			sb.append(str);
		}
		return sb.toString();
	}
}
