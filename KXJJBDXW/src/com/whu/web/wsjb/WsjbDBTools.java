package com.whu.web.wsjb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.whu.tools.CheckPage;
import com.whu.tools.DBPools;
import com.whu.tools.crypto.AESCrypto;
import com.whu.web.event.BeReportBean;

public class WsjbDBTools {

	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private Statement stmt = null;

	public WsjbDBTools() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/WW_REPORT",
					"root", "root");////3951345
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int getTotalRows(String sql, String[] params) {
		int count = 0;
		try {
			pst = conn.prepareStatement(sql);
			int i = 1;
			for (String param : params) {
				pst.setString(i++, param);
			}
			rs = pst.executeQuery();
			while (rs.next()) {
				count++;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.print("getTotalRows errors" + ex.getMessage());
		}
		return count;
	}
	
	public ResultSet queryRs(int queryPageNo, CheckPage pageBean,
			int rowsPerPage) {
		String sql = "";
		String[] params = new String[0];

		queryPageNo = pageBean.getQueryPageNo(); 
		rowsPerPage = pageBean.getRowsPerPage();
		if (pageBean.getQuerySql() != null)
			sql = pageBean.getQuerySql(); 
		if (pageBean.getParams() != null) 
			params = pageBean.getParams();
		int totalRows = getTotalRows(sql, params);
		pageBean.setTotalRows(totalRows);

		int totalPage = totalRows % rowsPerPage == 0 ? totalRows / rowsPerPage
				: totalRows / rowsPerPage + 1;
		pageBean.setTotalPage(totalPage);
		
		int lastPageRows = totalRows % rowsPerPage == 0 ? rowsPerPage
				: totalRows % rowsPerPage;
		pageBean.setLastPageRows(lastPageRows);

		try {
			pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int i = 1;
			for (String param : params) {
				pst.setString(i++, param);
			}
			rs = pst.executeQuery();
			int skipRows = (queryPageNo - 1) * rowsPerPage;
			for (i = 0; i < skipRows; i++)
				rs.next();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ArrayList queryWsjbList(ResultSet rs, int rowsPerPage) {
		ArrayList list = new ArrayList();
		try {
			int count = rowsPerPage;
			AESCrypto aes = new AESCrypto();
			String key = "TB_REPORTINFO";
			int num = 0;
			while (rs != null && rs.next() && count > 0) {
				num++;
				WsjbInfo wsjbInfo = new WsjbInfo();
				wsjbInfo.setId(String.valueOf(rs.getInt("ID")));
				wsjbInfo.setSerialNum(String.valueOf(num));
				wsjbInfo.setReportID(rs.getString("REPORTID"));
				wsjbInfo.setReportIP(rs.getString("REPORTIP"));
				wsjbInfo.setReportName(new String(aes.createDecryptor(rs.getBytes("REPORTNAME"), key)));
				wsjbInfo.setBeReportName(new String(aes.createDecryptor(rs.getBytes("BEREPORTNAME"), key)));
				//System.out.println("BEREPORTNAME="+(new String(aes.createDecryptor(rs.getBytes("BEREPORTNAME"), key))));
				//System.out.println("BEDEPT="+(new String(aes.createDecryptor(rs.getBytes("BEDEPT"), key))));
				wsjbInfo.setBeDept(new String(aes.createDecryptor(rs.getBytes("BEDEPT"), key)));
				wsjbInfo.setJbsy2(rs.getString("JBSY2"));
				//wsjbInfo.setDetail(new String(aes.createDecryptor(rs.getBytes("DETAIL"), key)));
				wsjbInfo.setTime(rs.getString("TIME"));
				list.add(wsjbInfo);
				count--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return list;
	}
	/**
	 * 查询最新的5个网上举报信息
	 * @param sql
	 * @return
	 */
	public ArrayList queryWsjbList(String sql, String[] params) {
		ArrayList list = new ArrayList();
		try {
			pst = conn.prepareStatement(sql);
			int i = 1;
			for (String param : params) {
				pst.setString(i++, param);
			}
			rs = pst.executeQuery();
			AESCrypto aes = new AESCrypto();
			String key = "TB_REPORTINFO";
			while (rs != null && rs.next()) {
				WsjbInfo wsjbInfo = new WsjbInfo();
				wsjbInfo.setId(String.valueOf(rs.getInt("ID")));
				wsjbInfo.setReportID(rs.getString("REPORTID"));
				wsjbInfo.setReportName(new String(aes.createDecryptor(rs.getBytes("REPORTNAME"), key)));
				wsjbInfo.setBeReportName(new String(aes.createDecryptor(rs.getBytes("BEREPORTNAME"), key)));
				wsjbInfo.setBeDept(new String(aes.createDecryptor(rs.getBytes("BEDEPT"), key)));
				wsjbInfo.setTime(rs.getString("TIME"));
				list.add(wsjbInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
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

	
	/**
	 * 从指定数据表中批量删除多条记录
	 * @param ids 记录的ID数组
	 * @param tableName 表名
	 * @return
	 * @throws SQLException 
	 */
	public boolean deleteItems(String[] ids, String tableName) throws SQLException
	{
		try {
			conn.setAutoCommit(false);
			String sql = "update " + tableName + " set ISRECV='2' where ID=?";
			
			pst = conn.prepareStatement(sql);
			for (int i = 0; i < ids.length; i++) {
				pst.setString(1, ids[i]);
				pst.addBatch();
			}
			pst.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
			return false;
		} finally {
			pst.close();
			conn.close();
		}
		return true;
	}
	/**
	 * 从指定数据库表中删除指定编号的记录
	 * @param id 编号
	 * @param tableName 表名
	 * @return
	 */
	public boolean deleteItem(String id, String tableName) {
		try {
			String sql = "update " + tableName + " set ISRECV='2' where ID=?";
			
			pst = conn.prepareStatement(sql);
			pst.setString(1, id);
			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally
		{
			closeConnection();
		}
		return true;
	}

	/**
	 * 查询被举报人信息，返回list
	 * @param sql
	 * @return
	 */
	public ArrayList queryBeReport(String sql, String[] params) {
		ArrayList list = new ArrayList();
		try {
			AESCrypto aes = new AESCrypto();
			String key = "TB_BEREPORTPE";
			pst = conn.prepareStatement(sql);
			int i = 1;
			for(String param: params) {
				pst.setString(i++, param);
			}
			rs = pst.executeQuery();
			BeReportBean brb = null;
			while (rs != null && rs.next()) {
				brb = new BeReportBean();
				brb.setReportID(params[0]);
				brb.setBeName(new String(aes.createDecryptor(rs.getBytes("BEREPORTNAME"), key)));
				brb.setBePosition(rs.getString("POSITION"));
				brb.setBeTelPhone(new String(aes.createDecryptor(rs.getBytes("TELPHONE"), key)));
				brb.setBeDept(new String(aes.createDecryptor(rs.getBytes("DEPTNAME"), key)));
				list.add(brb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public WsjbInfo QueryWsjbInfo(String id) {
		WsjbInfo wsjbInfo = new WsjbInfo();
		try {
			String sql = "select a.* from TB_REPORTINFO a where  a.ID=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, id);
			rs = pst.executeQuery();
			AESCrypto aes = new AESCrypto();
			String key = "TB_REPORTINFO";
			while (rs.next()) {
				wsjbInfo.setId(id);
				wsjbInfo.setReportID(rs.getString("REPORTID"));
				wsjbInfo.setReportIP(rs.getString("REPORTIP"));
				wsjbInfo.setReportName(new String(aes.createDecryptor(rs.getBytes("REPORTNAME"), key)));
				wsjbInfo.setIsNi(rs.getString("ISNI"));
				wsjbInfo.setSex(rs.getString("SEX"));
				wsjbInfo.setNation(rs.getString("NATION"));
				wsjbInfo.setDept(new String(aes.createDecryptor(rs.getBytes("DEPT"), key)));
				wsjbInfo.setMailAddres(new String(aes.createDecryptor(rs.getBytes("MAILADDRESS"), key)));
				wsjbInfo.setGdPhone(new String(aes.createDecryptor(rs.getBytes("GDPHONE"), key)));
				wsjbInfo.setTelPhone(new String(aes.createDecryptor(rs.getBytes("TELPHONE"), key)));
				wsjbInfo.setBeReportName(new String(aes.createDecryptor(rs.getBytes("BEREPORTNAME"), key)));
				wsjbInfo.setBeSex(rs.getString("BESEX"));
				wsjbInfo.setBeDept(new String(aes.createDecryptor(rs.getBytes("BEDEPT"), key)));
				wsjbInfo.setBePosition(rs.getString("BEPOSITION"));
				wsjbInfo.setBePhone(new String(aes.createDecryptor(rs.getBytes("BEPHONE"), key)));
				wsjbInfo.setNotice(rs.getString("NOTICE"));
				wsjbInfo.setJbsy1(rs.getString("JBSY1"));
				wsjbInfo.setJbsy2(rs.getString("JBSY2"));
				wsjbInfo.setDetail(new String(aes.createDecryptor(rs.getBytes("DETAIL"), key)));
				wsjbInfo.setTime(rs.getString("TIME"));
				wsjbInfo.setSearchID(rs.getString("SEARCHID"));
				wsjbInfo.setAttachPath(rs.getString("ATTACHPATH"));
				wsjbInfo.setSearchID(rs.getString("SEARCHID"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return wsjbInfo;
	}

	/**
	 * 后台接收成功后，将前台数据库的接收状态置为1，“已接收”
	 * @param id
	 * @return
	 */
	public boolean recvReport(String id) {
		try {
			String sql = "update TB_REPORTINFO set ISRECV='1' where ID=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, id);
			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean UpdateItem(String sql, String[] params)
	{
		try {
			pst = conn.prepareStatement(sql);
			int i = 1;
			for (String param : params) {
				pst.setString(i++, param);
			}
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 插入反馈信息
	 * @param time
	 * @param info
	 * @return
	 */
	public boolean InsertFKInfo(String time, String info, String searchID)
	{
		String sql = "insert into TB_FKINFO(SEARCHID, FKCONTENT,FKTIME) values(?, ?, ?)" ;
		
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, searchID);
			pst.setString(2, info);
			pst.setString(3, time);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally
		{
			closeConnection();
		}
		return true;
	}
	
	public ResultSet queryRsList(String sql, String[] params) {
		try {
			pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i = 1;
			for (String param : params) {
				pst.setString(i++, param);
			}
			rs = pst.executeQuery();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return rs;
	}
	
	/*
	 * 根据ID返回REPORTID
	 * 
	 */
	public String queryReportID(String id){
      String reportID = "";
		int ID = Integer.valueOf(id).intValue();
		String sql = "select REPORTID from TB_REPORTINFO where ID="+ID;
		try{
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				reportID = rs.getString("REPORTID");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		
		return reportID ;
	}
}
