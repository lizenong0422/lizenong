package com.whu.web.email;

public class EmailBean {
	//���
	private String ID;
	//�˻�����
	private String accountName;
	//��������:pop3���͡�
	private String mailBoxType;
	//�����ַ
	private String mailBoxAddress;
	//��������
	private String mailBoxPwd;
	//smtp����
	private String smtpPC;
	//smtp�˿�
	private String smtpPort;
	//pop����
	private String popPC;
	//pop�˿�
	private String popPort;
	//�Ƿ�Ĭ��
	private String isDefault;
	public String getID() {
		return ID;
	}
	public void setID(String id) {
		ID = id;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getMailBoxType() {
		return mailBoxType;
	}
	public void setMailBoxType(String mailBoxType) {
		this.mailBoxType = mailBoxType;
	}
	public String getMailBoxAddress() {
		return mailBoxAddress;
	}
	public void setMailBoxAddress(String mailBoxAddress) {
		this.mailBoxAddress = mailBoxAddress;
	}
	public String getMailBoxPwd() {
		return mailBoxPwd;
	}
	public void setMailBoxPwd(String mailBoxPwd) {
		this.mailBoxPwd = mailBoxPwd;
	}
	public String getSmtpPC() {
		return smtpPC;
	}
	public void setSmtpPC(String smtpPC) {
		this.smtpPC = smtpPC;
	}
	public String getSmtpPort() {
		return smtpPort;
	}
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}
	public String getPopPC() {
		return popPC;
	}
	public void setPopPC(String popPC) {
		this.popPC = popPC;
	}
	public String getPopPort() {
		return popPort;
	}
	public void setPopPort(String popPort) {
		this.popPort = popPort;
	}
}
