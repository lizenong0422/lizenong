package com.whu.web.wsjb;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DefSerialnumDBTools {
	private static Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private static Statement stmt = null;

	public DefSerialnumDBTools() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/DB_REPORT",
					"root", "3951345");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * change serialnum
	 * 
	 */
	public boolean changeSerialnum(String value1, String value2) throws SQLException{

		String sql = "UPDATE TB_REPORTINFO SET SERIALNUM='" +value1+"'"+" WHERE REPORTID='" +value2+"'";
		try{
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		int count = pst.executeUpdate();
		closeConnection();
		if(count > 0) return true;
		else return false;

	}
	

	
	public void closeConnection()
	{
		try {
			if (stmt != null)
				stmt.close();
			if (pst != null)
				pst.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
