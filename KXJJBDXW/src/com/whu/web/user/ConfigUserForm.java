/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.whu.web.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 01-07-2014
 * 
 * XDoclet definition:
 * @struts.form name="configUserForm"
 */
public class ConfigUserForm extends ActionForm {
	/*
	 * Generated Methods
	 */
	private String id;
	//账号
	private String loginName;
	//姓名
	private String userName;
	//性别
	private String sex;
	//邮箱地址
	private String mailAddress;
	//手机号码
	private String telPhone;
	//办公电话
	private String bgPhone;
	//办公室号
	private String bgsNum;
	//单位
	private String dept;
	private String isHead;
	
	public String getIsHead() {
		return isHead;
	}

	public void setIsHead(String isHead) {
		this.isHead = isHead;
	}

	private String recordNotFind = "false";
	private List recordList = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecordNotFind() {
		return recordNotFind;
	}

	public void setRecordNotFind(String recordNotFind) {
		this.recordNotFind = recordNotFind;
	}

	public List getRecordList() {
		return recordList;
	}

	public void setRecordList(List recordList) {
		this.recordList = recordList;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getBgPhone() {
		return bgPhone;
	}

	public void setBgPhone(String bgPhone) {
		this.bgPhone = bgPhone;
	}

	public String getBgsNum() {
		return bgsNum;
	}

	public void setBgsNum(String bgsNum) {
		this.bgsNum = bgsNum;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
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
}