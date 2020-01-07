package com.whu.web.wsjb;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MergeDBTools {
	private static Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private static Statement stmt = null;

	public MergeDBTools() {
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
	 * 查询举报案件信息表
	 * 
	 */
	public String queryTB(String value){
		String returnValue = "/00000";
		String sql = "select REPORTID from TB_REPORTINFO where SERIALNUM='"+value+"'" + " and STATUS <> '53'";
		try{
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				returnValue = rs.getString("REPORTID"); 
			}
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally
		{
			closeConnection();
		}
		return returnValue;
	}
	
	/*
	
	
	public void updateTB(String column, String value, String reportID){
		String sql = "update TB_REPORTINFO set " +column+ "='" +value+ "'" + " where REPORTID='" +reportID+"'";
		try{
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally
		{
			closeConnection();
		}
	}
	
	
	
	public void updateMergeID(int value, String reportID){
		String sql = "update TB_REPORTINFO set MERGEID="+value+" where REPORTID='" +reportID+"'";
		try{
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally
		{
			closeConnection();
		}
	}
	
	
	public int queryID(String reportID){
		int id = 0;
		String sql = "select ID from TB_REPORTINFO where REPORTID='" +reportID+"'";
		try{
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				id = rs.getInt("ID");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return id;
	}
	

	*/
	
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
	

	
	
	/*
	 * 判断是否支持sql批处理
	 * 
	 */
	public static boolean supportBatch(Connection con) {
        try {
            DatabaseMetaData md = con.getMetaData();
            return md.supportsBatchUpdates();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

	
    public static int[] goBatch(Connection conn, String[] sqls) throws Exception {
        if (sqls == null) {
            return null;
        }
        try {
            stmt = conn.createStatement();
            for (int i = 0; i < sqls.length; i++) {
                stmt.addBatch(sqls[i]);// 将所有的SQL语句添加到Statement中
            }
            return stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
        }

        return null;

    }

    public void updateDB(String reportID, String reportID1, String serialnum) throws Exception {
        String[] sqls = new String[3];
        sqls[0] = "UPDATE TB_REPORTINFO SET STATUS='53' where REPORTID='" +reportID+ "'";
        sqls[1] = "UPDATE TB_REPORTINFO SET MERGEID='" +reportID1+"'"+" where REPORTID='" +reportID+"'";
        sqls[2] = "UPDATE TB_REPORTINFO SET SERIALNUM='" +serialnum+"'"+" where REPORTID='" +reportID+"'";
        try {
            boolean supportBatch = supportBatch(conn); // 判断是否支持批处理
            if (supportBatch) {
                int[] results = goBatch(conn, sqls);// 执行一批SQL语句
            }
        } catch (ClassNotFoundException e1) {
            throw e1;
        } catch (SQLException e2) {
            throw e2;
        } finally {
            conn.close();// 关闭数据库连接
        }
    }
}
