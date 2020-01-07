<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  
                + request.getServerName() + ":" + request.getServerPort()  
                + path + "/";
%> 
<script type="text/javascript" src="<%=path %>/web/dsoframer/dsoframer.js"></script>
<script type="text/javascript">
	var XMLHttpReq=false;
   //创建一个XMLHttpRequest对象
   
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
	         		alert(root);
	         	}
	       		else{
	         		window.alert("所请求的页面有异常");
	       		}
	     	}
	   }
	function checkIdentity() {
		alertMsg.biconfirm("该实名举报者的身份是否真实？", {
			okName1: "属实",
			okCall1: function(){
				$.post("<%=path%>/servlet/CheckIdentityServlet?type=real", null, ajaxDoneReloadCurrent, "json");
			},
			okName2: "不属实",
			okCall2: function() {
				$.post("<%=path%>/servlet/CheckIdentityServlet?type=noreal" + id, null, ajaxDoneReloadCurrent, "json");
			}
		});
		
		var ajaxDoneReloadCurrent = function(data) {
			if (data.statusCode == DWZ.statusCode.error) {
				if(data.message) alertMsg.error(data.message);
			} else if (data.statusCode == DWZ.statusCode.timeout) {
				alertMsg.error(data.message || DWZ.msg("Session Timeout"));
			} else {
				alertMsg.correct(data.message || DWZ.msg("Done"));
				$.pdialog.reload($.pdialog.getCurrent().data("url"));
			}
		}
	}
	 //向举报者发送邮件
	 function sendMail()
	 {
	 	//先控制页面跳转到发送邮件页面
	 	$("#sendEmailID").click();
	 	//然后关闭当前对话框
	 	$.pdialog.closeCurrent();
	 }
	 
	 function printEvent()
	 {
		var id = document.getElementById("report_id").value;
	 	if(id != "" && id != null){
	 		var url = "<%=path%>/eventDetailAction.do?method=print&id=" + id;
			//openMaxWin(url);
			window.open(url);
	 	}
	 }

</script>
<script type="text/javascript">
function showreason()
{
	paramers="dialogWidth:320px; dialogHeight:400px; resizable:yes; overflow:auto; status:no";
	url = "<%=path%>/web/event/reportReason.jsp";
	var value1 = window.showModalDialog(url, "", paramers);
	if(value1.names !== null)
	{	
		document.getElementById("detail_jbReason").value=value1.names;
	}
}
</script>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<div class="pageContent">
	<div class="tabs" currentIndex="0" eventType="click">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>举报信息</span></a></li>
					<li><a href="<%=path %>/eventDetailAction.do?method=eventDetail&type=approveInfo" class="j-ajax"><span>审核信息</span></a></li>
					<!--  <li><a href="<%=path %>/eventDetailAction.do?method=eventDetail&type=analysInve" class="j-ajax"><span>分析结论</span></a></li>
					<li><a href="<%=path %>/eventDetailAction.do?method=eventDetail&type=treatSuggest" class="j-ajax"><span>处理建议</span></a></li>-->
					<li><a href="<%=path %>/eventDetailAction.do?method=eventDetail&type=deptAdvice" class="j-ajax"><span>依托单位意见</span></a></li>
					<li><a href="<%=path %>/eventDetailAction.do?method=eventDetail&type=expertAdvice" class="j-ajax"><span>专家意见</span></a></li>
					<li><a href="<%=path %>/eventDetailAction.do?method=eventDetail&type=litigantState" class="j-ajax"><span>当事人陈述</span></a></li>
					<li><a href="<%=path %>/eventDetailAction.do?method=eventDetail&type=facultyAdvice" class="j-ajax"><span>学部意见</span></a></li>
					<li><a href="<%=path %>/eventDetailAction.do?method=eventDetail&type=analysInve" class="j-ajax"><span>分析结论</span></a></li>
					<li><a href="<%=path %>/eventDetailAction.do?method=eventDetail&type=treatSuggest" class="j-ajax"><span>处理建议</span></a></li>
					<li><a href="<%=path %>/eventDetailAction.do?method=eventDetail&type=conofmeet" class="j-ajax"><span>会议结论</span></a></li>
					<li><a href="<%=path %>/eventDetailAction.do?method=eventDetail&type=handleDecide" class="j-ajax"><span>处理决定</span></a></li>
					<li><a href="<%=path %>/eventDetailAction.do?method=eventDetail&type=handleFlow" class="j-ajax"><span>处理流程</span></a></li>
					<li><a href="<%=path %>/eventDetailAction.do?method=attachment" class="j-ajax"><span>附件</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
		<div>
		<logic:notEqual value="true" name="eventManageForm" property="isEdit">
			<div class="pageFormContent" layoutH="95">
			<form method="post" action="<%=path%>/eventDetailAction.do?method=print" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<logic:notEqual value="true" name="eventManageForm" property="recordNotFind">
		<logic:notEmpty name="eventManageForm" property="recordList">
     		<logic:iterate name="eventManageForm" property="recordList" id="EventBean">
     		<input type="hidden" id="report_id" name="report_id" value='${EventBean.reportID}'/>
				<fieldset>
					<legend>举报人信息</legend>
					<logic:equal value="1" name="EventBean" property="isNI">
						<dl>
							<dt>姓名：</dt>
							<dd><input readonly type="text" size="20" value='${EventBean.reportName}' style="color:#ff0000;"/></dd>
						</dl>
						<dl>
							<dt>举报方式：</dt>
							<dd><input readonly type="text" size="30" value='${EventBean.reportType}'/></dd>
						</dl>
						<dl>
							<dt>举报时间：</dt>
							<dd><input readonly type="text" size="30" class="required date" value='${EventBean.reportTime}'/><a class="inputDateButton" href="javascript:;">选择</a></dd>
						</dl>
						<dl>
							<dt>收件编号：</dt>
							<dd><input readonly type="text" size="20" value='${EventBean.serialNum}'/></dd>
						</dl>
						<dl>
							<dt>系统编号：</dt>
							<dd><input readonly type="text" size="30" value='${EventBean.reportID}'/></dd>
						</dl>
						<dl>
							<dt>录入人员：</dt>
							<dd><input readonly type="text" size="30" value='${EventBean.recorder}'/></dd>
						</dl>
					</logic:equal>
					<logic:notEqual value="1" name="EventBean" property="isNI">
						<dl class="nowrap">
							<dt>姓名：</dt>
							<dd>
								<input readonly type="text" size="20" value='${EventBean.reportName}' style="color:#ff0000;"/>
								<!--<logic:equal value="0" name="EventBean" property="isNI">
									<div class="button"><div class="buttonContent"><button onclick="javascript:checkIdentity();">核实身份</button></div></div>
								</logic:equal>
								<logic:equal value="2" name="EventBean" property="isNI">
									<font color="#ff0000">身份已核实</font>
								</logic:equal>-->
							</dd>
						</dl>
						<dl class="nowrap">
							<dt>工作单位：</dt>
							<dd><input readonly type="text" size="60" value='${EventBean.dept}'/></dd>
						</dl>
						<dl class="nowrap">
							<dt>邮箱地址：</dt>
							<dd>
								<input readonly type="text" size="30" value='${EventBean.mailAddress}'/>
								<!--<logic:notEqual value="" name="EventBean" property="mailAddress">
								<logic:notEqual value="无" name="EventBean" property="mailAddress">
									<a id="sendEmailID" href="<%=path%>/newMailAction.do?method=init&address=${EventBean.mailAddress}" target="navTab" rel="newEmail" style="display:none;">发送邮件</a>
									<a href="#" onclick="javascript:sendMail()"><font color="blue">发送邮件</font></a>
								</logic:notEqual>
								</logic:notEqual>-->
							</dd>
						</dl>
						<dl>
							<dt>固定电话：</dt>
							<dd><input readonly type="text" size="30" value='${EventBean.gdPhone}'/></dd>
						</dl>
						<dl>
							<dt>手机号码：</dt>
							<dd><input readonly type="text" size="30" value='${EventBean.telPhone}' style="color:#ff0000;"/></dd>
						</dl>
						<dl>
							<dt>举报时间：</dt>
							<dd><input readonly type="text" size="30" value='${EventBean.reportTime}'/></dd>
						</dl>
						<dl>
							<dt>举报方式：</dt>
							<dd><input readonly type="text" size="30" value='${EventBean.reportType}'/></dd>
						</dl>
						<dl>
							<dt>收件编号：</dt>
							<dd><input readonly type="text" size="20" value='${EventBean.serialNum}' style="color:#ff0000;"/></dd>
						</dl>
						<dl>
							<dt>系统编号：</dt>
							<dd><input readonly type="text" size="30" value='${EventBean.reportID}'/></dd>
						</dl>
						<dl>
							<dt>录入人员：</dt>
							<dd><input readonly type="text" size="30" value='${EventBean.recorder}'/></dd>
						</dl>
					</logic:notEqual>
				</fieldset>
				<fieldset>
					<legend>被举报人信息</legend>
					<logic:notEmpty name="EventBean" property="beReportList">
					<logic:iterate name="EventBean" property="beReportList" id="BeReportBean">
						<dl class="nowrap">
							<dt>被举报人姓名：</dt>
							<dd><input readonly type="text" size="30" value='${BeReportBean.beName}' style="color:#ff0000;"/></dd>
						</dl>
						<dl class="nowrap">
							<dt>所属单位：</dt>
							<dd><input readonly type="text" size="60" value='${BeReportBean.beDept}'/></dd>
						</dl>
						<dl>
							<dt>职称：</dt>
							<dd><input readonly type="text" size="30" value='${BeReportBean.bePosition}'/></dd>
						</dl>
						<dl>
							<dt>联系方式：</dt>
							<dd><input readonly type="text" size="30" value='${BeReportBean.beTelPhone}'/></dd>
						</dl>
						<div class="divider"></div>
					</logic:iterate>
					</logic:notEmpty>
				</fieldset>
				<fieldset>
					<legend>举报内容</legend>
					<!-- <dl class="nowrap">
						<dt>所属学部：</dt>
						<dd><input readonly type="text" size="50" value='${EventBean.faculty}' style="color:#ff0000;"/></dd>
					</dl> -->
					<dl class="nowrap">
						<dt>举报不端类型：</dt>
						<dd><textarea rows="3" cols="80" readonly>${EventBean.reportReason}</textarea></dd>
					</dl>
					<dl class="nowrap">
						<dt>内容详情：</dt>
						<dd>
							<textarea rows="15" cols="80" readonly>${EventBean.reportContent}</textarea>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>备注：</dt>
						<dd>
							<textarea rows="8" cols="80" readonly>${EventBean.bz}</textarea>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>附件：</dt>
						<dd>
							<logic:notEqual value="" name="EventBean" property="accessory">
								<a href="${EventBean.accessory }" >下载附件</a>
							</logic:notEqual>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>举报查询码：</dt>
						<dd><input readonly type="text" size="30" value='${EventBean.searchID}'/></dd>
					</dl>
				</fieldset>
				</logic:iterate>
				</logic:notEmpty>
				</logic:notEqual>
				<logic:notEqual value="false" name="eventManageForm" property="recordNotFind">
				出错了！！！
				</logic:notEqual>
				</form>
			</div>
			<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="printEvent()">打印</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</logic:notEqual>
	
	<logic:notEqual value="false" name="eventManageForm" property="isEdit">
			<div class="pageFormContent" layoutH="95">
			<form method="post" action="<%=path%>/newEventAction.do?method=editReportInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<logic:notEqual value="true" name="eventManageForm" property="recordNotFind">
		<logic:notEmpty name="eventManageForm" property="recordList">
     		<logic:iterate name="eventManageForm" property="recordList" id="EventBean">
				<fieldset>
					<legend>举报人信息</legend>
					<logic:equal value="1" name="EventBean" property="isNI">
						<dl>
							<dt>姓名：</dt>
							<dd><input name="reportName" type="text" size="20" value='${EventBean.reportName}' style="color:#ff0000;"/></dd>
						</dl>
						<dl>
							<dt>举报方式：</dt>
							<dd><input name="reportType" type="text" size="30" value='${EventBean.reportType}'/></dd>
						</dl>
						<dl>
							<dt>举报时间：</dt>
							<dd><input name="reportTime" type="text" size="30" class="required date" value='${EventBean.reportTime}'/><a class="inputDateButton" href="javascript:;">选择</a></dd>
						</dl>
						<dl>
							<dt>收件编号：</dt>
							<dd><input name="serialNum" type="text" size="20" value='${EventBean.serialNum}'/></dd>
						</dl>
						<dl>
							<dt>系统编号：</dt>
							<dd><input readonly name="reportID" type="text" size="30" value='${EventBean.reportID}'/></dd>
						</dl>
						<dl>
							<dt>录入人员：</dt>
							<dd><input readonly type="text" size="30" value='${EventBean.recorder}'/></dd>
						</dl>
					</logic:equal>
					<logic:notEqual value="1" name="EventBean" property="isNI">
						<dl class="nowrap">
							<dt>姓名：</dt>
							<dd>
								<input name="reportName" type="text" size="20" value='${EventBean.reportName}' style="color:#ff0000;"/>
								<logic:equal value="0" name="EventBean" property="isNI">
									<div class="button"><div class="buttonContent"><button onclick="javascript:checkIdentity();">核实身份</button></div></div>
								</logic:equal>
								<logic:equal value="2" name="EventBean" property="isNI">
									<font color="#ff0000">身份已核实</font>
								</logic:equal>
							</dd>
						</dl>
						<dl class="nowrap">
							<dt>工作单位：</dt>
							<dd><input name="dept" type="text" size="60" value='${EventBean.dept}'/></dd>
						</dl>
						<dl class="nowrap">
							<dt>邮箱地址：</dt>
							<dd>
								<input name="mailAddress" type="text" size="30" value='${EventBean.mailAddress}'/>
								<!-- <logic:notEqual value="" name="EventBean" property="mailAddress">
								<logic:notEqual value="无" name="EventBean" property="mailAddress">
									<a id="sendEmailID" href="<%=path%>/newMailAction.do?method=init&address=${EventBean.mailAddress}" target="navTab" rel="newEmail" style="display:none;">发送邮件</a>
									<a href="#" onclick="javascript:sendMail()"><font color="blue">发送邮件</font></a>
								</logic:notEqual>
								</logic:notEqual>-->
							</dd>
						</dl>
						<dl>
							<dt>录入人员：</dt>
							<dd><input readonly type="text" size="30" value='${EventBean.recorder}'/></dd>
						</dl>
						<dl>
							<dt>固定电话：</dt>
							<dd><input name="gdPhone" type="text" size="30" value='${EventBean.gdPhone}'/></dd>
						</dl>
						<dl>
							<dt>手机号码：</dt>
							<dd><input name="telPhone" type="text" size="30" value='${EventBean.telPhone}' style="color:#ff0000;"/></dd>
						</dl>
						<dl>
							<dt>举报时间：</dt>
							<dd><input name="reportTime" type="text" size="30" class="required date" value='${EventBean.reportTime}'/><a class="inputDateButton" href="javascript:;">选择</a></dd>
						</dl>
						<dl>
							<dt>举报方式：</dt>
							<dd><input name="reportType" type="text" size="30" value='${EventBean.reportType}'/></dd>
						</dl>
						<dl>
							<dt>收件编号：</dt>
							<dd><input name="serialNum" type="text" size="20" value='${EventBean.serialNum}' style="color:#ff0000;"/></dd>
						</dl>
						<dl>
							<dt>系统编号：</dt>
							<dd><input readonly name="reportID" type="text" size="30" value='${EventBean.reportID}'/></dd>
						</dl>
						<dl>
							<dt>录入人员：</dt>
							<dd><input readonly type="text" size="30" value='${EventBean.recorder}'/></dd>
						</dl>
					</logic:notEqual>
				</fieldset>
				<fieldset>
					<legend>被举报人信息</legend>
					<logic:notEmpty name="EventBean" property="beReportList">
					<logic:iterate name="EventBean" property="beReportList" id="BeReportBean" indexId="number">
						<dl class="nowrap">
							<dt>被举报人姓名：</dt>
							<dd><input readonly name="beReportedList[${number}].beName" type="text" size="30" value='${BeReportBean.beName}' style="color:#ff0000;"/></dd>
						</dl>
						<dl class="nowrap">
							<dt>所属单位：</dt>
							<dd><input readonly name="beReportedList[${number}].beDept" type="text" size="60" value='${BeReportBean.beDept}'/></dd>
						</dl>
						<dl>
							<dt>职称：</dt>
							<dd><input readonly name="beReportedList[${number}].bePosition" type="text" size="30" value='${BeReportBean.bePosition}'/></dd>
						</dl>
						<dl>
							<dt>联系方式：</dt>
							<dd><input readonly name="beReportedList[${number}].beTelPhone" type="text" size="30" value='${BeReportBean.beTelPhone}'/></dd>
						</dl>
						<div class="divider"></div>
					</logic:iterate>
					</logic:notEmpty>
				</fieldset>
				<fieldset>
					<legend>举报内容</legend>
					<!-- <dl class="nowrap">
						<dt>所属学部：</dt>
						<dd><input readonly type="text" size="50" value='${EventBean.faculty}' style="color:#ff0000;"/></dd>
					</dl> -->
					<dl class="nowrap">
						<dt>举报不端类型：</dt>
						<dd>
							<textarea id="detail_jbReason" rows="3" cols="70" name="reportReason">${EventBean.reportReason}</textarea>
							<a class="btnLook" href="javascript:showreason()">选择举报事由</a>
							<span class="info"></span>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>内容详情：</dt>
						<dd>
							<textarea rows="15" cols="80" name="reportContent">${EventBean.reportContent}</textarea>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>备注：</dt>
						<dd>
							<textarea rows="8" cols="80" name="BZ">${EventBean.bz}</textarea>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>附件：</dt>
						<dd>
							<logic:notEqual value="" name="EventBean" property="accessory">
								<a href="${EventBean.accessory }" >下载附件</a>
							</logic:notEqual>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>举报查询码：</dt>
						<dd><input readonly type="text" size="30" value='${EventBean.searchID}'/></dd>
					</dl>
				</fieldset>
				</logic:iterate>
				</logic:notEmpty>
				</logic:notEqual>
				<logic:notEqual value="false" name="eventManageForm" property="recordNotFind">
				出错了！！！
				</logic:notEqual>
				<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
					</ul>
				</div>
				</form>
			</div>
	</logic:notEqual>
	</div>
	<div></div>
	<div></div>
	<div></div>
	<div></div>
	<div></div>
	<div></div>
	<div></div>
	<div></div>
	<div></div>
	<div></div>
	<div></div>	
			
		</div>

		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>
