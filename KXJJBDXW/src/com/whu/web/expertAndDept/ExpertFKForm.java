/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.whu.web.expertAndDept;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

/** 
 * MyEclipse Struts
 * Creation date: 05-14-2014
 * 
 * XDoclet definition:
 * @struts.form name="expertFKForm"
 */
public class ExpertFKForm extends ValidatorForm {
	/*
	 * Generated Methods
	 */
	private String recordNotFind = "false";
	private List recordList = null;
	private String fileName;
	private FormFile file;
	private String fileExtFlag;
	private String jdConCount;
	private String jdAdvice;
	private String jdID;
	private String reportID;
	private String adviceID;
	public String getReportID() {
		return reportID;
	}

	public void setReportID(String reportID) {
		this.reportID = reportID;
	}
	
	public String getJdID() {
		return jdID;
	}

	public void setJdID(String jdID) {
		this.jdID = jdID;
	}

	public String getAdviceID() {
		return adviceID;
	}

	public void setAdviceID(String adviceID) {
		this.adviceID = adviceID;
	}

	public String getJdConCount() {
		return jdConCount;
	}

	public void setJdConCount(String jdConCount) {
		this.jdConCount = jdConCount;
	}

	public String getJdAdvice() {
		return jdAdvice;
	}

	public void setJdAdvice(String jdAdvice) {
		this.jdAdvice = jdAdvice;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	public String getFileExtFlag() {
		return fileExtFlag;
	}

	public void setFileExtFlag(String fileExtFlag) {
		this.fileExtFlag = fileExtFlag;
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