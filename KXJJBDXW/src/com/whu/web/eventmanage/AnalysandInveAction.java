/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.whu.web.eventmanage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.whu.tools.DBTools;
import com.whu.tools.SystemConstant;
import com.whu.web.common.SystemShare;
import com.whu.web.eventbean.DeptSurveyLetter;
import com.whu.web.eventbean.HandleDecide;
import com.whu.web.eventbean.JDYJSBean;
import com.whu.web.eventbean.PrintBean;

/** 
 * MyEclipse Struts
 * Creation date: 01-20-2013
 * 
 * XDoclet definition:
 * @struts.action path="/deptAdviceAction" name="deptAdviceForm" parameter="method" scope="request" validate="true"
 */
public class AnalysandInveAction extends DispatchAction {
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
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		AnalysandInveForm analysAndInveForm = (AnalysandInveForm) form;
		boolean result = false;
		String loginName = (String)request.getSession().getAttribute("UserName");
		DBTools dbTools = new DBTools();
	
		String workername = analysAndInveForm.getWorkername();
		if(workername == null || workername.equals(""))
			workername = loginName;
		String time = analysAndInveForm.getTime();		
		if(time == null || time.equals(""))
			time = SystemShare.GetNowTime("yyyy-MM-dd");
		String content = analysAndInveForm.getContent();
		String reportID = analysAndInveForm.getReportID();
		String id = analysAndInveForm.getId();
		String attachName = (String)request.getSession().getAttribute("EventAttachName");
		if(attachName != null && !attachName.equals(""))
		{
			attachName = "ai" + reportID + "/" + attachName;
			request.getSession().setAttribute("EventAttachName", "");
		}
		else
		{
			attachName = "";
		}
		String sql = "";
		String[] params = null;
		//如果不为空，则说明是编辑
		if(id != null && !id.equals(""))
		{
			if(attachName.equals(""))
			{
				sql = "update TB_ANALYSANDINVE set WORKERNAME=?, TIME=?, CONTENT=? where ID=?";
				params = new String[]{workername, time, content, id};
			//	sql = "update TB_DEPTADVICE set DEPT='" + dept + "', TIME='" + time + "', ADVICE='"  + advice + "',EXPERTADVICE='" + expertAdvice + "',ISFK='1' where ID=" + id;
			}
			else
			{
				sql = "update TB_ANALYSANDINVE set WORKERNAME=?, TIME=?, CONTENT=?, ATTACHNAME=? where ID=?";
				params =  new String[]{workername, time, content, attachName, id};
			//	sql = "update TB_DEPTADVICE set DEPT='" + dept + "', TIME='" + time + "', ADVICE='"  + advice + "',EXPERTADVICE='" + expertAdvice + "',ISFK='1', ATTACHNAME='" + attachName + "' where ID=" + id;
			}
			
		}
		else//如果为空，则说明是新增
		{
		//	sql = "insert into TB_DEPTADVICE(REPORTID,DEPT,TIME,ADVICE,EXPERTADVICE,ISFK,ATTACHNAME,ISLETTER) values('" + reportID + "','" + dept + "','" + time + "','" + advice + "','" + expertAdvice + "','1','" + attachName + "', '0')";
			sql = "insert into TB_ANALYSANDINVE(REPORTID,WORKERNAME,TIME,CONTENT,ATTACHNAME) values(?,?,?,?,?)";
			params = new String[]{reportID, workername, time, content, attachName};
		}
		String filePath = request.getSession().getServletContext().getRealPath("/")+"/attachment/ai/";
		//String path1 = filePath + "temp";
		String path1 = request.getSession().getServletContext().getRealPath("/") + "/temp/" + loginName + "/";
		String path2 = filePath + reportID;
		//将临时文件夹中的附件转存到以警情编号为目录的文件夹下
		//获得服务器的IP地址路径，存放在数据库中，便于下载
		String relDirectory = "attachment/ai/" + reportID;
		String createName = (String)request.getSession().getAttribute("UserName");
		result = SystemShare.IOCopy(path1, path2, relDirectory, createName);
		
		try {
			result = dbTools.insertItem(sql, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(result)
		{
			String describe = time + "," + createName + "   编辑分析结论";
			//插入处理过程到数据库中
			result = dbTools.InsertHandleProcess(reportID, createName, SystemConstant.HP_ANALYSINVE, SystemConstant.SS_SURVEYING, SystemConstant.LCT_FXJL, describe);
			
			//写入日志文件
			dbTools.insertLogInfo(createName, SystemConstant.LOG_ANALYSINVE, "编辑分析结论，事件编号为：" + reportID, request.getRemoteAddr());
		
			//更新事件的最近一次操作时间
			result = dbTools.UpdateLastTime(reportID);
		}
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		if(result)
		{
			//防止高级检索功能模块执行
			request.getSession().setAttribute("GjSearch", "false");
			json.put("statusCode", 200);
			json.put("message", "保存成功！");
			//json.put("callbackType", "closeCurrent");
			json.put("navTabId", "addAnalysisAndInves");
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
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String ids = request.getParameter("ids");
		DBTools dbTool = new DBTools();
		boolean result = true;
		if(ids == null || ids.equals(""))
		{
			String id = request.getParameter("id");
			result = dbTool.deleteItemReal(id, "TB_DEPTADVICE", "ID");
		}
		else
		{
			String[] arrID = ids.split(",");
			result = dbTool.deleteItemsReal(arrID, "TB_DEPTADVICE", "ID");
		}
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		if(result)
		{
			//防止高级检索功能模块执行
			request.getSession().setAttribute("GjSearch", "false");
			json.put("statusCode", 200);
			json.put("message", "删除依托单位意见成功");
			json.put("navTabId", "addAnalysisAndInves");
			//json.put("callbackType", "closeCurrent");
		}
		else
		{
			json.put("statusCode", 300);
			json.put("message", "删除依托单位意见失败！");
		}
		out.write(json.toString());
		out.flush();
		out.close();
		
		return null;
	}
	public ActionForward refresh(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		return mapping.findForward("refresh");
	}
}