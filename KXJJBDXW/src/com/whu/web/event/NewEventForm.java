/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.whu.web.event;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 07-24-2013
 * 
 * XDoclet definition:
 * @struts.form name="newEventForm"
 */
public class NewEventForm extends ActionForm {
	/*
	 * Generated Methods
	 */
	//举报ID
	private String reportID;
	//举报人姓名
	private String reportName;
	//编号
	private String serialNum;
	//办公电话
	private String gdPhone;
	//邮箱地址
	private String mailAddress;
	//联系方式
	private String telPhone;
	//举报时间
	private String reportTime;
	//举报方式
	private String reportType;
	//举报人所属单位
	private String dept;
	// 被举报人列表
	private List beReport;
	//举报事由
	private String reportReason;
	//举报内容
	private String reportContent;
	//BZ
	private String BZ;
	public String getGdPhone() {
		return gdPhone;
	}

	public void setGdPhone(String gdPhone) {
		this.gdPhone = gdPhone;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public List getBeReport() {
		return beReport;
	}

	public void setBeReport(List beReport) {
		this.beReport = beReport;
	}

	public String getReportReason() {
		return reportReason;
	}

	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportID() {
		return reportID;
	}

	public void setReportID(String reportID) {
		this.reportID = reportID;
	}

	public String getBZ() {
		return BZ;
	}

	public void setBZ(String bZ) {
		BZ = bZ;
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