/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.whu.web.dic;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.whu.tools.CheckPage;
import com.whu.tools.DBTools;

/** 
 * MyEclipse Struts
 * Creation date: 08-21-2013
 * 
 * XDoclet definition:
 * @struts.action path="/dicManageAction" name="dicManageForm" input="/web/dic/dicManage.jsp" parameter="method" scope="request" validate="true"
 */
public class DicManageAction extends DispatchAction {
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DicManageForm dicManageForm = (DicManageForm) form;// TODO Auto-generated method stub
		CheckPage pageBean = new CheckPage();
		int queryPageNo = 1;
		int rowsPerPage = 20;
		pageBean.setRowsPerPage(rowsPerPage);
		pageBean.setQueryPageNo(queryPageNo);
		String sql = "select ID,CODENAME,CODE,CAPTION,REMARK from SYS_DATA_DIC";
		request.getSession().setAttribute("queryDicSql", sql);
		request.getSession().setAttribute("queryDicParams", new String[0]);
		pageBean.setQuerySql(sql);
		pageBean.setParams(new String[0]);
		DBTools db = new DBTools();
		ResultSet rs = db.queryRs(queryPageNo, pageBean, rowsPerPage);
		ArrayList result = db.queryDicList(rs, rowsPerPage);
		if(result.size() > 0)
		{
			dicManageForm.setRecordNotFind("false");
			dicManageForm.setRecordList(result);
			
			int totalRows = pageBean.getTotalRows();
			int pagecount = pageBean.getTotalPage();// �õ���ҳ��
			int currentPage = pageBean.getQueryPageNo();// �õ���ǰҳ
			request.setAttribute("pageNum",String.valueOf(currentPage));
			request.setAttribute("totalRows",String.valueOf(totalRows));
			request.setAttribute("pageCount",String.valueOf(pagecount));
		}
		else
		{
			dicManageForm.setRecordNotFind("true");
			request.setAttribute("pageNum",String.valueOf(0));
			request.setAttribute("totalRows",String.valueOf(0));
			request.setAttribute("pageCount",String.valueOf(0));
		}
		return mapping.findForward("init");
	}
	
	public ActionForward queryMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");	
		DicManageForm dicManageForm = (DicManageForm) form;
		String operation = request.getParameter("operation");
		
		
		CheckPage pageBean = new CheckPage();
		String sql = "";
		String[] params = new String[0];
		int queryPageNo = 1;
		int rowsPerPage = 20;
		pageBean.setRowsPerPage(rowsPerPage);
		
		//�������ѯ��,����ѡ�����β˵�
		if (operation != null  && (operation.equalsIgnoreCase("search") || operation.equalsIgnoreCase("select"))) {
			String codeName = "";
			if(operation.equalsIgnoreCase("search"))
			{
				codeName = dicManageForm.getCodeName();
			}
			else if (operation.equalsIgnoreCase("select"))
			{
				codeName = request.getParameter("id");
			}
			if(codeName == null || codeName.equals("")){
				sql = "select ID,CODENAME,CODE,CAPTION,REMARK from SYS_DATA_DIC";
			}
			else
			{
				sql = "select ID,CODENAME,CODE,CAPTION,REMARK from SYS_DATA_DIC where CODENAME=?";
				params = new String[]{codeName};
			}
			request.getSession().setAttribute("queryDicSql", sql);
			request.getSession().setAttribute("queryDicParams", params);
		}
		// ����Ƿ�ҳ
		else if(operation.equalsIgnoreCase("changePage")){
			sql = (String)request.getSession().getAttribute("queryDicSql");
			params = (String[])request.getSession().getAttribute("queryDicParams");
			if (request.getParameter("pageNum") != null && request.getParameter("pageNum") != "") {
				queryPageNo = Integer.parseInt(request.getParameter("pageNum"));
			}
		}
		pageBean.setQuerySql(sql);
		pageBean.setParams(params);
		pageBean.setQueryPageNo(queryPageNo);
		DBTools db = new DBTools();
		ResultSet rs = db.queryRs(queryPageNo, pageBean, rowsPerPage);
		ArrayList result = db.queryDicList(rs, rowsPerPage);
		if(result.size() > 0)
		{
			dicManageForm.setRecordNotFind("false");
			dicManageForm.setRecordList(result);
			
			int totalRows = pageBean.getTotalRows();
			int pagecount = pageBean.getTotalPage();// �õ���ҳ��
			int currentPage = pageBean.getQueryPageNo();// �õ���ǰҳ
			request.setAttribute("pageNum",String.valueOf(currentPage));
			request.setAttribute("totalRows",String.valueOf(totalRows));
			request.setAttribute("pageCount",String.valueOf(pagecount));
		}
		else
		{
			dicManageForm.setRecordNotFind("true");
			
			request.setAttribute("pageNum",String.valueOf(0));
			request.setAttribute("totalRows",String.valueOf(0));
			request.setAttribute("pageCount",String.valueOf(0));
		}
		return mapping.findForward("initList");
	}
	/**
	 * ɾ���ֵ���Ϣ�����idsΪ�գ����ʾ����ԱҪɾ�����¼�����Ϊ�գ����ʾ����ɾ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");


		DBTools dbTool = new DBTools();
		String sql = "";
		String ids = request.getParameter("ids");
		String[] params = null;
		boolean result = false;
		if(ids == null || ids.equals(""))
		{
			String id = request.getParameter("id");
			sql = "delete from SYS_DATA_DIC where ID=?";
			params = new String[]{id};
			result = dbTool.deleteItem(sql, params);
		}
		else
		{
			String[] arrID = ids.split(",");
			result = dbTool.deleteItemsReal(arrID, "SYS_DATA_DIC", "ID");
		}
		if(result)
		{
			return mapping.findForward("deleteOK");
		}
		else
		{
			return mapping.findForward("deleteFail");
		}
	}
}