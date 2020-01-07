<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  
                + request.getServerName() + ":" + request.getServerPort()  
                + path + "/";
%> 
<script type="text/javascript">

function makeSJYBD()
{
	var ybdId = document.getElementById("ybdID").value;
	if(ybdId == null || ybdId == "")
	{
		alert("请先编辑内容并保存成功后，再生成收件阅办单！");
	}
	else
	{
		var reportID = document.getElementById("reportID").value;
		var url = "<%=path%>/sjybdManageAction.do?method=makeSJYBD&reportID=" + reportID;
		openMaxWin(url);
	}
}

function refreshwsjb()
{
	alert("关闭之后，阅办单需要到初核模块生成！");
	document.getElementById("refreshwsjb").click();
}
function setYear(sy){
	var select = document.getElementById("year");
	var button = document.getElementById("sure");
	var text = document.getElementById("defserialnum");
	var autoserialnum = document.getElementById("autoserialNum").value;
	if(sy.checked == true){
		document.getElementById("serialNum").readOnly=true;
		document.getElementById("serialNum").style.backgroundColor = "lightsteelblue";
		select.style.display = "block";
		text.style.display = "block";
		button.style.display = "block";
		var myDate = new Date();
		var startYear = myDate.getFullYear()-10;
		var endYear = myDate.getFullYear();
		if(select.value===""){
			for(var i = startYear; i <= endYear; i++)
			{
				select.options.add(new Option(i,i));
			}
		}
	}
	else{
		select.style.display = "none";
		text.style.display = "none";
		button.style.display = "none";
		//document.getElementById("serialNum").readOnly=false;
		//document.getElementById("serialNum").style.backgroundColor = "white";
		//document.getElementById("serialNum").value = autoserialnum;
		
	}
	
}
function confirm(){
	var select = document.getElementById("year");
	var setyear = select.options[select.selectedIndex].value;
	var defserialnum = document.getElementById("defserialnum").value;
	var Userialnum = setyear+defserialnum;
	var reportid = document.getElementById("reportID").value;
	//console.log(reportid);
	document.getElementById("serialNum").value = Userialnum;
	$.ajax({
		type:"GET",
		dataType:"text",
		url:"<%=path%>/wsjbManageAction.do?method=changeSerialnum&RID=" + reportid +"&USnum=" + Userialnum,
		success:function(data){
			if(data ==  "false"){
				alert("修改编号失败");
			}
			else{
				alert("修改编号成功");
				
			}
		}
	});
}

</script>
<div class="pageContent">
	<form method="post" action="<%=path%>/sjybdManageAction.do?method=recvYBD" target="_blank" class="pageForm required-validate" >
		<div class="pageFormContent" layoutH="56">
			<logic:notEmpty name="wsjbManageForm" property="recordList">
     		<logic:iterate name="wsjbManageForm" property="recordList" id="SjybdBean">
     		<input type="hidden" name="reportID" id="reportID" value="${SjybdBean.reportID }"/>
     		<input type="hidden" name="ybdID" id="ybdID" value="${SjybdBean.id }"/>
     		<input type="hidden" name="autoserialNum" id="autoserialNum" value="${SjybdBean.serialNum }"/>
			<dl class="nowrap">
				<dt>编号：</dt>
				<dd>
					<input id="serialNum" class="requird" name="serialNum" type="text" size="20" value='${SjybdBean.serialNum }'/>
					<select id="year" style="display:none"></select>
					<input id="defserialnum" name="defserialnum" type="text" size="10" minlength="1" maxlength="6" style="display:none"/>
					<button id="sure" type="button" style="display:none" onclick="confirm()">确定</button>
					<input type="checkbox" name="choose" value="5" onclick="setYear(this);"/>自定义案件编号
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>来件单位：</dt>
				<dd>
					<input class="requird" name="comeName" type="text" size="50" value='${SjybdBean.comeName }'/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>收件日期：</dt>
				<dd>
					<input type="text" name="recvTime" class="required date" size="20" readonly value="${SjybdBean.recvTime }"/><a class="inputDateButton" href="javascript:;">选择</a>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>来件标题：</dt>
				<dd>
					<textarea class="requird" rows="5" cols="60" name="title">${SjybdBean.title }</textarea>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>拟办意见：</dt>
				<dd>
					<textarea class="requird" rows="5" cols="60" name="proposedOpinion">${SjybdBean.proposedOpinion }</textarea>
				</dd>
			</dl>
			</logic:iterate>
			</logic:notEmpty>
			</div>
			<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">生成阅办单</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close" onclick="refreshwsjb()">关闭</button></div></div></li>
				<a id="refreshwsjb" href="<%=path%>/wsjbManageAction.do?method=init" target="navTab" rel="wsjb"></a>
			</ul>
			</div>
	</form>
</div>
		