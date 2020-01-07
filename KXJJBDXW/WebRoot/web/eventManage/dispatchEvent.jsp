<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="org.apache.commons.lang.StringUtils" %>
<%@ include file="/commons/taglibs.jsp"%>
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  
                + request.getServerName() + ":" + request.getServerPort()  
                + path + "/";                  
%> 

<form id="pagerForm" method="post" action='<%=request.getContextPath()%>/dispatchEventAction.do?method=queryMsg&operation=changePage&serialNum=<%=request.getParameter("serialNum") %>'>
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="pageSize" value="${pageSize}" />
</form>

<div class="pageHeader">
	<form method="post" action="<%=request.getContextPath()%>/dispatchEventAction.do?method=queryMsg&operation=search&serialNum=<%=request.getParameter("serialNum") %>" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>姓名:</label>
				<input type="text" name="officer" value=""/>
			</li>
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" rel="ids" href="<%=path%>/dispatchEventAction.do?method=dispatch&reportID=<%=request.getParameter("reportID") %>" postType="string" target="selectedTodo" targetType="dialog" title="确定指派这些查办人员吗？"><span>确定</span></a></li>
		</ul>
	</div>
	<table class="table" layoutH="118" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th align="center"><input type="checkbox" class="checkboxCtrl" group="ids"/></th>
				<th align="center" width="50">编号</th>
				<th align="center" width="150">用户名</th>
				<th align="center" width="200">姓名</th>
				<!-- <th align="center" width="60">选择</th> -->
			</tr>
		</thead>
		<tbody>
		<logic:notEqual value="true" name="dispatchEventForm" property="recordNotFind">
   				<logic:notEmpty name="dispatchEventForm" property="recordList">
     				<logic:iterate name="dispatchEventForm" property="recordList" id="UserBean">
      					<tr target="eventid" rel="${UserBean.id}">
      						<td align="center">
      						<input type="checkbox" name="ids" id="checkboxID" value="${UserBean.id}"/> 
     							</td>
      						<td align="center">
								<bean:write name="UserBean" property="serialNum"/>
								</td>
								<td align="center">
								<bean:write name="UserBean" property="loginName"/>
								</td>
      						<td align="center">
      						<input type="hidden" id="${UserBean.id}" name="test" value="${UserBean.userName }"/>
								<bean:write name="UserBean" property="userName"/>
								</td>
							<!-- <td align="center">
								<a href="#">&nbsp;</a>
								<a class="btnSelect" href="<%=path%>/dispatchEventAction.do?method=dispatch&officer=${UserBean.id}&reportID=<%=request.getParameter("reportID") %>" target="ajaxTodo" callback="dialogAjaxDone" title="确定分派给 ${UserBean.userName } 吗?">选择</a>
							</td> -->
						</tr>
					</logic:iterate>
				</logic:notEmpty>
			</logic:notEqual>
			<logic:equal value="true" name="dispatchEventForm" property="recordNotFind">
			<tr>
				<td align="center" colspan="5">
					没有查询到任何办公室人员
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