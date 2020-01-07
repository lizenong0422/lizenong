/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.whu.web.eventmanage;

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
import com.whu.tools.SystemConstant;
import com.whu.web.common.SystemShare;

/** 
 * MyEclipse Struts
 * Creation date: 01-06-2014
 * 
 * XDoclet definition:
 * @struts.action path="/lookUpGroupAction" name="lookUpGroupForm" parameter="method" scope="request" validate="true"
 */
public class LookUpGroupAction extends DispatchAction {
	/*
	 * Generated Methods
	 */

	/**
	 * "选择带回"功能通用Action
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LookUpGroupForm lookUpGroupForm = (LookUpGroupForm) form;
		String type = request.getParameter("type");
		String sql = "";
		String[] params = new String[0];
		if(type.equals("jbsy"))//举报事由
		{
			sql = "select * from SYS_JBREASON";
		}
		else if(type.equals("bsld"))//报送领导
		{
			sql = "select a.*,b.ZZNAME from SYS_USER a,SYS_ZZINFO b where a.ZZID=b.ZZID and a.ISHEAD='1' and (a.ZZID<2000 OR a.ZZID=2006)";
		}
		else if(type.equals("hymc"))//会议名称
		{
			sql = "select * from TB_CONFERENCE";
		}
		else if(type.equals("sszz"))//所属组织
		{
			sql = "select a.*,b.ZZNAME as PZZNAME from SYS_ZZINFO a, SYS_ZZINFO b where a.PZZID=b.ZZID";
		}
		else if(type.equals("role"))//角色
		{
			sql = "select * from SYS_ROLE";
		}
		else if(type.equals("pos"))//职务
		{
			sql = "select * from SYS_POSITION";
		}
		else if(type.equals("contact"))//邮箱通讯录
		{
			String loginName = (String)request.getSession().getAttribute("LoginName");
			sql = "select ID,CONNAME,CONADDR from TB_CONTACT where LOGINNAME=? or LOGINNAME='expert' or LOGINNAME='committee'";
			params = new String[]{loginName};
		}
		else if(type.equals("expert"))//鉴定专家
		{
			sql = "select * from SYS_EXPERTINFO";
		}
		else if(type.equals("cljdlook"))//处理决定
		{
			sql = "select * from SYS_DATA_DIC where CODENAME=? order by CODE asc";
			params = new String[]{SystemConstant.cljd};
		}
		else if(type.equals("wylook"))//委员信息
		{
			sql = "select * from TB_WYINFO";
		}
		else if(type.equals("faculty")) // faculty
		{
			sql = "select a.*,b.ZZNAME as PZZNAME from SYS_ZZINFO a, SYS_ZZINFO b where a.ZZID like '3%' and a.PZZID=b.ZZID";
		}
		CheckPage pageBean = new CheckPage();
		int queryPageNo = 1;
		int rowsPerPage = 50;
		pageBean.setRowsPerPage(rowsPerPage);
		if (request.getParameter("queryPageNo") != null) {
			queryPageNo = Integer.parseInt(request.getParameter("queryPageNo"));
		}
		pageBean.setQueryPageNo(queryPageNo);
		
		request.getSession().setAttribute("query" + type + "Sql", sql);
		request.getSession().setAttribute("query" + type + "Params", params);
		pageBean.setQuerySql(sql);
		pageBean.setParams(params);
		DBTools db = new DBTools();
		ResultSet rs = db.queryRs(queryPageNo, pageBean, rowsPerPage);
		ArrayList result = new ArrayList();
		if(type.equals("jbsy"))
		{
			result = db.queryJBReList(rs, rowsPerPage);
		}
		else if(type.equals("bsld"))
		{
			result = db.querySysUser(rs, rowsPerPage);
		}
		else if(type.equals("hymc"))
		{
			result = db.queryMeetList(rs, rowsPerPage);
		}
		else if(type.equals("sszz") || type.equals("faculty"))
		{
			result = db.queryZZList(rs, rowsPerPage);
		}
		else if(type.equals("role"))
		{
			result = db.queryRoleList(rs, rowsPerPage);
		}
		else if(type.equals("pos"))
		{
			result = db.queryPosList(rs, rowsPerPage);
		}
		else if(type.equals("contact"))
		{
			result= db.queryConList(rs, rowsPerPage);
		}
		else if(type.equals("expert"))
		{
			result = db.queryExpertList(rs, rowsPerPage);
		}
		else if(type.equals("cljdlook"))
		{
			result = db.queryDicList(rs, rowsPerPage);
		}
		else if(type.equals("wylook"))
		{
			result = db.queryWYList(rs, rowsPerPage);
		}
		if(result.size() > 0)
		{
			lookUpGroupForm.setRecordNotFind("false");
			lookUpGroupForm.setRecordList(result);
			SystemShare.SplitPageFun(request, pageBean, 1);
		}
		else
		{
			lookUpGroupForm.setRecordNotFind("true");
			SystemShare.SplitPageFun(request, pageBean, 0);
		}
		return mapping.findForward(type);
	}
	public ActionForward queryMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");	
		LookUpGroupForm lookUpGroupForm = (LookUpGroupForm) form;
		String operation = request.getParameter("operation");
		
		String type = request.getParameter("type");
		CheckPage pageBean = new CheckPage();
		String sql = "";
		String[] params = new String[0];
		String temp = "";
		int queryPageNo = 1;
		int rowsPerPage = 50;
		pageBean.setRowsPerPage(rowsPerPage);
		
		if (operation.equalsIgnoreCase("search")) {
			if(type.equals("jbsy"))//举报事由
			{
				String jbObject = lookUpGroupForm.getJbObject();
				if(!jbObject.equals(""))
				{
					temp += " and PID=?";
					params = new String[]{jbObject};
				}
				sql = "select * from SYS_JBREASON where 1=1 " + temp;
			}
			else if(type.equals("bsld"))//报送领导
			{
				String userName = lookUpGroupForm.getUserName();
				if(userName != null && !userName.equals(""))
				{
					temp += " and a.USERNAME like ?";
					params = new String[]{"%" + userName + "%"};
				}
				sql = "select a.*,b.ZZNAME from SYS_USER a,SYS_ZZINFO b where a.ZZID=b.ZZID and a.ISHEAD='1' " + temp;
			}
			else if(type.equals("hymc"))//会议名称
			{
				String meetName = lookUpGroupForm.getMeetName();
				if(!meetName.equals(""))
				{
					temp += " and MEETNAME like ?";
					params = new String[]{"%" + meetName + "%"};
				}
				sql = "select * from TB_CONFERENCE where 1=1 " + temp;
			}
			else if(type.equals("sszz"))
			{
				String zzName = lookUpGroupForm.getZzName();
				if(!zzName.equals(""))
				{
					temp += " and a.ZZNAME like ?";
					params = new String[]{"%" + zzName + "%"};
				}
				sql = "select a.*,b.ZZNAME as PZZNAME from SYS_ZZINFO a, SYS_ZZINFO b where a.PZZID=b.ZZID" + temp;
			}
			else if(type.equals("role"))
			{
				String roleName = lookUpGroupForm.getRoleName();
				if(!roleName.equals(""))
				{
					temp += " and ROLENAME like ?";
					params = new String[]{"%" + roleName + "%"};
				}
				sql = "select * from SYS_ROLE where 1=1" + temp;
			}
			else if(type.equals("pos"))
			{
				String posName = lookUpGroupForm.getPosName();
				if(!posName.equals(""))
				{
					temp += " and POSNAME like '%" + posName + "%'";
					params = new String[]{"%" + posName + "%"};
				}
				sql = "select * from SYS_POSITION where 1=1" + temp;
			}
			else if(type.equals("contact"))
			{
				String contactName = lookUpGroupForm.getContactName();
				String loginName = (String)request.getSession().getAttribute("LoginName");
				params = new String[]{loginName};
				if(!contactName.equals(""))
				{
					temp += " and CONNAME like ?";
					params = new String[]{loginName, "%" + contactName + "%"};
				}
				sql = "select ID,CONNAME,CONADDR from TB_CONTACT where LOGINNAME='" + loginName + " 'or LOGINNAME='committee' or LOGINNAME='expert' " + temp;
			}
			else if(type.equals("expert"))
			{
				String expertName = lookUpGroupForm.getExpertName();
				String expertFaculty = lookUpGroupForm.getExpertFaculty();
				ArrayList<String> paramList = new ArrayList<String>();
				if(!expertName.equals(""))
				{
					temp += " and NAME like ?";
					paramList.add("%" + expertName + "%");
				}
				if(!expertFaculty.equals(""))
				{
					temp += " and FACULTY like ?";
					paramList.add("%" + expertFaculty + "%");
				}
				sql = "select * from SYS_EXPERTINFO where 1=1 " + temp;
				params = paramList.toArray(new String[0]);
			}
			else if(type.equals("cljdlook"))
			{
				String cljd = lookUpGroupForm.getCljd();
				if(!cljd.equals(""))
				{
					temp += " and CAPTION like ?";
					params = new String[]{"%" + cljd + "%"};
				}
				sql = "select * from SYS_DATA_DIC where CODENAME='" + SystemConstant.cljd + "' " + temp + " order by ID asc";
			}
			else if(type.equals("wylook"))
			{
				String wyName = lookUpGroupForm.getWyName();
				if(!wyName.equals(""))
				{
					temp += " and NAME like ?";
					params = new String[]{"%" + wyName + "%"};
				}
				sql = "select * from TB_WYINFO where 1=1 " + temp;
			}
			request.getSession().setAttribute("query" + type + "Sql", sql);
			request.getSession().setAttribute("query" + type + "params", params);
		}
		else if(operation.equalsIgnoreCase("changePage")){
			sql = (String)request.getSession().getAttribute("query" + type + "Sql");
			params = (String[])request.getSession().getAttribute("query" + type + "Params");
			if (request.getParameter("pageNum") != null && request.getParameter("pageNum") != "") {
				queryPageNo = Integer.parseInt(request.getParameter("pageNum"));
			}
		}
		pageBean.setQuerySql(sql);
		pageBean.setParams(params);
		pageBean.setQueryPageNo(queryPageNo);
		DBTools db = new DBTools();
		ResultSet rs = db.queryRs(queryPageNo, pageBean, rowsPerPage);
		ArrayList result = new ArrayList();
		if(type.equals("jbsy"))
		{
			result = db.queryJBReList(rs, rowsPerPage);
		}
		else if(type.equals("bsld"))
		{
			result = db.querySysUser(rs, rowsPerPage);
		}
		else if(type.equals("hymc"))
		{
			result = db.queryMeetList(rs, rowsPerPage);
		}
		else if(type.equals("sszz"))
		{
			result = db.queryZZList(rs, rowsPerPage);
		}
		else if(type.equals("role"))
		{
			result = db.queryRoleList(rs, rowsPerPage);
		}
		else if(type.equals("pos"))
		{
			result = db.queryPosList(rs, rowsPerPage);
		}
		else if(type.equals("contact"))
		{
			result = db.queryConList(rs, rowsPerPage);
		}
		else if(type.equals("expert"))
		{
			result = db.queryExpertList(rs, rowsPerPage);			
		}
		else if(type.equals("cljdlook"))
		{
			result = db.queryDicList(rs, rowsPerPage);
		}
		else if(type.equals("wylook"))
		{
			result = db.queryWYList(rs, rowsPerPage);
		}
		if(result.size() > 0)
		{
			lookUpGroupForm.setRecordNotFind("false");
			lookUpGroupForm.setRecordList(result);
			SystemShare.SplitPageFun(request, pageBean, 1);
		}
		else
		{
			lookUpGroupForm.setRecordNotFind("true");
			SystemShare.SplitPageFun(request, pageBean, 0);
		}
		return mapping.findForward(type);
	}
	public ActionForward sbld( ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {		
		LookUpGroupForm lookUpGroupForm = (LookUpGroupForm)form;
		CheckPage pageBean = new CheckPage();
		int queryPageNo = 1;
		int rowsPerPage = 20;
		pageBean.setRowsPerPage(rowsPerPage);
		pageBean.setQueryPageNo(queryPageNo);
		String sql = "select a.*,b.ZZNAME from SYS_USER a,SYS_ZZINFO b where a.ZZID=b.ZZID and a.ISHEAD='1' and (a.ZZID<2000 OR a.ZZID=2006)";
		String[] params = new String[0];
		pageBean.setQuerySql(sql);
		pageBean.setParams(params);
		DBTools db = new DBTools();
		ResultSet rs = db.queryRs(queryPageNo, pageBean, rowsPerPage);
		ArrayList result = db.querySysUser(rs, rowsPerPage);
		if(result.size() > 0)
		{
			lookUpGroupForm.setRecordNotFind("false");
			lookUpGroupForm.setRecordList(result);
			SystemShare.SplitPageFun(request, pageBean, 1);
		}
		else
		{
			lookUpGroupForm.setRecordNotFind("true");
			SystemShare.SplitPageFun(request, pageBean, 0);
		}
		return mapping.findForward("sbld");
	}
}