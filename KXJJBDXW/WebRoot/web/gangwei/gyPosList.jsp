<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<form id="pagerForm" method="post" action='<%=request.getContextPath()%>/lookUpGroupAction.do?method=queryMsg&operation=changePage&type=pos'>
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="pageSize" value="${pageSize}" />
</form>

<div class="pageHeader">
	<form method="post" action="<%=request.getContextPath()%>/lookUpGroupAction.do?method=queryMsg&operation=search&type=pos" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>职务名称:</label>
				<input type="text" name="posName" value=""/>
			</li>
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" multLookup="posId" warn="请选择职务">选择</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="118" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th align="center" width="50">编号</th>
				<th align="center" width="200">职务名称</th>
				<th align="center" >职务简介</th>
				<th align="center" width="60">选择</th>
			</tr>
		</thead>
		<tbody>
		<logic:notEqual value="true" name="lookUpGroupForm" property="recordNotFind">
   				<logic:notEmpty name="lookUpGroupForm" property="recordList">
     				<logic:iterate name="lookUpGroupForm" property="recordList" id="PosBean">
      					<tr>
      						<td align="center">
								<bean:write name="PosBean" property="serialNum"/>
							</td>
      						<td align="center">
								<bean:write name="PosBean" property="posName"/>
							</td>
							<td align="center">
								<bean:write name="PosBean" property="posDescribe"/>
							</td>
							<td align="center">
								<a href="#">&nbsp;</a>
								<a class="btnSelect" href="javascript:$.bringBack({posID:'${PosBean.id }',posName:'${PosBean.posName}'})" title="职务选择">选择</a>
							</td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
			</logic:notEqual>
			<logic:equal value="true" name="lookUpGroupForm" property="recordNotFind">
			<tr>
				<td align="center" colspan="5">
					没有查询到任何职务
				</td>
			</tr>
			</logic:equal>
		</tbody>
	</table>
<div class="panelBar">
	<div class="pages">
		<span>每页10  条, 共 <%=request.getAttribute("totalRows") %> 条, 共 <%=request.getAttribute("pageCount") %> 页</span>
	</div>
	<div class="pagination" targetType="dialog" totalCount=" <%=request.getAttribute("totalRows") %>" numPerPage="10" pageNumShown="10" currentPage="<%=request.getAttribute("pageNum") %>"></div>
</div>
</div>