/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.whu.web.role;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.whu.tools.DBTools;
import com.whu.web.user.ConfigUserForm;
import com.whu.web.user.UserBean;

/** 
 * MyEclipse Struts
 * Creation date: 02-20-2014
 * 
 * XDoclet definition:
 * @struts.action path="/configRoleAction" name="configRoleForm" parameter="method" scope="request" validate="true"
 */
public class ConfigRoleAction extends DispatchAction {
	/*
	 * Generated Methods
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");	
		ConfigRoleForm configRoleForm = (ConfigRoleForm)form;
		String operation = request.getParameter("operation");
		
		String roleName = configRoleForm.getRoleName();
		String roleDescribe = configRoleForm.getRoleDescribe();
		String isUse = configRoleForm.getIsUse();		
		DBTools dbTool = new DBTools();
		
		String sql = "";
		String[] params = new String[0];
		if(operation.equals("newRole"))
		{
			sql = "insert into SYS_ROLE(ROLENAME,ROLEDESCRIBE,ISUSE,MODULEIDS) values(?, ?, ?, ?)";
			params = new String[]{roleName, roleDescribe, isUse, ""};
		}
		else if(operation.equals("editRole"))
		{
			String id = configRoleForm.getId();
			sql = "update SYS_ROLE set ROLENAME=?,ROLEDESCRIBE=?,ISUSE=? where ID=?";
			params = new String[]{roleName, roleDescribe, isUse, id};
		}
		boolean result = dbTool.insertItem(sql, params);
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		if(result)
		{
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
		out.close();
		
		return null;
	}
	/**
	 * 编辑用户信息，跳转
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");	
		ConfigRoleForm configRoleForm = (ConfigRoleForm)form;
		
		String id = request.getParameter("id");
		DBTools dbTools = new DBTools();
		String sql = "select * from SYS_ROLE where ID=?";
		RoleBean rb = dbTools.queryRoleBean(sql, new String[]{id});
		ArrayList result = new ArrayList();
		if(rb!=null)
		{
			result.add(rb);
			configRoleForm.setRecordNotFind("false");
			configRoleForm.setRecordList(result);
			return mapping.findForward("edit");
		}
		else
		{
			configRoleForm.setRecordNotFind("true");
			return mapping.findForward("initError");
		}
	}
}