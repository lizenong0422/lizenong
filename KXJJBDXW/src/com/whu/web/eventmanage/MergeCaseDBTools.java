package com.whu.web.eventmanage;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.whu.tools.crypto.AESCrypto;
import com.whu.web.event.EventBean;

public class MergeCaseDBTools {

	private static Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private static Statement stmt = null;

	public MergeCaseDBTools() {
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
	public ArrayList mergeCaseInfo(String value) throws SQLException{

		String sql = "SELECT * FROM TB_REPORTINFO WHERE MERGEID='" +value+"'";
		ArrayList list = new ArrayList();
		try{
			AESCrypto aes = new AESCrypto();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next())
			{
				EventBean eb = new EventBean();
				eb.setId(String.valueOf(rs.getInt("ID")));
				eb.setReportID(rs.getString("REPORTID"));
				eb.setReportName(new String(aes.createDecryptor(rs.getBytes("REPORTNAME"), "TB_REPORTINFO")));
				eb.setBeReportName(new String(aes.createDecryptor(rs.getBytes("BEREPORTNAME"), "TB_REPORTINFO")));
				eb.setReportReason(new String(aes.createDecryptor(rs.getBytes("REPORTREASON"), "TB_REPORTINFO")));
				eb.setMergeID(rs.getString("MERGEID"));
				
				list.add(eb);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		
		return list;

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
