/**
 * 
 */
/**
 * @author root
 *
 */
package com.whu.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Validate{
	
	static Connection dbConn;
	static Statement statement;
	static ResultSet rst;
	static int Count;
	public static final String url = "jdbc:mysql://localhost:3306/DB_REPORT";
	public static final String driver = "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String psw = "3951345";
	//public static final String v = "2018010";
	
	
	public boolean validate(String value){
		
		try{
			Class.forName(driver);
			dbConn = DriverManager.getConnection(url, user, psw);
			if(!dbConn.isClosed()) System.out.println("Succeeded connecting to the database!");
			statement = dbConn.createStatement();
			String sql = "select count(*) as count from TB_REPORTINFO where SERIALNUM = '" + value + "'";
			rst = statement.executeQuery(sql);
			String id = null;
			while(rst.next()){
				Count = rst.getInt("count");
			}
			rst.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		if (Count > 0) 
			{
				return false;
			}
		else return true;
	}
/*	
	public static void main(String[] args){
		Validate val  = new Validate();
		
		boolean flag = val.validate(v);
		if (flag == true) System.out.println("ok");
	}
*/
}