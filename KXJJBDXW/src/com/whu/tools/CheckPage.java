package com.whu.tools;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ��ҳ������
 * 
 * */
public class CheckPage implements Serializable{

	public int queryPageNo;       // �����ҳ�� 
	public int rowsPerPage ;    // ÿҳ������ 
	public String querySql ;   // ��ѯ��Sql���
	public int totalPage;      // ��ҳ�� 
	public int totalRows;      // ������ 
	public int lastPageRows;   // ���һҳ������ 
	public String[] params;   // sql parameters
	
	public CheckPage(){
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jbInit() throws Exception
	{
		
	}
	
	public int getQueryPageNo() {
		return queryPageNo;
	}
	public void setQueryPageNo(int queryPageNo) {
		this.queryPageNo = queryPageNo;
	}
	public int getRowsPerPage() {
		return rowsPerPage;
	}
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	public String getQuerySql() {
		return querySql;
	}
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getLastPageRows() {
		return lastPageRows;
	}
	public void setLastPageRows(int lastPageRows) {
		this.lastPageRows = lastPageRows;
	}
	public String[] getParams() {
		return params;
	}
	public void setParams( String[] params) {
		this.params = params;
	}
	
}
