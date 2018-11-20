package com.td.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * ���ݿ����������
 * 
 * @author lamp
 * 
 */
public class DBUtils {

	// ���ݿ����ӵ�ַ
	public static String URL;
	// �û���
	public static String USERNAME;
	// ����
	public static String PASSWORD;
	// mysql��������
	public static String DRIVER;

	private static ResourceBundle rb = ResourceBundle.getBundle("cn.com.gfaoki.td.util.db-config");

	private DBUtils() {
	}

	// ʹ�þ�̬�������������
	static {
		URL = rb.getString("jdbc.url");
		USERNAME = rb.getString("jdbc.username");
		PASSWORD = rb.getString("jdbc.password");
		DRIVER = rb.getString("jdbc.driver");
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// ����һ����ȡ���ݿ����ӵķ���
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("��ȡ����ʧ��");
		}
		return conn;
	}

	// �ر����ݿ�����
	public static void close(ResultSet rs, Statement stat, Connection conn) {
		try {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}