/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.whu.web.email;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 07-02-2013
 * 
 * XDoclet definition:
 * @struts.form name="newMailForm"
 */
public class NewMailForm extends ActionForm {
	/*
	 * Generated fields
	 */

	/** 邮件内容 property */
	private String content;

	/** 抄送人姓名 property */
	private String csName;

	/** 邮件主题 property */
	private String title;

	/** 邮件附件 property */
	private String accessory;

	/** 发送人姓名 property */
	private String sendName;

	/** 收件人姓名 property */
	private String recvName;
	
	//已经配置的账号列表
	private List sendNameList;
	
	//是否查询到记录
	private String recordNotFind = "false";
	

	/*
	 * Generated Methods
	 */

	public String getRecordNotFind() {
		return recordNotFind;
	}

	public void setRecordNotFind(String recordNotFind) {
		this.recordNotFind = recordNotFind;
	}

	public List getSendNameList() {
		return sendNameList;
	}

	public void setSendNameList(List sendNameList) {
		this.sendNameList = sendNameList;
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
	}

	/** 
	 * Returns the content.
	 * @return String
	 */
	public String getContent() {
		return content;
	}

	/** 
	 * Set the content.
	 * @param content The content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/** 
	 * Returns the csName.
	 * @return String
	 */
	public String getCsName() {
		return csName;
	}

	/** 
	 * Set the csName.
	 * @param csName The csName to set
	 */
	public void setCsName(String csName) {
		this.csName = csName;
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
	 * Returns the accessory.
	 * @return String
	 */
	public String getAccessory() {
		return accessory;
	}

	/** 
	 * Set the accessory.
	 * @param accessory The accessory to set
	 */
	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	/** 
	 * Returns the sendName.
	 * @return String
	 */
	public String getSendName() {
		return sendName;
	}

	/** 
	 * Set the sendName.
	 * @param sendName The sendName to set
	 */
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	/** 
	 * Returns the recvName.
	 * @return String
	 */
	public String getRecvName() {
		return recvName;
	}

	/** 
	 * Set the recvName.
	 * @param recvName The recvName to set
	 */
	public void setRecvName(String recvName) {
		this.recvName = recvName;
	}
}