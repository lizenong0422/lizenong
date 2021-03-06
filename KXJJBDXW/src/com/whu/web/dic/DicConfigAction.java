/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.whu.web.dic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.whu.tools.DBTools;
import com.whu.web.email.EmailBean;
import com.whu.web.email.MailConfigForm;

/** 
 * MyEclipse Struts
 * Creation date: 08-21-2013
 * 
 * XDoclet definition:
 * @struts.action path="/dicConfigAction" name="dicConfigForm" parameter="method" scope="request" validate="true"
 */
public class DicConfigAction extends DispatchAction {
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
	 * @throws IOException 
	 */
	public ActionForward addDic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		DicConfigForm dicConfigForm = (DicConfigForm) form;
		String codeName = dicConfigForm.getCodeName();
		String code = dicConfigForm.getCode();
		String caption = dicConfigForm.getCaption();
		String remark = dicConfigForm.getRemark();
		String operation = request.getParameter("operation");
		String sql = "";
		String[] params = null;
		if (operation.equals("new")){
			//sql = "insert into SYS_DATA_DIC(CODENAME,CODE,CAPTION,REMARK) values('" + codeName + "','" + code + "','" + caption + "','" + remark + "')";
			sql = "insert into SYS_DATA_DIC(CODENAME,CODE,CAPTION,REMARK) values(?,?,?,?)";
			params = new String[]{codeName, code, caption, remark};
		}
		if (operation.equals("edit")) {
			String id = dicConfigForm.getId();
			sql = "update SYS_DATA_DIC set CODENAME='" + codeName + "', CODE='" + code + "', CAPTION='" + caption + "', REMARK='" + remark + "' where ID='" + id + "'";
			sql = "update SYS_DATA_DIC set CODENAME=?, CODE=?, CAPTION=?, REMARK=? where ID=?";
			params = new String[]{codeName, code, caption, remark, id};
		}
		DBTools db = new DBTools();
		boolean result = db.insertItem(sql, params);
		db.closeConnection();
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		if(result)
		{
			request.getSession().setAttribute("configFlag", "true");
			json.put("statusCode", 200);
			json.put("message", "保存成功！");
			json.put("callbackType", "closeCurrent");
		}
		else
		{
			json.put("statusCode", 300);
			json.put("message", "保存失败！");
		}
		out.write(json.toString());
		out.flush();
		return null;
	}
	
	/**
	 * �༭�ֵ���Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DicConfigForm dicConfigForm = (DicConfigForm) form;
		String id = request.getParameter("id");
		DBTools dbTools = new DBTools();
		
		DicBean dicBean = dbTools.queryDicConfig(id);
		if(dicBean!=null)
		{
			ArrayList result = new ArrayList();
			result.add(dicBean);
		
			dicConfigForm.setRecordList(result);
			return mapping.findForward("edit");
		}
		else
		{
			return mapping.findForward("fail");
		}
	}
}