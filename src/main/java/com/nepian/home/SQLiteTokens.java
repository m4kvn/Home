package com.nepian.home;

public class SQLiteTokens {
	protected static String CREATE = "create table if not exists homes( uuid, location )";
	protected static String ADD    = "insert into homes values (?,?)";
	protected static String UPDATE = "update homes set location=? where uuid=?";
	protected static String REMOVE = "delete from homes where uuid=?";
	
	protected static String SELECT_ALL      = "select * from homes";
	protected static String SELECT_UUID     = "select uuid from homes where uuid=?";
	protected static String SELECT_LOCATION = "select location from homes where uuid=?";
}
