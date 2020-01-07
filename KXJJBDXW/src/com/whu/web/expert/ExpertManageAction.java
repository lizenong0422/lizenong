/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.whu.web.expert;

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
import com.whu.web.eventbean.ExpertInfo;
import com.whu.web.user.UserBean;
import com.whu.web.user.UserManageForm;

/** 
 * MyEclipse Struts
 * Creation date: 03-03-2014
 * 
 * XDoclet definition:
 * @struts.action path="/expertManageAction" name="expertManageForm" parameter="method" scope="request" validate="true"
 */
public class ExpertManageAction extends DispatchAction {
	/*
	 * Generated Methods
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ExpertManageForm expertManageForm = (ExpertManageForm)form;
		
		CheckPage pageBean = new CheckPage();
		int queryPageNo = 1;
		int rowsPerPage = 20;
		pageBean.setRowsPerPage(rowsPerPage);
		if (request.getParameter("queryPageNo") != null) {
			queryPageNo = Integer.parseInt(request.getParameter("queryPageNo"));
		}
		pageBean.setQueryPageNo(queryPageNo);
		String sql = "select * from SYS_EXPERTINFO";
		String[] params = new String[0];
		request.getSession().setAttribute("queryExpertSql", sql);
		request.getSession().setAttribute("queryExpertParams", params);
		pageBean.setQuerySql(sql);
		pageBean.setParams(params);
		DBTools db = new DBTools();
		ResultSet rs = db.queryRs(queryPageNo, pageBean, rowsPerPage);
		ArrayList result = db.queryExpertList(rs, rowsPerPage);
		if(result.size() > 0)
		{
			expertManageForm.setRecordNotFind("false");
			expertManageForm.setRecordList(result);
			SystemShare.SplitPageFun(request, pageBean, 1);
		}
		else
		{
			expertManageForm.setRecordNotFind("true");
			SystemShare.SplitPageFun(request, pageBean, 0);
		}
		return mapping.findForward("init");
	}
	
	public ActionForward queryMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");	
		
		ExpertManageForm expertManageForm = (ExpertManageForm)form;
		String operation = request.getParameter("operation");
		CheckPage pageBean = new CheckPage();
		String sql = "";
		String[] params = new String[0];
		int queryPageNo = 1;
		int rowsPerPage = 20;
		pageBean.setRowsPerPage(rowsPerPage);
		if (operation.equalsIgnoreCase("search")) {
			String expertName = expertManageForm.getExpertName();
			String dept = expertManageForm.getDept();
			String faculty = expertManageForm.getFaculty();
			String research = expertManageForm.getResearch();
			String temp = "";
			ArrayList<String> paramList = new ArrayList<String>();
			if(expertName != null && !expertName.equals(""))
			{
				temp += " and NAME like ?";
				paramList.add("%" + expertName + "%");
			}
			if(dept != null && !dept.equals(""))
			{
				temp += " and DEPT like ?";
				paramList.add("%" + dept + "%");
			}
			if(research != null && !research.equals(""))
			{
				temp += " and RESEARCH like ?";
				paramList.add('%' + research + "%");
			}
			if(faculty != null && !faculty.equals(""))
			{
				temp += " and FACULTY like ?";
				paramList.add("%" + faculty + "%");
			}
			params = paramList.toArray(new String[0]);
			sql = "select * from SYS_EXPERTINFO where 1=1 " + temp;
			request.getSession().setAttribute("queryExpertSql", sql);
			request.getSession().setAttribute("queryExpertParams", params);
		}
		else if(operation.equalsIgnoreCase("changePage")){
			sql = (String)request.getSession().getAttribute("queryExpertSql");
			params = (String[])request.getSession().getAttribute("queryExpertParams");
			if (request.getParameter("pageNum") != null && request.getParameter("pageNum") != "") {
				queryPageNo = Integer.parseInt(request.getParameter("pageNum"));
			}
		}
		pageBean.setQuerySql(sql);
		pageBean.setParams(params);
		pageBean.setQueryPageNo(queryPageNo);
		DBTools db = new DBTools();
		ResultSet rs = db.queryRs(queryPageNo, pageBean, rowsPerPage);
		ArrayList result = db.queryExpertList(rs, rowsPerPage);
		if(result.size() > 0)
		{
			expertManageForm.setRecordNotFind("false");
			expertManageForm.setRecordList(result);
			
			SystemShare.SplitPageFun(request, pageBean, 1);
		}
		else
		{
			expertManageForm.setRecordNotFind("true");
			
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
			result = dbTool.deleteItemReal(uid, "SYS_EXPERTINFO", "ID");
		}
		else
		{
			String[] arrID = ids.split(",");
			result = dbTool.deleteItemsReal(arrID, "SYS_EXPERTINFO", "ID");
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
	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");	
		
		ExpertManageForm expertManageForm = (ExpertManageForm)form;
		
		String id = request.getParameter("uid");
		DBTools dbTools = new DBTools();
		String sql = "select * from SYS_EXPERTINFO where ID=?";
		ExpertInfo expertInfo = dbTools.queryExpertInfo(sql, new String[]{id});
		ArrayList result = new ArrayList();
		if(expertInfo!=null)
		{
			result.add(expertInfo);
			expertManageForm.setRecordNotFind("false");
			expertManageForm.setRecordList(result);
			return mapping.findForward("detail");
		}
		else
		{
			expertManageForm.setRecordNotFind("true");
			return mapping.findForward("initError");
		}
	}
	/**
	 * 导出专家列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward export(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DBTools db = new DBTools();
		
		String sql = (String)request.getSession().getAttribute("queryExpertSql");
		String[] params = (String[])request.getSession().getAttribute("queryExpertParams");
		try
		{
			String fname = "expertList";
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment;filename=" + fname + ".xls");
			response.setContentType("application/msexcel");
			ResultSet rs = db.queryRsList(sql, params);
			rs.last();
			int length = rs.getRow();
			rs.beforeFirst();
			ArrayList result = db.queryExpertList(rs, length);
			ExcelTools et = new ExcelTools();
			//et.createSheet(rs, os);
			String sheetName = "专家信息表";
			ArrayList titleList = new ArrayList();
			titleList.add("序号");
			titleList.add("专家姓名");
			titleList.add("职称");
			titleList.add("单位");
			titleList.add("专业");
			titleList.add("研究方向");
			titleList.add("联系方式");
			titleList.add("所属学部");
			et.createEventSheet(result, os, sheetName,3, titleList);
			rs.close();
		}
		catch(Exception e)
		{
			DebugLog.WriteDebug(e);
		}
		return null;
	}
}