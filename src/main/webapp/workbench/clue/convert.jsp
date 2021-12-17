<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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


<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript">
	$(function(){
		$(".time").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "bottom-left"
		});

		$("#isCreateTransaction").click(function(){
			if(this.checked){
				$("#create-transaction2").show(200);
			}else{
				$("#create-transaction2").hide(200);
			}
		});

	   //打开模态窗口
		$("#activityModel").click(function () {

			$("#searchActivityModal").modal("show");

			$("#name").keydown(function (e) {
				//判断按下的哪个键
				if(e.keyCode==13){
					$.ajax({
						url:"user/clue/activityConventModel",
						data:{
							name:$("#name").val()
						},
						dataType:"json",
						type:"get",
						success:function (resp) {
							var html='';
							$.each(resp,function (i,n) {
								html+='<tr>';
								html+='<td><input value="'+n.id+'" name="xz" type="radio" name="activity"/></td>';
								html+='<td id="'+n.id+'">'+n.name+'</td>';
								html+='<td>'+n.startDate+'</td>';
								html+='<td>'+n.endDate+'</td>';
								html+='<td>'+n.owner+'</td>';
								html+='</tr>';
							})

							$("#tbody").html(html);
						}



					})
                    return false
				}

			})

 })
		  //选择市场活动点击确定后
		  $("#addBtn").click(function () {

		  	if ($("input[name=xz]:checked").length==0){
		  		alert("请选择你要绑定的数据");
			}

		  	var id=$("input[name=xz]:checked").val();


		  	 $("#hidden").val(id);

               $("#activity").val($("#"+id).text());


			  $("#searchActivityModal").modal("hide");

		  })

		 //转化线索
       $("#Conversion").click(function () {

       	//判定创不创建交易
		   if ($("#isCreateTransaction").prop("checked")){

			   $("#formActivity").submit();



		   }else{
			   window.location.href="user/clue/Conversion?clueId=${param.clueId}";

		   }



	   })






});



</script>

</head>
<body>

<!-- 搜索市场活动的模态窗口 -->
<div class="modal fade" id="searchActivityModal" role="dialog" >
 <div class="modal-dialog" role="document" style="width: 90%;">
     <div class="modal-content">
         <div class="modal-header">
             <button type="button" class="close" data-dismiss="modal">
                 <span aria-hidden="true">×</span>
             </button>
             <h4 class="modal-title">搜索市场活动</h4>
         </div>
         <div class="modal-body">
             <div class="btn-group" style="position: relative; top: 18%; left: 8px;">
                 <form class="form-inline" role="form">
                   <div class="form-group has-feedback">
                     <input id="name" type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
                     <span class="glyphicon glyphicon-search form-control-feedback"></span>
                   </div>
                 </form>
             </div>
             <table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
                 <thead>
                     <tr style="color: #B3B3B3;">
                         <td></td>
                         <td>名称</td>
                         <td>开始日期</td>
                         <td>结束日期</td>
                         <td>所有者</td>
                         <td></td>
                     </tr>
                 </thead>
                 <tbody id="tbody">
                     <%--<tr>
                         <td><input type="radio" name="activity"/></td>
                         <td>发传单</td>
                         <td>2020-10-10</td>
                         <td>2020-10-20</td>
                         <td>zhangsan</td>
                     </tr>
                     <tr>
                         <td><input type="radio" name="activity"/></td>
                         <td>发传单</td>
                         <td>2020-10-10</td>
                         <td>2020-10-20</td>
                         <td>zhangsan</td>
                     </tr>--%>
						</tbody>
					</table>
				</div>
			 <div class="modal-footer">
				 <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				 <button type="button" class="btn btn-primary" id="addBtn">确定</button>
			 </div>
			</div>
		</div>
	</div>

	<div id="title" class="page-header" style="position: relative; left: 20px;">
		<h4>转换线索 <small>${param.fullname}-${param.company}</small></h4>
	</div>
	<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
		新建客户：${param.fullname}
	</div>
	<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
		新建联系人：${param.company}
	</div>
	<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
		<input type="checkbox" id="isCreateTransaction"/>
		为客户创建交易
	</div>
	<div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;" >
		<form id="formActivity" action="user/clue/Conversion">

			<input type="hidden" name="flag" value="true">

			<input type="hidden" name="clueId" value="${param.clueId}">


			<div class="form-group" style="width: 400px; position: relative; left: 20px;">
		    <label for="amountOfMoney">金额</label>
		    <input type="text" class="form-control" id="amountOfMoney" name="money">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="tradeName">交易名称</label>
		    <input type="text" class="form-control" id="tradeName" name="name">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="expectedClosingDate">预计成交日期</label>
		    <input type="text" class="form-control time" id="expectedClosingDate" name="expectedDate">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="stage">阶段</label>
		    <select id="stage"  class="form-control" name="stage">
		    	<option></option>
				<c:forEach items="${stage}" var="s">
					//每一个对象中的数据
					<option value="${s.value}">${s.text}</option>
				</c:forEach>
		    </select>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="activity">市场活动源&nbsp;&nbsp;<a id="activityModel" href="javascript:void(0);"style="text-decoration: none;"><span class="glyphicon glyphicon-search"></span></a></label>
		    <input type="text" class="form-control" id="activity" placeholder="点击上面搜索" readonly>
		    <input type="hidden" id="hidden" name="activityId">
		  </div>
		</form>
		
	</div>
	
	<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
		记录的所有者：<br>
		<b>${param.owner}</b>
	</div>
	<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
		<input class="btn btn-primary" type="button" id="Conversion" value="转换">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-default" type="button" value="取消">
	</div>
</body>
</html>
</html>