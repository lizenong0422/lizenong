<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
//得到记录的个数，用于确定div的高度
int size = (Integer)request.getAttribute("size");
int divHeight=25;
int totalHeight=480;

int topHeight=size*divHeight;
int contentHeight = totalHeight - topHeight;

String height= String.valueOf(contentHeight) + "px";
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="pageContent" layoutH="56" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<div class="accordion" style="width:99%;">
		<logic:notEqual value="true" name="eventDetailForm" property="recordNotFind">
			<logic:notEmpty name="eventDetailForm" property="recordList">
	     		<logic:iterate name="eventDetailForm" property="recordList" id="TreatmentSuggestion">
				<div class="accordionHeader">
					<h2>办公人员：<font color="#ff0000">${TreatmentSuggestion.workername }</font>，时间：${TreatmentSuggestion.time }</h2>
				</div>
				<div class="accordionContent" style="height:<%=height %>">
					<br/>
					<h2>处理建议：</h2>
					<p style="line-height:20px;">${TreatmentSuggestion.content }</p>
					<br/>
					<br/>
					<h2>下载附件：</h2>
					<p style="line-height:20px;">
						<logic:notEqual value="" name="TreatmentSuggestion" property="attachname">
							<a href="${TreatmentSuggestion.attachname }"><font color="blue">下载</font></a>
						</logic:notEqual>
						<logic:equal value="" name="TreatmentSuggestion" property="attachname">
							无
						</logic:equal>
					</p>
				</div>
				</logic:iterate>
			</logic:notEmpty>
		</logic:notEqual>
		<logic:notEqual value="false" name="eventDetailForm" property="recordNotFind">
			<div class="accordionHeader">
				<h2>无处理建议</h2>
			</div>
			<div class="accordionContent" style="height:200px">
				
			</div>
		</logic:notEqual>
	</div>
</div>