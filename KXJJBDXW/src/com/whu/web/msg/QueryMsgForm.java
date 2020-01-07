/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.whu.web.msg;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.whu.tools.HtmlValueNameBean;

/** 
 * MyEclipse Struts
 * Creation date: 07-01-2013
 * 
 * XDoclet definition:
 * @struts.form name="queryMsgForm"
 */
public class QueryMsgForm extends ActionForm {
	/*
	 * Generated fields
	 */

	/** revEndTime property */
	private String revEndTime;

	/** title property */
	private String title;

	/** revBeginTime property */
	private String revBeginTime;

	/** msgType property */
	private String msgType;
	//�Ƿ��ѯ����¼
	private String recordNotFind = "false";
	
	private String operation;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	private List recordList = null;
	
	public List getRecordList() {
		return recordList;
	}

	public void setRecordList(List recordList) {
		this.recordList = recordList;
	}

	public String getRecordNotFind() {
		return recordNotFind;
	}

	public void setRecordNotFind(String recordNotFind) {
		this.recordNotFind = recordNotFind;
	}

	private List typeList;

	/*
	 * Generated Methods
	 */

	public List getTypeList() {
		return typeList;
	}

	public void setTypeList(List typeList) {
		this.typeList = typeList;
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		typeList = new ArrayList();
		recordList = new ArrayList();
		for(int i = 0; i < 1; i++)
			typeList.add(new HtmlValueNameBean("", ""));
	}

	/** 
	 * Returns the revEndTime.
	 * @return String
	 */
	public String getRevEndTime() {
		return revEndTime;
	}

	/** 
	 * Set the revEndTime.
	 * @param revEndTime The revEndTime to set
	 */
	public void setRevEndTime(String revEndTime) {
		this.revEndTime = revEndTime;
	}

	/** 
	 * Returns the title.
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/** 
	 * Set the title.
	 * @param title The title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 
	 * Returns the revBeginTime.
	 * @return String
	 */
	public String getRevBeginTime() {
		return revBeginTime;
	}

	/** 
	 * Set the revBeginTime.
	 * @param revBeginTime The revBeginTime to set
	 */
	public void setRevBeginTime(String revBeginTime) {
		this.revBeginTime = revBeginTime;
	}

	/** 
	 * Returns the msgType.
	 * @return String
	 */
	public String getMsgType() {
		return msgType;
	}

	/** 
	 * Set the msgType.
	 * @param msgType The msgType to set
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
}