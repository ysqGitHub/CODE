package com.yanshiqian.gps;

/**
 * 此类用于配置数据库项目 这个类中的值应该是静态的，并且将被其他类访问 当环境改变时，你应该更改这些值。
 */
public class Configure {
	// user name
	public final static String USERNAME = "root";
	// password
	public final static String PASSWORD = "123456";
	// your database name
	public final static String DBNAME = "test";
	// mysql driver
	public final static String DRIVER = "com.mysql.jdbc.Driver";
	// mysql url
	public final static String URL = "jdbc:mysql://localhost:3306/" + DBNAME;
	// must bigger than the number of the keyword in your database table
	public final static int TABLELEN = 10;
}