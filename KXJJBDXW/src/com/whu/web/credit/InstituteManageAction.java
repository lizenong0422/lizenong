/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.whu.web.credit;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.whu.tools.CheckPage;
import com.whu.tools.DBTools;
import com.whu.tools.DebugLog;
import com.whu.tools.ExcelTools;
import com.whu.web.common.SystemShare;
import com.whu.web.credit.InstituteInfo;
import com.whu.web.credit.InstituteManageForm;

/** 
 * MyEclipse Struts
 * Creation date: 11-19-2014
 * 
 * XDoclet definition:
 * @struts.action path="/instituteManage" name="instituteManageForm" scope="request" validate="true"
 */
public class InstituteManageAction extends DispatchAction {
	/*
	 * Generated Methods
	 */


	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		InstituteManageForm instituteManageForm = (InstituteManageForm)form;
		
		CheckPage pageBean = new CheckPage();
		int queryPageNo = 1;
		int rowsPerPage = 20;
		pageBean.setRowsPerPage(rowsPerPage);
		if (request.getParameter("queryPageNo") != null) {
			queryPageNo = Integer.parseInt(request.getParameter("queryPageNo"));
		}
		pageBean.setQueryPageNo(queryPageNo);
		String sql = "select a.*,ROUND(IFNULL(b.CREDIT, 1),4) as CREDIT,ifnull(c.COUNT,0) as COUNT from SYS_INST_INFO a left join VIEW_INSTITUTE_CREDIT b on a.CODE=b.INSTITUTE left join (select INSTID,count(MISCOUNTID) as COUNT from TB_MISCOUNT group by INSTID) c on a.CODE=c.INSTID order by CREDIT, COUNT desc,CODE";
		String[] params = new String[0];
		request.getSession().setAttribute("queryInstituteSql", sql);
		request.getSession().setAttribute("queryInstituteParams", params);
		pageBean.setQuerySql(sql);
		pageBean.setParams(params);
		DBTools db = new DBTools();
		ResultSet rs = db.queryRs(queryPageNo, pageBean, rowsPerPage);
		ArrayList result = db.queryInstituteList(rs, rowsPerPage);
				
		if(result.size() > 0)
		{
			instituteManageForm.setRecordNotFind("false");
			instituteManageForm.setRecordList(result);
			SystemShare.SplitPageFun(request, pageBean, 1);
		}
		else
		{
			instituteManageForm.setRecordNotFind("true");
			SystemShare.SplitPageFun(request, pageBean, 0);
		}
		return mapping.findForward("init");
	}
	
	
	public ActionForward queryMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");	
		
		InstituteManageForm instituteManageForm = (InstituteManageForm)form;
		String operation = request.getParameter("operation");
		CheckPage pageBean = new CheckPage();
		String sql = "";
		String[] params = new String[0];
		int queryPageNo = 1;
		int rowsPerPage = 20;
		pageBean.setRowsPerPage(rowsPerPage);
		if (operation != null && operation.equalsIgnoreCase("search")) {
			String name = instituteManageForm.getName();
			String code = instituteManageForm.getCode();
			String category = instituteManageForm.getCategory();
			String temp = "";
			ArrayList<String> paramList = new ArrayList<String>();
			if(category != null && !category.equals("")) {
				temp += " and CATEGORY = ?";
				paramList.add(category);
			}
			if(name != null && !name.equals(""))
			{
				temp += " and NAME like ?";
				paramList.add("%" + name + "%");
			}
			if(code != null && !code.equals(""))
			{
				temp += " and CODE like ?";
				paramList.add("%" + code + "%");
			}
			params = paramList.toArray(new String[0]);
			sql = "select a.*, ROUND(ifnull(b.CREDIT, 1),4) as CREDIT,ifnull(c.COUNT,0) as COUNT from (select * from SYS_INST_INFO where 1=1 " + temp + ") as a left join VIEW_INSTITUTE_CREDIT b on a.CODE=b.INSTITUTE left join (select INSTID,count(MISCOUNTID) as COUNT from TB_MISCOUNT group by INSTID) c on a.CODE=c.INSTID order by CREDIT, COUNT desc,CODE";
			request.getSession().setAttribute("queryInstituteSql", sql);	
			request.getSession().setAttribute("queryInstituteParams", params);
			request.setAttribute("category", category);
		}
		else if(operation.equalsIgnoreCase("changePage")){
			sql = (String)request.getSession().getAttribute("queryInstituteSql");
			params = (String[])request.getSession().getAttribute("queryInstituteParams");
			if (request.getParameter("pageNum") != null && request.getParameter("pageNum") != "") {
				queryPageNo = Integer.parseInt(request.getParameter("pageNum"));
			}
		}
		pageBean.setQuerySql(sql);
		pageBean.setParams(params);
		pageBean.setQueryPageNo(queryPageNo);
		DBTools db = new DBTools();
		ResultSet rs = db.queryRs(queryPageNo, pageBean, rowsPerPage);
		ArrayList result = db.queryInstituteList(rs, rowsPerPage);
		if(result.size() > 0)
		{
			instituteManageForm.setRecordNotFind("false");
			instituteManageForm.setRecordList(result);
			
			SystemShare.SplitPageFun(request, pageBean, 1);
		}
		else
		{
			instituteManageForm.setRecordNotFind("true");
			
			SystemShare.SplitPageFun(request, pageBean, 0);
		}
		return mapping.findForward("init");
	}
	
	
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

		boolean result = false;
		String ids = request.getParameter("ids");
		DBTools dbTool = new DBTools();
		if(ids == null || ids.equals(""))
		{
			String uid = request.getParameter("uid");
			result = dbTool.deleteItemReal(uid, "SYS_INST_INFO", "ID");
		}
		else
		{
			String[] arrID = ids.split(",");
			result = dbTool.deleteItemsReal(arrID, "SYS_INST_INFO", "ID");
		}
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		if(result)
		{
			json.put("statusCode", 200);
			json.put("message", "删除成功！");
		}
		else
		{
			json.put("statusCode", 300);
			json.put("message", "删除失败！");
		}
		out.write(json.toString());
		out.flush();
		out.close();
		
		return null;
	}
	
	public ActionForward export(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DBTools db = new DBTools();
		
		String sql = (String)request.getSession().getAttribute("queryInstituteSql");
		String[] params = (String[])request.getSession().getAttribute("queryInstituteparams");
		String num = request.getParameter("exportnum");
		if( num != null && Integer.parseInt(num) > 0){
			sql += " limit " + num;
		}
		try
		{
			String fname = "instituteList";
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment;filename=" + fname + ".xls");
			response.setContentType("application/msexcel");
			ResultSet rs = db.queryRsList(sql, params);
			rs.last();
			int length = rs.getRow();
			rs.beforeFirst();
			ArrayList result = db.queryInstituteList(rs, length);
			ExcelTools et = new ExcelTools();
			//et.createSheet(rs, os);
			String sheetName = "不端行为记录表";
			ArrayList titleList = new ArrayList();
			titleList.add("代码");
			titleList.add("名称");
			titleList.add("平均诚信值");
			titleList.add("不端案例人次");
			titleList.add("通讯地址");
			et.createEventSheet(result, os, sheetName,7, titleList);
			rs.close();
		}
		catch(Exception e)
		{
			DebugLog.WriteDebug(e);
		}
		return null;
	}
	
/**
 * check institute name then return code if exists 
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	
	public ActionForward getcode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

		String result = "";
		String name = request.getParameter("name");
		DBTools dbTool = new DBTools();
		String sql = "select CODE from SYS_INST_INFO where NAME=?";
		
		ResultSet rs = dbTool.queryRsList(sql, new String[]{name});
		
		if(rs != null & rs.next()){
			result = rs.getString("CODE");
		}
		
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		if(result != null && result != "")
		{
			json.put("code", result);
		}
		out.write(json.toString());
		out.flush();
		out.close();
		
		return null;
	}	
	
}