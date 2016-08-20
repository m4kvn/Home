package com.nepian.home.utils;

import java.sql.SQLException;

import com.nepian.core.utils.sqlite.SQLite;

public class SQLiteUtil {

	/**
	 * executeUpdateを実行する
	 * @param sqlite SQLiteインスタンス
	 * @param token 実行する完全なトークン
	 */
	public static void executeUpdate(final SQLite sqlite, final String token) {
		try {
			sqlite.getPreparedStatement(token).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
