<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  
                + request.getServerName() + ":" + request.getServerPort()  
                + path + "/";  
%> 
<script type="text/javascript">
	var zTree, rMenu;
	var treeNodes;
	var key;
	var setting = {
		view: {
			dblClickExpand: false,
			selectedMulti: false
		},
		check: {
			enable: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: onClick
		}
	};
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("addExpertTree"),
		nodes = zTree.getSelectedNodes(),
		v = "";
		id = "";
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
			id += nodes[i].id + ",";
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		if (id.length > 0 ) id = id.substring(0, id.length-1);
		var cityObj = $("#addExpertSel");
		cityObj.attr("value", v);
		hideMenu();
	}

	function showMenu() {
		var tempLeft = 405;
		var tempTop = 357;
		var cityObj = $("#addExpertSel");
		var cityOffset = $("#addExpertSel").offset();
		var leftPX = tempLeft - 263;
		var topPX = tempTop + cityObj.outerHeight() - 50;
		$("#menuContent").css({left:leftPX + "px", top:topPX + "px"}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	$(function(){  
	    $.ajax({  
	        async : false,  
	        cache:false,  
	        type: 'POST',  
	        dataType : "json",  
	        url: "<%=path%>/zzManageAction.do?method=initTree&type=3",//请求的action路径  
	        error: function () {//请求失败处理函数  
	            alert('请求失败');  
	        },  
	        success:function(data){ //请求成功后处理函数。    
	            treeNodes = data;   //把后台封装好的简单Json格式赋给treeNodes
	        }
	    });
	  
	    $.fn.zTree.init($("#addExpertTree"), setting, treeNodes);
	});
</script> 
<div class="pageContent">
	<form method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);" action="<%=path%>/configExpertAction.do?method=save&operation=new">
		<div class="pageFormContent" layoutH="57">
			<dl class="nowrap">
				<dt>专家姓名：</dt>
				<dd><input class="required" minlength="2" maxlength="20" name="name" type="text" size="20"/></dd>
			</dl>
			<dl class="nowrap">
				<dt>性别：</dt>
				<dd><select name="sex">
							<option value="男">请选择</option>
							<option value="男">男</option>
							<option value="女">女</option>
					</select></dd>
			</dl>
			<dl class="nowrap">
				<dt>年龄：</dt>
				<dd><input name="age" type="text" size="7" /></dd>
			</dl>
			<div class="divider"></div>
			<dl class="nowrap">
				<dt>职称：</dt>
				<dd>
					<input class="required" minlength="1" maxlength="25" name="title" type="text" size="25" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>是否博导：</dt>
					<dd>
						<select name="isPHD">
							<option value="否">请选择</option>
							<option value="是">是</option>
							<option value="否">否</option>
						</select>
					</dd>
			</dl>
			<dl class="nowrap">
				<dt>单位：</dt>
				<dd><input class="required" minlength="2" maxlength="25" name="dept" type="text" size="50" /></dd>
			</dl>
			<dl class="nowrap">
				<dt>专业：</dt>
				<dd>
					<input class="required" name="specialty" type="text" size="40"/>
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>研究方向：</dt>
					<dd>
						<input name="research" type="text" size="40"/>
					</dd>
			</dl>
			<dl class="nowrap">
				<dt>所属学部：</dt>
				<dd>
					<input id="addExpertSel" class="required" readonly name="faculty" type="text" size="30" />
					<a id="menuBtn" class="btnLook" href="#" onclick="showMenu(); return false;">选择</a>
					<span class="info">选择</span>
				</dd>
			</dl>
			<div class="divider"></div>
			<dl class="nowrap">
				<dt>联系电话：</dt>
				<dd>
					<input minlength="6" maxlength="20" name="phone" type="text" size="25" />
				</dd>
			</dl>
			<dl class="nowrap">
				<dt>邮箱地址：</dt>
					<dd>
						<input class="required email" name="email" size="40" type="text"/>
					</dd>
			</dl>
			<dl class="nowrap">
				<dt>通讯地址：</dt>
				<dd>
					<input minlength="2" maxlength="50" name="address" type="text" size="60" />
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<div id="menuContent" class="menuContent" style="display:none; border:solid 1px #CCC; background:#fff; position: absolute;">
	<ul id="addExpertTree" class="ztree" style="margin-top:0; width:240px;"></ul>
</div>
