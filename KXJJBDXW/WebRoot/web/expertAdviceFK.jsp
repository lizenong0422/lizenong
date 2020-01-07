<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%
String serialNum = request.getParameter("serialNum");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>专家鉴定意见</title>
    <link href="<%=path%>/styles/css.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
	var XMLHttpReq=false;
       
    function createXMLHttpRequest(){
      if(window.XMLHttpRequest){ //Mozilla 
        XMLHttpReq=new XMLHttpRequest();
      }else if(window.ActiveXObject){
 		  try{
          XMLHttpReq=new ActiveXObject("Msxml2.XMLHTTP");
  	  }catch(e){
   	    try{
            XMLHttpReq=new ActiveXObject("Microsoft.XMLHTTP");
          }catch(e){}
        }
      }
    }
    
    //发送提交的请求函数
    function send(url){
      createXMLHttpRequest();
      XMLHttpReq.open("post",url,true);
      XMLHttpReq.onreadystatechange=proce;   //指定响应的函数
      XMLHttpReq.send(null);  //发送请求
    }
    function proce(){        	 
	if(XMLHttpReq.readyState==4){ //对象状态,收到完整的服务器响应
  		  if(XMLHttpReq.status==200){//信息已成功返回，HTTP服务器响应的值为OK   
          		var root=XMLHttpReq.responseText;
          		if(root == "已反馈")
          		{
          			document.getElementById("inputDiv").style.display="none";
          			document.getElementById("fkokDiv").style.display="block";
          		}
          		else if(root == "未反馈")
          		{
          			document.getElementById("inputDiv").style.display="block";
          			document.getElementById("fkokDiv").style.display="none";
          		}
          		else if(root == "页面出错")
          		{
          			document.getElementById("inputDiv").style.display="none";
          			document.getElementById("fkokDiv").style.display="none";
          			alert("页面地址有误，请核实！");
          		}
          	}
       		else{
         		window.alert("所请求的页面有异常");
       		}
      	}
    }
	function count()
	{
		var serialNum = "<%=serialNum%>";
	    send('<%=path%>/servlet/ExpertAdviceServlet?serialNum=' + serialNum);
	}
	
	function countRemind(id)
	{
		var text = document.getElementById(id).value;
		var len;
		var totalLen;
		if(id == "conclusion")//鉴定结论最多200个字
		{
			totalLen = 200;
		}
		else
		{
			totalLen = 2500;
		}
		if(text.length >= totalLen)
		{
			document.getElementById(id).value=text.substr(0, 2500);
			len = 0;
		}
		else
		{
			len = totalLen - text.length;
		}
		document.getElementById(id + "Label").innerText = len;
	}
	function checkType(value)
	{
		
		if(value == "")
		{
			document.getElementById("fileExtFlag").value="1";
		}
		else
		{
			var extname = /\.[^\.]+/.exec(value);
			if(extname==".doc" || extname==".docx" || extname==".ppt" || extname==".pptx" || extname==".xls" || extname==".xlsx" || extname==".rar" || extname == ".zip")
			{
				document.getElementById("fileExtFlag").value="1";
				return true;
			}
			else
			{
				document.getElementById("fileExtFlag").value="0";
				alert("上传文件的格式不合法，只能上传office文件或者压缩文件！请重新上传...");
				return false;
			}
		}
	}
</script>
  </head>
  
<body onload="count();">
  <div class="containter">
	<div class="top"></div>
	<div class="nav clearfix">
		<span class="nav_l"></span>
		<ul>
			 
		</ul>
		<span class="nav_r"></span>
	</div>
	<div class="subnav mar_b_5">
		
	</div>
	<div class="locaArea clearfix mar_b_10">
		<h2>当前位置：</h2>
		<div class="location">专家意见</div>
	</div>
	<div  class="main">
		<div class="nextCon_02">
			<div align="right"><a href="<%=path%>/web/template.html" target="_blank"><font color="#ff0000">查看示例</font></a></div>
			<div id="inputDiv" style="display:none;">
		   		<html:form action="/expertAdviceAction.do?method=expertAdviceFK" method="post" onsubmit="return validateExpertAdviceForm(this);" enctype="multipart/form-data">
				<input type="hidden" name="serialNum" value="<%=serialNum %>"/>
				<h1>专家信息</h1>
				<div class="otherArea">注意：标有<font color="#ff0000">*</font>的必须填写</div>
				<table width="90%"  cellpadding="3"  cellspacing="10" border="0" align="center">
					<tr>
						<td align="right">
						您的姓名：<font color="#ff0000">*</font>
						</td>
						<td colspan="3">
							<input type="text" size="20" name="expertName"></input>
						</td>
					</tr>
					<tr>
						<td align="right">
						工作单位：
						</td>
						<td colspan="3">
							<input type="text" size="50" name="dept"></input>
						</td>
					</tr>
					<tr>
						<td align="right">
						职称：
						</td>
						<td>
							<input type="text" size="20" name="title"></input>
						</td>
					</tr>
				</table>
				<br/>
				<h1>鉴定意见</h1>
				<div class="otherArea"></div>
				<table width="90%"  cellpadding="3"  cellspacing="10" border="0" align="center">
				<tr>
					<td align="right">
					鉴定意见：<font color="#ff0000">*</font>
					</td>
					<td>
						<textarea id="advice" cols="120" name="advice" rows="15" onKeyUp="countRemind('advice')" onblur="countRemind('advice')"></textarea>
						<br/>
						您可以输入<font color="#ff0000"><label id="adviceLabel">2500</label></font>个字！
					</td>	
				</tr>
				<tr>
					<td align="right">
					鉴定结论：<font color="#ff0000">*</font>
					</td>
					<td>
						<textarea id="conclusion" cols="120" name="conclusion" rows="4" onKeyUp="countRemind('conclusion')" onblur="countRemind('conclusion')"></textarea>
						<br/>
						您可以输入<font color="#ff0000"><label id="conclusionLabel">200</label></font>个字！
					</td>
				</tr>
				<tr>
					<td align="right">
					上传附件：
					</td>
					<td>
						<html:file property="file" size="30" onchange="checkType(this.value);"></html:file> 
						<input type="hidden" id="fileExtFlag" name="fileExtFlag" value="1"/>
						<br/>
						请将附件打包压缩后再上传，谢谢！
					</td>
				</tr>
				</table>
				<br/>
				<p class="text_center">
					<input type="submit" class="btn_h24_01_s mar_r_10" value="提 交" />
				</p>
				</html:form>
				<html:javascript formName="expertAdviceForm"/>
		   	</div>
		   	<div id="fkokDiv" style="display:none;">
		   		<br/>
				<br/>
				<br/>
				<br/>
		   		<div align='center'>
		   			<span style='line-height:50px; text-align:center; font-size:28px; color:#126fbb; font-family:microsoft yahei;'>您已经提交过信息，不能再次提交！<br/>
		   			如确实需要更改，请电话或邮件联系办公室，对您造成的不便敬请谅解！<br/>
		   			联系电话：(010)11111111<br/>
		   			邮箱地址：oic@nsfc.gov.cn</span>
		   		</div>
		   		<br/>
				<br/>
				<br/>
				<br/>
		   	</div>
		</div>
	</div>
	
	</div>
	<div class="footer">
		<div class="bottomNav"><a href="###">联系我们</a>|<a href="###">版权说明</a>|<a href="###">浏览建议</a></div>
		<div class="copyright">CopyRight © 版权所有 监督委员会  地址：北京市海淀区双清路83号   累计访问人次：<br />
	建议分辨率 1024X768</div>
	</div>
  </body>
</html>
