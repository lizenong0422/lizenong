package com.whu.web.msg;

public class SendMsgBean {

	//����
	private String title;
	//��Ϣ����
	private String msgType;
	//����ʱ��
	private String sendTime;
	//��Ҫ�ظ�
	private String needBack;
	//������
	private String recvName;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getNeedBack() {
		return needBack;
	}
	public void setNeedBack(String needBack) {
		this.needBack = needBack;
	}
	public String getRecvName() {
		return recvName;
	}
	public void setRecvName(String recvName) {
		this.recvName = recvName;
	}
	
	
}
