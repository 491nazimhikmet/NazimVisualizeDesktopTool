package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import model.WorkLine;

public class DBConnection {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static String DB_URL = "";
	
	
	// Database credentials
	static final String USER = "root";
	static final String PASS = "";
	private Connection conn;
	private Statement stmt;
	
	public DBConnection(){
		File fl = new File("connection.txt");
		Scanner scn;
		try {
			scn = new Scanner(fl);
			DB_URL = scn.next();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		startConnection();
	}	
	public void startConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void closeConnection(){
		if (conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}	
	public Connection getConn() {
		return conn;
	}
	public Statement getStmt() {
		return stmt;
	}
}
	
	
