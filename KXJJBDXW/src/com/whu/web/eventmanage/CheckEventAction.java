/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.whu.web.eventmanage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import com.whu.tools.SystemConstant;
import com.whu.web.common.SystemShare;
import com.whu.web.event.BeReportBean;
import com.whu.web.event.EventBean;

/** 
 * MyEclipse Struts
 * Creation date: 09-03-2013
 * 
 * XDoclet definition:
 * @struts.action path="/checkEventAction" name="checkEventForm" parameter="method" scope="request" validate="true"
 */
public class CheckEventAction extends DispatchAction {
	/*
	 * Generated Methods
	 */
	/**
	 * 上报初步核实信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public ActionForward report(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		CheckEventForm checkEventForm = (CheckEventForm) form;
		String reportID = request.getParameter("reportID");
		
		String faculty = "";//所属学部
		String preAdvice = checkEventForm.getPreAdvice();//初步核实意见
		String checkName = checkEventForm.getCheckName();//核实人姓名
		
		/*String beReportName = checkEventForm.getBeReportName();//当事人姓名
		String idNumber = checkEventForm.getIdNumber();//身份证号
		String title = checkEventForm.getTitle();//职称
		String institution = checkEventForm.getInstitution();//单位
		String telphone = checkEventForm.getTelphone();//手机
		String email = checkEventForm.getEmail();//邮箱*/
		
		
		//报送领导，多个时用“,”分开
		String bsHead = "";//request.getParameter("org3.userName");
		String userName = (String)request.getSession().getAttribute("UserName");
		
		String[] arrHead = bsHead.split(",");
		
		//工作提醒类型
		String msgType = SystemConstant.MSG_GZTX;
		String sendTime = SystemShare.GetNowTime("yyyy-MM-dd HH:mm:ss");
		String checkTime = SystemShare.GetNowTime("yyyy-MM-dd");
		String isHandle = "0";
		String isNotify = "0";
		
		DBTools db = new DBTools();
		boolean result = false;
		boolean result1 = false;
		//*********coding test
		String beName = "";
		String bePosition = "";
		String beTelPhone = "";
		String beDept = "";
		String befaculty = "";
		String beidNumber = "";
		String relatedProject = "";
		String relatedProjectsl = "";
		ArrayList list = new ArrayList();
		CheckEventForm cef = null;
		for(int i = 0; i < SystemConstant.beReportNum; i++)
		{
			beName = request.getParameter("beReportedList[" + i + "].beName");
			bePosition = request.getParameter("beReportedList[" + i + "].bePosition");
			beTelPhone = request.getParameter("beReportedList[" + i + "].beTelPhone");
			beDept = request.getParameter("beReportedList[" + i + "].beDept");
			befaculty = request.getParameter("beReportedList[" + i + "].befaculty");
			//System.out.println("befaculty: " + befaculty);
			beidNumber = request.getParameter("beReportedList[" + i + "].beidNumber");
			relatedProject = request.getParameter("beReportedList[" + i + "].berelatedProject");
			relatedProjectsl = request.getParameter("beReportedList[" + i + "].berelatedProjectsl");
			if(beName != null && beName != "")
			{
				cef = new CheckEventForm();
				cef.setReportID(reportID);
				cef.setPreAdvice(preAdvice);
				cef.setBsHead(bsHead);
				cef.setCheckName(checkName);
				cef.setFaculty(befaculty);
				cef.setBeReportName(beName);
				cef.setIdNumber(beidNumber);
				cef.setTitle(bePosition);
				cef.setInstitution(beDept);
				cef.setTelphone(beTelPhone);
				cef.setEmail("blank@nsfc.com");
				cef.setRelatedProject(relatedProject);
				cef.setRelatedProjectsl(relatedProjectsl);
				list.add(cef);
				faculty += befaculty;
			}
		}
		try {
			//插入被举报人信息
			result1 = db.updateBeReport(list);
		} catch (SQLException e) {
			e.printStackTrace();
			result1 = false;
		}
		//**********coding test	
	
	//	String sql = "insert into TB_CHECKINFO(REPORTID,PREADVICE,BSHEAD,CHECKNAME,CHECKTIME) values('" + reportID + "','" + preAdvice + "','" + bsHead + "', '" + checkName + "','" + checkTime + "')";
		/*String sql = "insert into TB_CHECKINFO(REPORTID,PREADVICE,BSHEAD,CHECKNAME,CHECKTIME,BEREPORTNAME,IDNUMBER,TITLE,INSTITUTION,TELPHONE,EMAIL,RELATEDPROJECT) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String[] params = new String[]{reportID, preAdvice, bsHead, checkName, checkTime,beReportName,idNumber,title,institution,telphone,email,relatedProject};
		result = db.insertItem(sql, params);*/
		String sql = null;
		String[] params = null;
		sql = "select * from TB_CHECKINFO where REPORTID=?";//coding test
		if(db.queryCheckInfo(sql, new String[]{reportID}) == null){
			result = db.insertCheckInfoList(list);
		}else{
			result = db.updateCheckInfo(list);
		}//coding test
		
		
		String loginName=db.querySingleData("TB_REPORTINFO", "OFFICER", "REPORTID", reportID);
		String officer=db.querySingleData("SYS_USER", "USERNAME", "LOGINNAME", loginName);
		if(result && result1)
		{
			//将事件状态修改为“已初步核实”
		//	sql = "update TB_REPORTINFO set STATUS='" + SystemConstant.SS_CHECKEVENT + "',LASTTIME='" + checkTime + "',FACULTY='" + faculty + "' where REPORTID='"+ reportID + "'";
			sql = "update TB_REPORTINFO set STATUS=?, LASTTIME=?, FACULTY=? where REPORTID=?";
			params = new String[]{SystemConstant.SS_CHECKEVENT, checkTime, faculty, reportID};
			result = db.insertItem(sql, params);
			//插入到消息提醒数据库表中
			try
			{
				result = db.saveMsgNotify(userName, arrHead, reportID, sendTime, msgType, isHandle, isNotify,officer);
				//插入处理过程到数据库中
				String describe = checkTime + "," + checkName + "提交初步核实意见,核实意见详情请查看《审核信息》一栏";
				db.InsertHandleProcess(reportID, checkName, SystemConstant.HP_CHECKEVENT, SystemConstant.SS_CHECKEVENT,SystemConstant.LCT_DCHS, describe);
				//插入反馈信息到门户网站数据库中，便于实名举报用户利用查询码查询反馈信息
				//db.InsertFKInfo(reportID, SystemConstant.FK_CHECKEVENT, checkTime);
				//写入日志文件
				db.insertLogInfo(userName, SystemConstant.LOG_CHECK, "录入初步核实信息，事件编号为：" + reportID, request.getRemoteAddr());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		if(result)
		{
			json.put("statusCode", 200);
			json.put("message", "初核信息上报成功");
			json.put("callbackType", "closeCurrent");
		}
		else
		{
			json.put("statusCode", 300);
			json.put("message", "初核信息上报失败");
		}
		out.write(json.toString());
		out.flush();
		out.close();
		return null;
	}
	public ActionForward sbld(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		CheckEventForm checkEventForm = (CheckEventForm) form;
		DBTools db = new DBTools();
		String reportID = request.getParameter("reportID");
		String hsbshead=db.querySingleData("TB_CHECKINFO", "BSHEAD", "REPORTID", reportID);
		String bsHead ="";
		String sbldID=request.getParameter("sbldID");
		String sbld=db.querySingleData("SYS_USER", "USERNAME", "ID", sbldID);
		if(hsbshead!=null&&hsbshead!="")
		{
			//报送领导，多个时用“,”分开
			bsHead =hsbshead+","+ sbld;
		}
		String userName = (String)request.getSession().getAttribute("UserName");

		String[] arrHead = bsHead.split(",");
		//工作提醒类型
		String msgType = SystemConstant.MSG_GZTX;
		String sendTime = SystemShare.GetNowTime("yyyy-MM-dd HH:mm:ss");
		String checkTime = SystemShare.GetNowTime("yyyy-MM-dd");
		String isHandle = "0";
		String isNotify = "0";
		
		boolean result = false;
	
	//	String sql = "insert into TB_CHECKINFO(REPORTID,PREADVICE,BSHEAD,CHECKNAME,CHECKTIME) values('" + reportID + "','" + preAdvice + "','" + bsHead + "', '" + checkName + "','" + checkTime + "')";
		String sql = "update TB_CHECKINFO set BSHEAD=?,CHECKTIME=? where REPORTID=?";
		String[] params = new String[]{bsHead, checkTime ,reportID};
		result = db.insertItem(sql, params);
		String loginName=db.querySingleData("TB_REPORTINFO", "OFFICER", "REPORTID", reportID);
		String officer=db.querySingleData("SYS_USER", "USERNAME", "LOGINNAME", loginName);
		if(result)
		{
			//将事件状态修改为“已初步核实”
		//	sql = "update TB_REPORTINFO set STATUS='" + SystemConstant.SS_CHECKEVENT + "',LASTTIME='" + checkTime + "',FACULTY='" + faculty + "' where REPORTID='"+ reportID + "'";
			sql = "update TB_REPORTINFO set STATUS=?, LASTTIME=? where REPORTID=?";
			params = new String[]{SystemConstant.SS_CHECKEVENT, checkTime, reportID};
			result = db.insertItem(sql, params);
			//插入到消息提醒数据库表中
			try
			{
				result = db.saveMsgNotify(userName, arrHead, reportID, sendTime, msgType, isHandle, isNotify,officer);
				//插入处理过程到数据库中
				//String describe = checkTime + "," + checkName + "提交初步核实意见,核实意见详情请查看《审核信息》一栏";
				//db.InsertHandleProcess(reportID, checkName, SystemConstant.HP_CHECKEVENT, SystemConstant.SS_CHECKEVENT,SystemConstant.LCT_DCHS, describe);
				//插入反馈信息到门户网站数据库中，便于实名举报用户利用查询码查询反馈信息
				//db.InsertFKInfo(reportID, SystemConstant.FK_CHECKEVENT, checkTime);
				//写入日志文件
				db.insertLogInfo(userName, SystemConstant.LOG_SBLD, "受理阶段上报领导，事件编号为：" + reportID, request.getRemoteAddr());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		if(result)
		{
			json.put("statusCode", 200);
			json.put("message", "上报领导成功");
			json.put("callbackType", "closeCurrent");
		}
		else
		{
			json.put("statusCode", 300);
			json.put("message", "上报领导失败");
		}
		out.write(json.toString());
		out.flush();
		out.close();
		return null;
	}
}