<%@ page contentType="text/html;charset=UTF-8" language="java"
%>

<%
	String basePath = request.getScheme() + "://" +
			request.getServerName() + ":" + request.getServerPort() +
			request.getContextPath() + "/";
%>

<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script>

		$( function() {

			if (window.top!=window){
				window.top.location=window.location;
			}


			//使账号文本框获取焦点
			$("#loginact").focus();
			  //绑定事件
			$("#subBtn1").click(function () {
				 ajx();
				 })

			$(window).keydown(function (e) {
                  //判断按下的哪个键
				if(e.keyCode==13){
					ajx();
				}
			})

			//每重新输入内容之后，msg的值会变
			 $("#loginact").keydown(function () {
                     msg();
			})

			$("#loginpwd").keydown(function () {
				msg();
			})


			 function ajx() {
				 var loginAct=$("#loginact").val().toString();
				 var loginPwd=$("#loginpwd").val().toString();

				 loginAct=$.trim(loginAct);
				 loginPwd=$.trim(loginPwd);

				 if (loginAct==""||loginPwd=="") {
				 	$("#msg").text("账号或者密码不能为空");
					 return false;
				 }
					 $.ajax({
						 type:"post",
						 url:"user/loginUser",
						 data:{
							 "loginAct" : loginAct,
							 "loginPwd" : loginPwd
						 },
						 dataType:"json",
						 success:function(resp) {
							 if (resp.suu){
								 document.location.href = "workbench/index.jsp";
							 }
							 else {
								 $("#msg").text(resp.msg);
							 }
						 }
					 })

		}



		})

		function msg() {
			$("#msg").text("");
		}


	</script>



</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" id="loginact">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="密码" id="loginpwd">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">

					<span id="msg" style="color: red"></span>

				</div>
					<button type="button" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;" id="subBtn1">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>