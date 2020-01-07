/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.whu.web.eventmanage;

import java.io.File;
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
import com.whu.tools.SystemConstant;
import com.whu.web.event.EventBean;
import com.whu.web.eventbean.JDYJSBean;
import com.whu.web.eventbean.SjybdBean;

/** 
 * MyEclipse Struts
 * Creation date: 05-26-2014
 * 
 * XDoclet definition:
 * @struts.action path="/sjybdManageAction" name="sjybdManageForm" parameter="method" scope="request" validate="true"
 */
public class SjybdManageAction extends DispatchAction {
	/*
	 * Generated Methods
	 */
	public ActionForward createSJYBD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		SjybdManageForm sjybdManageForm = (SjybdManageForm)form;
		String reportID = request.getParameter("id");
		String sql = "select * from TB_SJYBDINFO where REPORTID=?";
		DBTools dbTools = new DBTools();
		SjybdBean sb = dbTools.querySJYBD(sql, new String[]{reportID});
		ArrayList result = new ArrayList();
		if(sb==null)//生成阅办单信息还未保存，从TB_REPORTINFO提取相关信息保存到TB_SJYBDINFO
		{
			sb = new SjybdBean();
			sql = "select * from TB_REPORTINFO where REPORTID=?";
			EventBean eb = dbTools.queryEvent(sql, new String[]{reportID});

			String serialNum = eb.getSerialNum();
			String recvTime = eb.getCreateTime();
			sb.setReportID(reportID);
			sb.setSerialNum(serialNum);
			sb.setRecvTime(recvTime);
			sb.setComeName(eb.getReportName());
		}
		result.add(sb);
		sjybdManageForm.setRecordList(result);
		return mapping.findForward("createSJYBD");
	}
	public ActionForward saveSJYBD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		SjybdManageForm sjybdManageForm = (SjybdManageForm)form;
		String reportID = sjybdManageForm.getReportID();
		String serialNum = sjybdManageForm.getSerialNum();
		String comeName = sjybdManageForm.getComeName();
		String recvTime = sjybdManageForm.getRecvTime();
		String title = sjybdManageForm.getTitle();
		String proposedOpinion = sjybdManageForm.getProposedOpinion();//拟办意见

		DBTools dbTools = new DBTools();
		String checkSql="select * from TB_SJYBDINFO where REPORTID=?";
		SjybdBean sb = dbTools.querySJYBD(checkSql, new String[]{reportID});
		String sql="";
		String[] params = new String[0];
		if(sb==null)//原始保存
		{
			sql = "insert into TB_SJYBDINFO(REPORTID,TITLE,SERIALNUM,COMENAME,RECVTIME,PROPOSEDOPINION) values(?, ?, ?, ?, ?,?)";
			params = new String[]{reportID, title, serialNum, comeName, recvTime,proposedOpinion};
		}
		else//更新保存
		{
			sql = "update TB_SJYBDINFO set TITLE=?, SERIALNUM=?,recvTime=?,COMENAME=?,PROPOSEDOPINION=? where REPORTID=?";
			params = new String[]{title, serialNum, recvTime, comeName, proposedOpinion, reportID};
		}
		boolean result = dbTools.insertItem(sql, params);
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
	 * 在线生成收件阅办单文档
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward makeSJYBD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("reportID");
		if(id.equals(""))
		{
			return null;
		}
		DBTools dbTools = new DBTools();
		String sql = "select * from TB_SJYBDINFO where REPORTID=?";
		SjybdBean sb = dbTools.querySJYBD(sql, new String[]{id});
		
		String sjybdPath = "";
		if(sb !=null) sjybdPath = sb.getFilePath();
		
		//System.out.println(sjybdPath);
		
		String templatePath = "";
		boolean isEdit = true;
		String filePath = request.getSession().getServletContext().getRealPath("/")+"/attachment/";
		
		//System.out.println(filePath);
		
		if(sjybdPath != null && !sjybdPath.equals(""))//如果是编辑，则查询数据库判断上次编辑过的文件是否存在，若存在，则发送到客户端继续编辑
		{			
			String tempFilePath = filePath + sjybdPath;
			if((new File(tempFilePath)).exists())//如果存在，则得到路径
			{
				//templatePath = SystemConstant.GetServerPath() + "/attachment/" +  sjybdPath;
				templatePath = "attachment/" +  sjybdPath;
				request.setAttribute("IsEdit", "1");
			}
			else//不存在，则继续使用模板，例如：人工删除或系统出错
			{
				isEdit = false;
				
			}
		}
		else//如果是新增，则调出模板发送到客户端
		{
			isEdit = false;
		}
		if(!isEdit)
		{
			request.setAttribute("IsEdit", "0");
			//templatePath = SystemConstant.GetServerPath() + "/web/template/sjybd.doc";
			templatePath = "web/template/sjybd.doc";
			//System.out.println(templatePath);
			if(sb != null)
			{
				String serialNum = sb.getSerialNum();
				String numYear =serialNum.substring(0, 4);
//				String numID =String.valueOf(Integer.parseInt(serialNum.substring(4, serialNum.length())));
				String numID =serialNum.substring(4, serialNum.length());
				
				String recvTime = sb.getRecvTime();
				String year = recvTime.substring(0, 4);
				String month = recvTime.substring(5, 7);
				String day = recvTime.substring(8, 10);
				sb.setReportID(id);
				sb.setReportName(sb.getComeName());

				sb.setDay(day);
				sb.setMonth(month);
				sb.setNumID(numID);
				sb.setNumYear(numYear);
				sb.setYear(year);
				request.setAttribute("SJYBDBean", sb);
			}
		}
	
		request.setAttribute("ReportID", id);
		request.setAttribute("ServerPath", SystemConstant.GetServerPath());
		request.setAttribute("templatePath", templatePath);
		//request.setAttribute("DomainPath", "http://ri.nsfc.gov.cn/KXJJBDXW/web/template/sjybd.doc");
		return mapping.findForward("makeSJYBD");
	}
	
	public ActionForward recvYBD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		SjybdManageForm sjybdManageForm = (SjybdManageForm)form;
		String reportID = sjybdManageForm.getReportID();
		String serialNum = sjybdManageForm.getSerialNum();
		String comeName = sjybdManageForm.getComeName();
		String recvTime = sjybdManageForm.getRecvTime();
		String title = sjybdManageForm.getTitle();
		String proposedOpinion = sjybdManageForm.getProposedOpinion();//拟办意见
		
		DBTools dbTools = new DBTools();
		String checkSql="select * from TB_SJYBDINFO where REPORTID=?";
		SjybdBean sb = dbTools.querySJYBD(checkSql, new String[]{reportID});
		String sql="";
		String[] params = new String[0];
		if(sb==null)//原始保存
		{
			sql = "insert into TB_SJYBDINFO(REPORTID,TITLE,SERIALNUM,COMENAME,RECVTIME,PROPOSEDOPINION) values(?, ?, ?, ?, ?,?)";
			params = new String[]{reportID, title, serialNum, comeName, recvTime,proposedOpinion};
		}
		else//更新保存
		{
			sql = "update TB_SJYBDINFO set TITLE=?, SERIALNUM=?,recvTime=?,COMENAME=?,PROPOSEDOPINION=? where REPORTID=?";
			params = new String[]{title, serialNum, recvTime, comeName, proposedOpinion, reportID};
		}
		boolean result = dbTools.insertItem(sql, params);
		if(result)
		{
			System.out.println(reportID);
		}
		
		if(reportID.equals(""))
		{
			return null;
		}
		sql = "select * from TB_SJYBDINFO where REPORTID=?";
		sb = dbTools.querySJYBD(sql, new String[]{reportID});
		
		String sjybdPath = "";
		if(sb !=null) sjybdPath = sb.getFilePath();
		
		//System.out.println(sjybdPath);
		
		String templatePath = "";
		boolean isEdit = true;
		String filePath = request.getSession().getServletContext().getRealPath("/")+"/attachment/";
		
		//System.out.println(filePath);
		
		if(sjybdPath != null && !sjybdPath.equals(""))//如果是编辑，则查询数据库判断上次编辑过的文件是否存在，若存在，则发送到客户端继续编辑
		{			
			String tempFilePath = filePath + sjybdPath;
			if((new File(tempFilePath)).exists())//如果存在，则得到路径
			{
				templatePath = "/attachment/" +  sjybdPath;
				request.setAttribute("IsEdit", "1");
			}
			else//不存在，则继续使用模板，例如：人工删除或系统出错
			{
				isEdit = false;
				
			}
		}
		else//如果是新增，则调出模板发送到客户端
		{
			isEdit = false;
		}
		if(!isEdit)
		{
			request.setAttribute("IsEdit", "0");
			templatePath = "/web/template/sjybd.doc";
			//System.out.println(templatePath);
			if(sb != null)
			{
				String numYear =serialNum.substring(0, 4);
				String numID =String.valueOf(Integer.parseInt(serialNum.substring(4, serialNum.length())));
				
				String year = recvTime.substring(0, 4);
				String month = recvTime.substring(5, 7);
				String day = recvTime.substring(8, 10);
				sb.setReportID(reportID);
				sb.setReportName(sb.getComeName());

				sb.setDay(day);
				sb.setMonth(month);
				sb.setNumID(numID);
				sb.setNumYear(numYear);
				sb.setYear(year);
				request.setAttribute("SJYBDBean", sb);
			}
		}
	
		request.setAttribute("ReportID", reportID);
		request.setAttribute("ServerPath", SystemConstant.GetServerPath());
		request.setAttribute("templatePath", templatePath);
//		System.out.println(templatePath);
		return mapping.findForward("makeSJYBD");
	}
	
}