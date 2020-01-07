<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<script type="text/javascript">
function merge(){
	var caseserialnum = document.getElementById("caseSerialnum").value;
	var gb = document.getElementById("gb");
	$.ajax({
		type:"GET",
		dataType:"text",
		url:"<%=path%>/wsjbManageAction.do?method=merge&id=" + caseserialnum,
		success:function(data){
			if(data ==  "false"){
				alert("合并失败,请重新填写编号!");
			}
			else{
				gb.click();
				alert("合并成功!");
				
			}
		}
	});
	document.getElementById("refreshwsjb").click();
};
function refreshwsjb(){
	document.getElementById("refreshwsjb").click();
}

</script>
<div class="pageContent">
	<form method = "post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">	

		<div class="pageFormContent" style="text-align:center">
			<dl class="content">
				<dt>请输入合并案件编号：</dt>
				<dd><input id="caseSerialnum" name="caseSerialnum" class="required" type="text" size="30" value=""/></dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div id="mergebutton" class="buttonActive"><div class="buttonContent"><button type="button" onclick="merge()">合并</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button id="gb" type="button" class="close" onclick="refreshwsjb()">关闭</button></div></div></li>
			   <a id="refreshwsjb" href="<%=path%>/wsjbManageAction.do?method=init" target="navTab" rel="wsjb"></a>
			</ul>
		</div>
	</form>
</div>