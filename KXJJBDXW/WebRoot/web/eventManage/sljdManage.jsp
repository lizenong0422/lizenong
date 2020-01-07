<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  
                + request.getServerName() + ":" + request.getServerPort()  
                + path + "/";
%> 
<script src="<%=path%>/js/moreops.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=path %>/web/dsoframer/dsoframer.js"></script>
<script type="text/javascript">
var boxID = "sljd_box";
function showSljdDiv(aID)
 {
 	document.getElementById("sljdReportID").value = aID;
 	  var opsID = "more" + aID;
 	  var opsObject = document.getElementById(opsID);
 	  var r = getAbsolutePos(opsObject);
 	  var newDiv = document.getElementById("sljd_box"); 
	  //让新层的zIndex属性要足够大，才会在最上面显示  	  
	  //newDiv.style.zIndex = "9999";  
	  newDiv.style.top= r.y - 65 + "px";  
	 // newDiv.style.left= r.x - 400 + "px";
	  newDiv.style.display="block";
 }

	 document.getElementById("sljd_box").onclick = function(e){
	     e = e || window.event;
	     if(window.event){    //阻止事件冒泡
	         e.cancelBubble = true;
	     } else {
	         e.stopPropagation();
	     }
	 }
	 
function createSJYBD()
{
	
	var reportID = document.getElementById("sljdReportID").value;
	var url = "<%=path%>/eventManageAction.do?method=createSJYBD&id=" + reportID;
	openMaxWin(url);
}
function setAnonymity(cb)
{
	if(cb.checked==true)
	{	
		document.getElementById("informer1").value="";
		document.getElementById("informer1").disabled=true;
	}
	else
	{
		document.getElementById("informer1").value="";
		document.getElementById("informer1").disabled=false;
	}
	
}
</script>
<form id="pagerForm" method="post" action='<%=path%>/eventManageAction.do?method=queryMsg&operation=changePage&jdid=1'>
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="pageSize" value="${pageSize}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>
<div id="sljd_box" class="ops_box ops_itemDiv">
	<ul style="width:330px;">
		<!-- <li class="ops_li">
			<a href="<%=path%>/eventManageAction.do?method=detail&id={eventid}" class="link detail ops_more" target="dialog" mask="true" rel="detail" width="900" height="600" title="查看详情">查看详情</a>
		</li> -->
		<li class="ops_li">
			 <a class="link edit ops_more" href="<%=path %>/sjybdManageAction.do?method=createSJYBD&id={eventid}"  mask="true" id="sjybdDialog" target="dialog" width="750" height="420" title="收件阅办单">生成阅办单</a>
		</li>
		<li class="ops_li">
			<a class="link parameter ops_more" href="<%=path%>/eventManageAction.do?method=notRegister&id={eventid}" target="ajaxTodo" title="确定要不予立案吗?">不予立案</a>
		</li>
		<li class="ops_li">
			<a class="link parameter ops_more" href="<%=path%>/eventManageAction.do?method=tempNotRegister&id={eventid}" target="ajaxTodo" title="确定要暂不立案吗?">暂不立案</a>
		</li>
		 <li class="ops_li">
			<a class="link parameter ops_more" href="<%=path%>/eventManageAction.do?method=midCloseEvent&id={eventid}" target="ajaxTodo" title="确定要直接结束该案件的调查吗?">结案</a>
		</li>
		<li class="ops_li">
			<a class="link edit ops_more" href="<%=path%>/eventManageAction.do?method=revocation&id={eventid}" target="ajaxTodo"  title="确定对该案件进行撤回吗？">撤回</a>
		</li>
	</ul>
</div>
<div class="pageHeader">
	<html:form onsubmit="return navTabSearch(this);" action="/eventManageAction.do?method=queryMsg&operation=search&jdid=1" method="post">
	<input type="hidden" id="sljdReportID" value=""/>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					编号：<input type="text" name="serialNum"/>
				</td>
				<td>
					举报人：<input type="text" name="reportName" id="informer1"/>
					<input type="checkbox" name="isNi" value="匿名举报" onclick="setAnonymity(this)"/>匿名
				</td>
				<td>
					被举报人：<input type="text" name="beReportName"/>
				</td>
				<td>
					 举报时间：
					 <input type="text" class="date" readonly name="jbBeginTime"/>
                                                   至：
        			<input type="text" class="date" readonly name="jbEndTime"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</html:form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<!-- 
			<li><a class="delete" rel="ids" href="<%=path %>/eventManageAction.do?method=delete&type=noreal" postType="string" target="selectedTodo" title="确定要删除吗?"><span>批量删除</span></a></li>
			 -->
			<!-- 导出excel功能暂不开发
			<li class="line">line</li>
			<li><a class="icon" href="<%=path %>/eventManageAction.do?method=print&id={eventid}" mask="true" target="dialog" width="750" height="200" title="生成收件阅办单"><span>生成阅办单</span></a></li>
			<li><a class="icon" href="<%=request.getContextPath()%>/myStartEventAction.do?method=export" target="dwzExport" targetType="navTab" title="确实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
			 -->
			<li><a class="icon" href="<%=path%>/eventManageAction.do?method=export&id=1" target="dwzExport" targetType="navTab" title="确实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="40" align="center"><input type="checkbox" class="checkboxCtrl" group="ids"/></th>
				<th width="70" align="center" orderField="SERIALNUM" class="asc">编号</th>
				<th width="100" align="center" orderField="a.STATUS" class="desc">状态</th>
				<th width="100" align="center">举报人姓名</th>
				<th width="110" align="center">被举报人姓名</th>
				<th align="center">举报事由</th>
				<th width="100" align="center" orderField="a.REPORTTIME" <%if (request.getParameter("orderField")!=null && request.getParameter("orderField").equals("a.REPORTTIME")) { %> class="${param.orderDirection}" <%} %>>举报时间</th>
				<th width="150" align="center" orderField="a.OFFICER" <%if (request.getParameter("orderField")!=null && request.getParameter("orderField").equals("a.OFFICER")) { %> class="${param.orderDirection}" <%} %>>查办人员</th>			
				<!--<logic:equal name="IsHead" value="1" scope="session">
					<th width="100" align="center" orderField="a.OFFICER" <%if (request.getParameter("orderField")!=null && request.getParameter("orderField").equals("a.OFFICER")) { %> class="${param.orderDirection}" <%} %>>查办人员</th>
				</logic:equal>-->
				
				<th width="180" align="center">管理</th>
			</tr>
		</thead>
		<tbody>
<logic:notEqual value="true" name="eventManageForm" property="recordNotFind">
   <logic:notEmpty name="eventManageForm" property="recordList">
     <logic:iterate name="eventManageForm" property="recordList" id="EventBean">
      <tr target="eventid" rel="${EventBean.reportID}">
      	<td align="center">
      		<input type="checkbox" name="ids" value="${EventBean.reportID}" />
      	</td>
      	<td align="center" >
			<bean:write name="EventBean" property="serialNum"/>
		</td>
      	<td align="center" >
			<bean:write name="EventBean" property="status"/>
		</td>
		<td align="center" >
			<bean:write name="EventBean" property="reportName"/>
		</td>
		<td align="center" >
			<bean:write name="EventBean" property="beReportName"/>
		</td>
		<td align="center" >
			<bean:write name="EventBean" property="reportReason"/>
		</td>
		<td  align="center" >
			<bean:write name="EventBean" property="reportTime"/>
		</td>
		<td align="center">
		<bean:write name="EventBean" property="officer"/>
		<logic:equal name="RoleIDs" value="1" scope="session">
				&nbsp;&nbsp;
				<a class="btnEdit" href='<%=path%>/dispatchEventAction.do?method=init&reportID=${EventBean.reportID}' clss="link edit" mask="true" target="dialog" rel="dispatchEvent" width="550" height="450" title="指派查办人员">重新指派</a>
		</logic:equal>
		<logic:equal name="RoleIDs" value="3" scope="session">
				&nbsp;&nbsp;
				<a class="btnEdit" href='<%=path%>/dispatchEventAction.do?method=init&reportID=${EventBean.reportID}' clss="link edit" mask="true" target="dialog" rel="dispatchEvent" width="550" height="450" title="指派查办人员">重新指派</a>
		</logic:equal>
		<logic:equal name="RoleIDs" value="9" scope="session">
				&nbsp;&nbsp;
				<a class="btnEdit" href='<%=path%>/dispatchEventAction.do?method=init&reportID=${EventBean.reportID}' clss="link edit" mask="true" target="dialog" rel="dispatchEvent" width="550" height="450" title="指派查办人员">重新指派</a>
		</logic:equal>
		</td>
		<td align="center" >
			<!-- 
			<a href="#">&nbsp;</a>
			<a href="<%=path%>/eventManageAction.do?method=detail&id={eventid}" class="btnLook" target="dialog" mask="true" rel="detail" width="900" height="600" title="查看详情">查看</a>
			<a href="<%=path%>/eventManageAction.do?method=delete&type=noreal&id={eventid}" class="btnDel" target="ajaxTodo" title="确定要删除吗?">删除</a>
			 -->
			 
			<a href="<%=path%>/eventManageAction.do?method=detail&id={eventid}" target="dialog" mask="true" rel="detail" width="900" height="600" title="查看详情">查看</a>
			
			<!--<logic:equal name="IsHead" value="0" scope="session">
				<a href="<%=path%>/eventManageAction.do?method=gyMethod&type=check&id=${EventBean.reportID}" mask="true" target="dialog" rel="checkEvent" width="850" height="550" title="核实">核实</a>
				<logic:equal value="已初步核实" name="EventBean" property="status">
				<a href="<%=path%>/lookUpGroupAction.do?method=sbld&id=${EventBean.reportID}" mask="true" target="dialog" width="650" height="450" title="上报领导">上报领导</a>
				</logic:equal>
			</logic:equal>-->
			<logic:equal name="RoleIDs" value="6" scope="session">
				<a href="<%=path%>/eventManageAction.do?method=gyMethod&type=check&id=${EventBean.reportID}" mask="true" target="dialog" rel="checkEvent" width="850" height="550" title="核实">核实</a>
				<a id="more${EventBean.reportID}" style="cursor: pointer;text-decoration: underline;" href="javascript:showSljdDiv('${EventBean.reportID}');" title="更多操作">更多操作</a>
			</logic:equal>
			<logic:equal name="RoleIDs" value="1" scope="session">
				<a href="<%=path%>/eventManageAction.do?method=gyMethod&type=check&id=${EventBean.reportID}" mask="true" target="dialog" rel="checkEvent" width="850" height="550" title="核实">核实</a>
				<a id="more${EventBean.reportID}" style="cursor: pointer;text-decoration: underline;" href="javascript:showSljdDiv('${EventBean.reportID}');" title="更多操作">更多操作</a>
			</logic:equal>
		</td>
	</tr>
	</logic:iterate>
	</logic:notEmpty>
	</logic:notEqual>
	<logic:equal value="true" name="eventManageForm" property="recordNotFind">
	<tr>
		<td align="center" colspan="7">
			没有查询到任何举报内容
		</td>
	</tr>
	</logic:equal>
		</tbody>
	</table>
	<div class="panelBar">
	<div class="pages">
		<span>每页 20  条, 共 <%=request.getAttribute("totalRows") %> 条, 共 <%=request.getAttribute("pageCount") %> 页</span>
	</div>
	<div class="pagination" targetType="navTab" totalCount=" <%=request.getAttribute("totalRows") %>" numPerPage="20" pageNumShown="10" currentPage="<%=request.getAttribute("pageNum") %>"></div>
</div>
</div>
