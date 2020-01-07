<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.whu.web.event.EventBean" %>
<%@ page import="com.whu.web.event.BeReportBean" %>
<%@ page import="java.util.ArrayList" %>
<%
EventBean eventBean = (EventBean)request.getAttribute("EventBean");
String reportID = (String)request.getAttribute("ReportID");
String serverPath = (String)request.getAttribute("ServerPath");
String templatePath = (String)request.getAttribute("templatePath");
String reportName = eventBean.getReportName();
String deptName = eventBean.getDept();
String email = eventBean.getMailAddress();
String gdPhone = eventBean.getGdPhone();
String telPhone = eventBean.getTelPhone();
String reportTime = eventBean.getReportTime();
String reportType = eventBean.getReportType();
String serialNum = eventBean.getSerialNum();
String faculty = eventBean.getFaculty();
String reportReason = eventBean.getReportReason();
String reportContent = eventBean.getReportContent();
String bz = eventBean.getBz();
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id = "1";
%>
<html>
	<head>
		<title>案件信息</title>
		<script type="text/javascript" src="<%=path %>/web/dsoframer/dsoframer.js"></script>
		<script type="text/javascript">
             var word = new word();
             //决定路径
		     word.setUploadUrl("<%=basePath%>" + "/web/dsoframer/upload_handle.jsp");
		  
		     function load(){
		         	word.openDoc('sjxq.doc',"<%=basePath%>" + "<%=templatePath%>");
		         	setFileVal();
		         }
		     //
		     function unload(){
		         word.close();
		     }
		     
		     function setFileVal(){
		     	var reportReason = document.getElementById("reasonID").value;
		     	var reportContent = document.getElementById("contentID").value;
		     	var bz = document.getElementById("bzID").value;
   			document.getElementById('oframe').SetFieldValue("reportID","<%=reportID%>","");	
		     	document.getElementById('oframe').SetFieldValue("reportName","<%=reportName%>","");	
		     	document.getElementById('oframe').SetFieldValue("serialNum","<%=serialNum%>","");
		     	document.getElementById('oframe').SetFieldValue("deptName","<%=deptName%>","");	
		     	document.getElementById('oframe').SetFieldValue("gdPhone","<%=gdPhone%>","");	
		     	document.getElementById('oframe').SetFieldValue("email","<%=email%>","");
		     	document.getElementById('oframe').SetFieldValue("telPhone","<%=telPhone%>","");
		     	document.getElementById('oframe').SetFieldValue("reportTime","<%=reportTime%>","");	
		     	document.getElementById('oframe').SetFieldValue("reportType","<%=reportType%>","");
		     	
		     	
		     	<%
		     	ArrayList list = (ArrayList)eventBean.getBeReportList();
		     	for(int i=0;list != null && i<list.size();i++){
		     		BeReportBean brb = (BeReportBean)list.get(i);
		     	%>
		     		document.getElementById('oframe').SetFieldValue("beReportName"+"<%=i+1%>","<%=brb.getBeName()%>","");
		     		document.getElementById('oframe').SetFieldValue("beDeptName"+"<%=i+1%>","<%=brb.getBeDept()%>","");
		     		document.getElementById('oframe').SetFieldValue("title"+"<%=i+1%>","<%=brb.getBePosition()%>","");
		     		document.getElementById('oframe').SetFieldValue("beTelPhone"+"<%=i+1%>","<%=brb.getBeTelPhone()%>","");
		     	<%
		     	}
		     	%>
		     	
		     	
		     	document.getElementById('oframe').SetFieldValue("faculty","<%=faculty%>","");
		     	document.getElementById('oframe').SetFieldValue("jbReason",reportReason,"");
		     	document.getElementById('oframe').SetFieldValue("jbContent",reportContent,"");	
		     	document.getElementById('oframe').SetFieldValue("bz",bz,"");
		     }
		   //重调文档
		     function reloadFile_p()
		     {
		     	load();
		     }
		</script>
	</head>
	<body onload="load();" onunload="unload();">
	<form action="" method="post">
		<input type="hidden" id="reasonID" name="reasonID" value="<%=reportReason %>"/>
		<input type="hidden" id="contentID" name="content" value="<%=reportContent %>"/>
		<input type="hidden" id="bzID" name="bz" value="<%=bz %>"/>
		<input type="button" value="重调文档" onclick="reloadFile_p()" >
      <object classid="clsid:00460182-9E5E-11d5-B7C8-B8269041DD57" codebase="dsoframer.CAB#Version=2.3.0.0" id="oframe" width="100%" height="100%">
	         <param name="BorderStyle" value="1">
	         <param name="TitleBar" value="0">
       </object> 
       </form>
	</body>
</html>