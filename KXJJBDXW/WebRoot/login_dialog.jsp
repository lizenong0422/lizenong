<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>科学基金科研诚信管理平台</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="<%=path %>/styles/login.css" rel="stylesheet" type="text/css" />
<script>
	window.location.href = "<%=path%>/login.jsp";
</script>	
