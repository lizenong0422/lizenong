<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="pageContent">
	<form method="post" action="<%=path%>/approveEventAction.do?method=approve" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input type="hidden" name="reportID" value="<%=request.getAttribute("ReportID") %>"/>
		<div class="pageFormContent" layoutH="56">
			<fieldset>
					<legend>举报内容</legend>
					<dl class="nowrap">
						<dt>事件简述：</dt>
						<dd><textarea rows="7" cols="80" readonly><%=request.getAttribute("ReportInfo") %></textarea></dd>
					</dl>
			</fieldset>
			<fieldset>
					<legend>初步核实意见</legend>
					<dl class="nowrap">
						<dt>核实意见：</dt>
						<dd><textarea rows="7" cols="80" readonly><%=request.getAttribute("CheckInfo") %></textarea></dd>
					</dl>
					<dl>
						<dt>核实人：</dt>
						<dd><input type="text" readonly size="20" name="checkName" value="<%=request.getAttribute("CheckName") %>"/></dd>
					</dl>
					<dl>
						<dt>核实时间：</dt>
						<dd><input type="text" readonly size="20" name="checkTime" value="<%=request.getAttribute("CheckTime") %>"/></dd>
					</dl>
			</fieldset>
			<fieldset>
					<legend>领导审批</legend>
					<dl class="nowrap">
						<dt>审核意见：</dt>
						<dd>
							<textarea name="laAdvice" cols="80" rows="10" class="required" value="">同意调查。</textarea>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>是否开展：</dt>
						<dd>
							<input type="radio" name="isLA" value="1" checked="true"/>调查
							<input type="radio" name="isLA" value="0"/>不调查
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>是否需要：</dt>
						<dd>
							<input type="checkbox" name="isXY" value="依托单位鉴定"/>依托单位鉴定
							<input type="checkbox" name="isXY" value="专家鉴定"/>专家鉴定
							<input type="checkbox" name="isXY" value="当事人陈述"/>当事人陈述
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>审核人：</dt>
						<dd>
							<input name="approveName" class="readonly" type="text" size="20" value="<%=(String)request.getSession().getAttribute("UserName") %>"/>
						</dd>
					</dl>
			</fieldset>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">返回</button></div></div></li>
			</ul>
		</div>
	</form>
</div>