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
 * @struts.form name="mailConfigForm"
 */
public class MailConfigForm extends ActionForm {
	/*
	 * Generated fields
	 */

	/** smtpPort property */
	private String smtpPort;

	/** mailBoxAddress property */
	private String mailBoxAddress;

	/** mailBoxType property */
	private String mailBoxType;

	/** mailBoxPwd property */
	private String mailBoxPwd;

	/** smtpPC property */
	private String smtpPC;

	/** accountName property */
	private String accountName;

	/** popPort property */
	private String popPort;

	/** popPC property */
	private String popPC;
	
	//根据编号查询出具体的邮箱配置信息
	private List recordList = null;
	//用于判断是添加一个新的邮箱账户，还是对已存在的信息进行编辑；新增时，没有初始值，编辑时有相应的信息
	private String isEdit = "false";

	/*
	 * Generated Methods
	 */

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public List getRecordList() {
		return recordList;
	}

	public void setRecordList(List recordList) {
		this.recordList = recordList;
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
	 * Returns the smtpPort.
	 * @return String
	 */
	public String getSmtpPort() {
		return smtpPort;
	}

	/** 
	 * Set the smtpPort.
	 * @param smtpPort The smtpPort to set
	 */
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	/** 
	 * Returns the mailBoxAddress.
	 * @return String
	 */
	public String getMailBoxAddress() {
		return mailBoxAddress;
	}

	/** 
	 * Set the mailBoxAddress.
	 * @param mailBoxAddress The mailBoxAddress to set
	 */
	public void setMailBoxAddress(String mailBoxAddress) {
		this.mailBoxAddress = mailBoxAddress;
	}

	/** 
	 * Returns the mailBoxType.
	 * @return String
	 */
	public String getMailBoxType() {
		return mailBoxType;
	}

	/** 
	 * Set the mailBoxType.
	 * @param mailBoxType The mailBoxType to set
	 */
	public void setMailBoxType(String mailBoxType) {
		this.mailBoxType = mailBoxType;
	}

	/** 
	 * Returns the mailBoxPwd.
	 * @return String
	 */
	public String getMailBoxPwd() {
		return mailBoxPwd;
	}

	/** 
	 * Set the mailBoxPwd.
	 * @param mailBoxPwd The mailBoxPwd to set
	 */
	public void setMailBoxPwd(String mailBoxPwd) {
		this.mailBoxPwd = mailBoxPwd;
	}

	/** 
	 * Returns the smtpPC.
	 * @return String
	 */
	public String getSmtpPC() {
		return smtpPC;
	}

	/** 
	 * Set the smtpPC.
	 * @param smtpPC The smtpPC to set
	 */
	public void setSmtpPC(String smtpPC) {
		this.smtpPC = smtpPC;
	}

	/** 
	 * Returns the accountName.
	 * @return String
	 */
	public String getAccountName() {
		return accountName;
	}

	/** 
	 * Set the accountName.
	 * @param accountName The accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/** 
	 * Returns the popPort.
	 * @return String
	 */
	public String getPopPort() {
		return popPort;
	}

	/** 
	 * Set the popPort.
	 * @param popPort The popPort to set
	 */
	public void setPopPort(String popPort) {
		this.popPort = popPort;
	}

	/** 
	 * Returns the popPC.
	 * @return String
	 */
	public String getPopPC() {
		return popPC;
	}

	/** 
	 * Set the popPC.
	 * @param popPC The popPC to set
	 */
	public void setPopPC(String popPC) {
		this.popPC = popPC;
	}
}