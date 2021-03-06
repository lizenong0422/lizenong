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
import com.whu.tools.EmailTools;
import com.whu.tools.SystemConstant;
import com.whu.web.common.SystemShare;
import com.whu.web.email.EmailBean;
import com.whu.web.email.EmailInfo;
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
public class DeptAdviceAction extends DispatchAction {
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
		DeptAdviceForm deptAdviceForm = (DeptAdviceForm) form;
		boolean result = false;
		String loginName = (String)request.getSession().getAttribute("LoginName");
		String userName =  (String)request.getSession().getAttribute("UserName");
		String operatorFlag = request.getParameter("operatorFlag");
		String reportID = deptAdviceForm.getReportID();
		String emailtime = SystemShare.GetNowTime("yyyy-MM-dd HH:mm:ss");
		DBTools dbTools = new DBTools();
		String sql = "";
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
	
		if(operatorFlag.equals("sendEmail"))
		{
			String deptID = request.getParameter("deptID");
			String deptName = request.getParameter("deptName");//依托单位
			String deptemail = request.getParameter("deptemail");//依托单位邮箱
			String deptcontent = request.getParameter("deptcontent");
			String depttitle = request.getParameter("depttitle");
			String deptAdviceID = "";
			String accessoryPath = request.getSession().getServletContext().getRealPath("/") + "/temp/" + loginName + "/";//附件
			sql = "select * from TB_MAILCONFIG where ISDEFAULT=?";
			EmailBean emailBean = dbTools.queryEmailConfig(sql, new String[]{"1"});
			
			//所有发送的附件名，以“：”分割
			String attachNames = "";
			EmailTools emailTools = new EmailTools();
			if(emailBean != null)
			{
				EmailInfo emailInfo = new EmailInfo();
				emailInfo.setSendName(emailBean.getMailBoxAddress());
				emailInfo.setCsName("");
				emailInfo.setRecvName(deptemail);
				emailInfo.setTitle(depttitle);
				emailInfo.setContent(deptcontent);
				emailInfo.setAccessory(accessoryPath);
				result = emailTools.SendEmail(emailInfo, emailBean);
				if(!result)
				{
					//邮件没有发送成功，则将上传的附件删除
					SystemShare.deleteAllFiles(accessoryPath);
					json.put("statusCode", 300);
					json.put("message", "邮件发送失败！请检查您的网络连接是否正常，或者稍后重新发送！");
					//json.put("callbackType", "closeCurrent");
					json.put("navTabId", "addExpertAdvice");
					
					out.write(json.toString());
					out.flush();
					out.close();
					return null;
				}
				else//邮件发送成功后，需要将附件转存到举报文件夹下，用于专家反馈页面查看所有的附件
				{
					String filePath = request.getSession().getServletContext().getRealPath("/")+"/attachment/expert/";
					//path1=/home/apache-tomcat-8.0.9/webapps/KXJJBDXW/temp/chenls/
					String path1 = request.getSession().getServletContext().getRealPath("/") + "/temp/" + loginName + "/";
					String path2 = filePath + reportID;//path2 =/home/apache-tomcat-8.0.9/webapps/KXJJBDXW/attachment/expert/20151024151657
					attachNames = SystemShare.SaveEmailAttach(path1, path2);
					
					sql = "insert into TB_DEPTEMAIL(DEPTADVICEID,DEPTNAME,EMAILADDRESS,TITLE,EMAILCONTENT,ATTACHMENT,REPORTID,SENDEMAILTIME) values(?, ?,?, ?, ?, ?, ?, ?)";
					result = result &&  dbTools.insertItem(sql, new String[]{deptAdviceID, deptName, deptemail, depttitle, deptcontent, attachNames, reportID, emailtime});
					//写入日志文件
					dbTools.insertLogInfo(userName, SystemConstant.LOG_DEPTADVICE, "向依托单位发送邮件，事件编号为：" + reportID, request.getRemoteAddr());
				}
			}
		}else if(operatorFlag.equals("newAdvice"))
		{
			String dept = deptAdviceForm.getDept();
			String time = deptAdviceForm.getTime();		
			if(time == null || time.equals(""))
				time = SystemShare.GetNowTime("yyyy-MM-dd");
			String advice = deptAdviceForm.getAdvice();
			String expertAdvice = deptAdviceForm.getExpertAdvice();
			String id = deptAdviceForm.getId();
			String attachName = (String)request.getSession().getAttribute("EventAttachName");
			if(attachName != null && !attachName.equals(""))
			{
				attachName = "dept" + reportID + "/" + attachName;
				request.getSession().setAttribute("EventAttachName", "");
			}
			else
			{
				attachName = "";
			}
			String[] params = null;
			//如果不为空，则说明是编辑
			if(id != null && !id.equals(""))
			{
				if(attachName.equals(""))
				{
					sql = "update TB_DEPTADVICE set DEPT=?, TIME=?, ADVICE=?, EXPERTADVICE=?, ISFK='1' where ID=?";
					params = new String[]{dept, time, advice, expertAdvice, id};
				//	sql = "update TB_DEPTADVICE set DEPT='" + dept + "', TIME='" + time + "', ADVICE='"  + advice + "',EXPERTADVICE='" + expertAdvice + "',ISFK='1' where ID=" + id;
				}
				else
				{
					sql = "update TB_DEPTADVICE set DEPT=?, TIME=?, ADVICE=?, EXPERTADVICE=?, ISFK='1', ATTACHNAME=? where ID=?";
					params =  new String[]{dept, time, advice, expertAdvice, attachName, id};
				//	sql = "update TB_DEPTADVICE set DEPT='" + dept + "', TIME='" + time + "', ADVICE='"  + advice + "',EXPERTADVICE='" + expertAdvice + "',ISFK='1', ATTACHNAME='" + attachName + "' where ID=" + id;
				}
				
			}
			else//如果为空，则说明是新增
			{
			//	sql = "insert into TB_DEPTADVICE(REPORTID,DEPT,TIME,ADVICE,EXPERTADVICE,ISFK,ATTACHNAME,ISLETTER) values('" + reportID + "','" + dept + "','" + time + "','" + advice + "','" + expertAdvice + "','1','" + attachName + "', '0')";
				sql = "insert into TB_DEPTADVICE(REPORTID,DEPT,TIME,ADVICE,EXPERTADVICE,ISFK,ATTACHNAME,ISLETTER) values(?,?,?,?,?,'1',?,'0')";
				params = new String[]{reportID, dept, time, advice, expertAdvice, attachName};
			}
			String filePath = request.getSession().getServletContext().getRealPath("/")+"/attachment/dept/";
			//String path1 = filePath + "temp";
			String path1 = request.getSession().getServletContext().getRealPath("/") + "/temp/" + loginName + "/";
			String path2 = filePath + reportID;
			//将临时文件夹中的附件转存到以警情编号为目录的文件夹下
			//获得服务器的IP地址路径，存放在数据库中，便于下载
			String relDirectory = "attachment/dept/" + reportID;
			String createName = (String)request.getSession().getAttribute("UserName");
			result = SystemShare.IOCopy(path1, path2, relDirectory, createName);
			result = dbTools.insertItem(sql, params);
			
			if(result)
			{
				String describe = time + "," + createName + "   编辑单位调查意见";
				//插入处理过程到数据库中
				result = dbTools.InsertHandleProcess(reportID, createName, SystemConstant.HP_DEPTADVICE, SystemConstant.SS_SURVEYING, SystemConstant.LCT_DWDC, describe);
				
				//写入日志文件
				dbTools.insertLogInfo(createName, SystemConstant.LOG_DEPTADVICE, "编辑依托单位意见，事件编号为：" + reportID, request.getRemoteAddr());
			
				//更新事件的最近一次操作时间
				result = dbTools.UpdateLastTime(reportID);
			}
		}
		
		
		
		if(result)
		{
			//防止高级检索功能模块执行
			request.getSession().setAttribute("GjSearch", "false");
			json.put("statusCode", 200);
			json.put("message", "操作成功！");
			//json.put("callbackType", "closeCurrent");
			json.put("navTabId", "addDeptAdvice");
		}
		else
		{
			json.put("statusCode", 300);
			json.put("message", "操作失败！");
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
			json.put("navTabId", "addDeptAdvice");
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
	/**
	 * 打开编辑调查函的页面，可能是新增，也可能是编辑已经存在的记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward createDCH(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		DeptAdviceForm deptAdviceForm = (DeptAdviceForm)form;
		String type = request.getParameter("type");
		ArrayList result = new ArrayList();
		DeptSurveyLetter dsl = new DeptSurveyLetter();
		if(type.equals("edit"))
		{
			String id = request.getParameter("id");
			String sql = "select a.*,b.REPORTID from TB_DEPTSURVEYLETTER a,TB_DEPTADVICE b where a.DEPTADVICEID=b.ID and b.ID=?";
			DBTools dbTools = new DBTools();
			dsl = dbTools.queryDeptSurveyLetter(sql, new String[]{id});
		}
		else if(type.equals("new"))
		{
			dsl = null;
		}
		if(dsl==null)
		{
			dsl = new DeptSurveyLetter();
			dsl.setDeptName("");
			dsl.setFkTime("");
			dsl.setLoginName("");
			dsl.setPassword("");
			dsl.setShortInfo("");
			dsl.setSurveyContent("");
			dsl.setTitle("关于委托调查核实XXX基金项目相关问题的函");
			dsl.setIsEdit("0");
		}
		else
		{
			dsl.setIsEdit("1");
		}
		result.add(dsl);
		deptAdviceForm.setRecordList(result);
		return mapping.findForward("createDCH");
	}
	/**
	 * 编辑调查函内容
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 */
	public ActionForward saveDCH(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		DeptAdviceForm deptAdviceForm = (DeptAdviceForm)form;
		String reportID = (String)request.getSession().getAttribute("deptReportID");
		String title = deptAdviceForm.getTitle();
		String deptName = deptAdviceForm.getDeptName();
		String shortInfo = deptAdviceForm.getShortInfo();
		String fkTime = deptAdviceForm.getFkTime();
		String loginName = deptAdviceForm.getLoginName();
		String password = deptAdviceForm.getPassword();
		String surveyContent = deptAdviceForm.getSurveyContent();
		String isEdit = request.getParameter("isEdit");
		DBTools dbTools = new DBTools();
		//获得当前日期
		String createTime = SystemShare.GetNowTime("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String year=createTime.substring(0, 4);
		String month=createTime.substring(5, 7);
		String day=createTime.substring(8, 10);
		GregorianCalendar worldTime =new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month)-1,Integer.parseInt(day));
		worldTime.add(GregorianCalendar.DATE,20);
		Date d=worldTime.getTime();
		String valTime_string=forma.format(d);
		Date date = forma.parse(valTime_string);
		String endTime = forma.format(date);

		String sql = "";
		String[] params = new String[0];
		boolean result = false;
		if(isEdit.equals("1"))//编辑
		{
			String id = deptAdviceForm.getAdviceID();
		//	sql = "update TB_DEPTADVICE set DEPT = '" + deptName + "',TIME='" + fkTime + "' where ID=" + id;
			sql = "update TB_DEPTADVICE set DEPT=?, TIME=? where ID=?";
			params = new String[]{deptName, fkTime, id};
			result = dbTools.insertItem(sql, params);
			if(result)
			{
				String dchID = deptAdviceForm.getDchID();
			//	sql = "update TB_DEPTSURVEYLETTER set TITLE = '" + title + "',DEPTNAME='" + deptName + "',SHORTINFO='" + shortInfo + "',FKTIME='" + fkTime + "',SURVEYCONTENT='" + surveyContent + "' where ID=" + dchID;
				sql = "update TB_DEPTSURVEYLETTER set TITLE=?, DEPTNAME=?, SHORTINFO=?, FKTIME=?, SURVEYCONTENT=? where ID=?";
				params = new String[]{title, deptName, shortInfo, fkTime, surveyContent, id};
				result = dbTools.insertItem(sql, params);
			}
		}
		else if(isEdit.equals("0"))//新增
		{
			//插入一条记录到依托单位调查结果表中
		//	sql = "insert into TB_DEPTADVICE(REPORTID,DEPT,TIME,SERIALNUM,ISFK, ISLETTER) values('" + reportID + "','" + deptName + "','" + fkTime + "','','0', '1')";
			sql = "insert into TB_DEPTADVICE(REPORTID,DEPT,TIME,SERIALNUM,ISFK, ISLETTER) values(?,?,?,'','0','1')";
			params = new String[]{reportID, deptName, fkTime};
			result = dbTools.insertItem(sql, params);
			if(result)
			{
				String deptAdviceID = dbTools.queryLastInsertID("TB_DEPTADVICE");
			//	sql = "insert into TB_DEPTSURVEYLETTER(DEPTADVICEID,TITLE,DEPTNAME,SHORTINFO,FKTIME,SURVEYCONTENT,FILEPATH,LOGINNAME,PASSWORD) values('" + deptAdviceID + "','" + title + "','" + deptName + "','" + shortInfo + "','" + fkTime + "','" + surveyContent + "','','" + loginName + "','" + password + "')";
				sql = "insert into TB_DEPTSURVEYLETTER(DEPTADVICEID,TITLE,DEPTNAME,SHORTINFO,FKTIME,SURVEYCONTENT,FILEPATH,LOGINNAME,PASSWORD) values(?,?,?,?,?,?,'',?,?)";
				params = new String[]{deptAdviceID, title, deptName, shortInfo, fkTime, surveyContent, loginName, password};
				result = dbTools.insertItem(sql, params);
				if(loginName != null && !loginName.equals(""))
				{
					//自动根据生成的账号和密码创建一个新的账户，分配“依托单位”的权限
					//sql = "if not exists(select * from SYS_ED_USER where LOGINNAME='" + loginName + "') insert into SYS_ED_USER(LOGINNAME, PASSWORD, DEPTNAME, ROLEIDS, ISUSE) values('" + loginName + "','" + password + "','" + deptName + "','9','1') else update SYS_ED_USER set PASSWORD='" + password + "' where LOGINNAME='" + loginName + "'";
					//System.out.println(tempsql);
					String tempsql="select * from SYS_ED_USER where LOGINNAME=?";
					boolean flag=dbTools.queryISEXIST(tempsql, new String[]{loginName});
					//System.out.println(flag);
					if(flag)
					{
					//	sql = "insert into SYS_ED_USER(LOGINNAME, PASSWORD, DEPTNAME, ROLEIDS, ISUSE) values('" + loginName + "','" + password + "','" + deptName + "','5','1')";
						sql = "insert into SYS_ED_USER(LOGINNAME, PASSWORD, DEPTNAME, ROLEIDS, ISUSE,CREATETIME,ENDTIME) values(?, ?, ?, '5', '0',?,?)";
						params = new String[]{loginName, password, deptName,createTime,endTime};
					}
					else
					{
					//	sql = "update SYS_ED_USER set PASSWORD='" + password + "' where LOGINNAME='" + loginName + "'";
						sql = "update SYS_ED_USER set PASSWORD=?,ISUSE='0',LOGINTIME='',CREATETIME=?,ENDTIME=? where LOGINNAME=?";
						params = new String[]{password, createTime,endTime,loginName};
					}
					//System.out.println(sql);
					result = dbTools.insertItem(sql, params);
					//向单位反馈记录表中 插入一条记录
			    //sql = "insert into TB_ED_ADVICE(REPORTID,LOGINNAME,EVENTTITLE,FKTIME,ISSUBMIT,ATTACHMENT,ADVICEID) values('" + reportID + "','" + loginName + "','" + title + "','" + fkTime + "','0','','" + deptAdviceID + "')";
					sql = "insert into TB_ED_ADVICE(REPORTID,LOGINNAME,EVENTTITLE,FKTIME,ISSUBMIT,ATTACHMENT,ADVICEID) values(?,?,?,?,'0','',?)";
					params = new String[]{reportID, loginName, title, fkTime, deptAdviceID};
					result = dbTools.insertItem(sql, params);
				}
			}
			
			String createName = (String)request.getSession().getAttribute("UserName");
			String describe = createTime + "," + createName + "   编辑单位调查意见";
			//插入处理过程到数据库中
			result = dbTools.InsertHandleProcess(reportID, createName, SystemConstant.HP_DEPTADVICE, SystemConstant.SS_SURVEYING, SystemConstant.LCT_DWDC, describe);
		}
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		if(result)
		{
			json.put("statusCode", 200);
			json.put("message", "保存成功！");
			json.put("callbackType", "closeCurrent");
			json.put("navTabId", "addDeptAdvice");
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
	 * 在线编辑调查函文档，自动填充后台数据到word文档中
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward makeDCH(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("dchID");
		if(id.equals(""))
		{
			return null;
		}
		DBTools dbTools = new DBTools();
		String sql = "select a.REPORTID, b.* from TB_DEPTADVICE a, TB_DEPTSURVEYLETTER b where a.ID=b.DEPTADVICEID and b.ID=?";
		DeptSurveyLetter dsl = dbTools.queryDeptSurveyLetter(sql, new String[]{id});
		String templatePath = "";
		request.setAttribute("IsEdit", "0");
		if(dsl != null)
		{
			String tempFile = dsl.getFilePath();
			String filePath = request.getSession().getServletContext().getRealPath("/")+"/attachment/";
			if(tempFile.equals(""))//如果是新增，则调出模板发送到客户端
			{	
				templatePath = SystemConstant.GetServerPath() + "/web/template/ytdwdch.doc";
			}
			else//如果是编辑，则查询数据库判断上次编辑过的文件是否存在，若存在，则发送到客户端继续编辑
			{
				String tempFilePath = filePath + tempFile;
				if((new File(tempFilePath)).exists())//如果存在，则得到路径
				{
					templatePath = SystemConstant.GetServerPath() + "/attachment/" +  tempFile;
					request.setAttribute("IsEdit", "1");
				}
				else//不存在，则继续使用模板，例如：人工删除或系统出错
				{
					templatePath = SystemConstant.GetServerPath() + "/web/template/ytdwdch.doc";
				}
			}
			
		}
		else
		{
			templatePath = SystemConstant.GetServerPath() + "/web/template/ytdwdch.doc";
			dsl = new DeptSurveyLetter();
			dsl.setDeptName("");
			dsl.setFkTime("");
			dsl.setLoginName("");
			dsl.setPassword("");
			dsl.setShortInfo("");
			dsl.setSurveyContent("");
			dsl.setTitle("关于委托调查核实XXX基金项目相关问题的函");
			String reportID = (String)request.getSession().getAttribute("deptReportID");
			dsl.setReportID(reportID);
		}

		String createTime = SystemShare.GetNowTime("yyyy-MM-dd");
		String year = createTime.substring(0, 4);
		String month = createTime.substring(5, 7);
		String day = createTime.substring(8, 10);
		dsl.setYear(year);
		dsl.setMonth(month);
		dsl.setDay(day);
		
		request.setAttribute("ServerPath", SystemConstant.GetServerPath());
		request.setAttribute("templatePath", templatePath);
		request.setAttribute("DeptSurveyLetter", dsl);
		return mapping.findForward("deptSurveyLetter");
	}
}