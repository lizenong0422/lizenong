/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.whu.web.expertAndDept;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.whu.tools.DBTools;
import com.whu.web.eventbean.ExpertInfo;
import com.whu.web.eventbean.JDYJSBean;

/** 
 * MyEclipse Struts
 * Creation date: 04-13-2015
 * 
 * XDoclet definition:
 * @struts.action path="expertFKManageAction" name="expertFKManageForm" parameter="method" scope="request" validate="true"
 */
public class ExpertFKManageAction extends DispatchAction {
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
	 * @throws UnsupportedEncodingException 
	 */
	public ActionForward expertInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		ExpertFKManageForm expertFKForm = (ExpertFKManageForm) form;
		String loginName = (String)request.getSession().getAttribute("LoginName");
		String sql = "select a.* from SYS_EXPERTINFO a, SYS_ED_USER b where b.LOGINNAME=? and b.EXPERTID=a.ID";
		DBTools dbTools = new DBTools();
		ExpertInfo expertInfo = dbTools.queryExpertInfo(sql, new String[]{loginName});
		ArrayList result = new ArrayList();
		if(expertInfo!=null)
		{
			result.add(expertInfo);
			expertFKForm.setRecordNotFind("false");
			expertFKForm.setRecordList(result);
			return mapping.findForward("expertInfo");
		}
		else
		{
			expertFKForm.setRecordNotFind("true");
			return mapping.findForward("initError");
		}
	}
	/**
	 * 查询待鉴定案件列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward eventList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ExpertFKManageForm expertFKForm = (ExpertFKManageForm)form;
		String loginName = (String)request.getSession().getAttribute("LoginName");
		String sql = "select a.*, b.STATUS from TB_ED_ADVICE a, TB_REPORTINFO b where a.LOGINNAME=? and a.REPORTID=b.REPORTID";
		DBTools db = new DBTools();
		ArrayList result = db.queryExpertJDList(sql, new String[]{loginName});
		if(result.size() > 0)
		{
			ArrayList tempList = new ArrayList();
			for(int i = 0; i < result.size(); i++)
			{
				ExpertIdentityBean eb = (ExpertIdentityBean)result.get(i);
				String reportID = eb.getReportID();
				sql = "select * from TB_EXPERTFILE where REPORTID=?";
				ExpertFile ef = db.queryExpertFile(sql, new String[]{reportID});
				eb.setLetterPath(ef.getJdhPath());
				eb.setAdviceLetterPath(ef.getYjsPath());
				
				sql = "select * from TB_JDYJSINFO where REPORTID=?";
				JDYJSBean jb = db.queryJDYJS(sql, new String[]{reportID});
				if(jb!=null)
				{
					eb.setEventReason(jb.getEventReason());
					eb.setJdContent(jb.getIdentifyContent());
					eb.setWtDept(jb.getWtDept());
				}
				tempList.add(eb);
			}
			expertFKForm.setRecordNotFind("false");
			expertFKForm.setRecordList(tempList);
			request.setAttribute("totalRows",String.valueOf(result.size()));
		}
		else
		{
			expertFKForm.setRecordNotFind("true");
			request.setAttribute("totalRows",String.valueOf(0));
		}
		return mapping.findForward("expertJDList");
	}
	/**
	 * 跳转到在线提交鉴定结论页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public ActionForward onlineSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		ExpertFKManageForm expertFKForm = (ExpertFKManageForm)form;
		String id = request.getParameter("id");
		String reportID = request.getParameter("reportID");
		String adviceID = request.getParameter("adviceID");
		DBTools dbTools = new DBTools();
		String sql = "select a.*,b.CONCLUSION,b.ADVICE,b.ATTACHNAME from TB_JDYJSINFO a,TB_EXPERTADVICE b where a.REPORTID=? and b.ID=? and a.REPORTID=b.REPORTID";
		JDYJSBean jb = dbTools.queryExpertFK(sql, new String[]{reportID, adviceID});
		int count = 0;
		if(jb != null)
		{
			
			String jdConclusion = jb.getJdConclusion();
			if(jdConclusion != null && !jdConclusion.equals(""))
			{
				String[] jdConArr = jdConclusion.split("\n");
				UrlAndName uan;
				String conclusion = jb.getConclusion();
				int n = jdConArr.length;
				String[] tempCon = new String[n];
				for(int i = 0; i < n; i++)
				{
					tempCon[i] = "不确定";
				}
				//如果专家已经提交过信息，那么应该把专家已经选择的鉴定结论“是”、“否”、“不确定”返回给用户，供用户编辑
				if(conclusion != null && !conclusion.equals(""))
				{
					tempCon = conclusion.split(",");
				}
				ArrayList tempList = new ArrayList();
				for(int i = 0; i < jdConArr.length; i++)
				{
					uan = new UrlAndName();
					uan.setId(String.valueOf(i));
					uan.setName(jdConArr[i]);
					uan.setIsCheck(tempCon[i]);
					tempList.add(uan);
					count++;
				}
				jb.setJdConclusionList(tempList);
			}
		}
		request.setAttribute("jdID", id);
		request.setAttribute("reportID", reportID);
		request.setAttribute("adviceID", adviceID);
		request.setAttribute("jdConCount", String.valueOf(count));
		ArrayList resultList = new ArrayList();
		resultList.add(jb);
		expertFKForm.setRecordList(resultList);
		
		
		// if submit, cannot edit separate in expertAdviceFk.jsp
		String loginName = (String)request.getSession().getAttribute("LoginName");
		String isSubmit = dbTools.querySingleData("TB_ED_ADVICE", "ISSUBMIT", "LOGINNAME", loginName);
		request.setAttribute("isSubmit", isSubmit);
		
		return mapping.findForward("onlineSubmit");
	}
}