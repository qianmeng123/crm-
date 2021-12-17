<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>


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


		$("#addbtn").click(function () {

	 		//处理ajax请求，获取所有者
			$.ajax({
				type:"get",
				url:"user/workbench/getname",
				dataType:"json",
				success:function(resp) {
					$("#create-marketActivityOwner").empty();
					$.each(resp,function (i,n) {
						$("#create-marketActivityOwner").append("<option value='"+n.id+"'>"+n.name+"</option>")
					})

					//给模态窗口的所有者赋一个默认值
					var id="${user.id}";
					$("#create-marketActivityOwner").val(id);

					//打开模态窗口
					$("#createActivityModal").modal("show");
				}
			})
		})

		//保存模态窗口信息
		//模态窗口添加数据
		$("#savebtn").click(function () {
			$.ajax({
				type:"post",
				data:{
					"owner":$.trim($("#create-marketActivityOwner").val()),
					"name":$.trim($("#create-marketActivityName").val()),
					"startDate" :$.trim($("#create-startTime").val()),
					"endDate" :$.trim($("#create-endTime").val()),
					"cost" :$.trim($("#create-cost").val()),
					"description" :$.trim($("#create-describe").val())

				},
				url:"user/workbench/save",
				dataType:"json",
				success:function(resp) {
                   if (resp.success){
					   pageList(1
							   ,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

					   //关闭模态窗口
					   $("#createActivityModal").modal("hide");

				   }
                   else{
                  alert("保存失败,"+resp.msg+"")
				   }

				}
			})

			//重置模态窗口的信息,(用dom对象，jquery对象不带reset()方法）
			$("#modalform")[0].reset();

		})

      //市场活动
		pageList(1,2);


		//查询
	  	$("#search").click(function () {
      //将查询信息保存到隐藏域中
	  		  $("#hidden-name").val($.trim($("#search-name").val()));
			$("#hidden-owner").val($.trim($("#search-username").val()));
			$("#hidden-startDate").val($.trim($("#search-startTime").val()));
			$("#hidden-endDate").val($.trim($("#search-endTime").val()));

			pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

		})

		//勾选单选框框，做全选操作
		$("#qx").click(function () {
			//选中input中，name等于xz的所有元素
			$("input[name=xz]").prop("checked",this.checked);
		})

		//如其他普通单选框已经被全选，那就勾选单选框
		$("#tbodypage").on("click",$("input[name=xz]"),function(){

			if ($("input[name=xz]").length==$("input[name=xz]:checked").length){

				$("#qx").prop("checked",true);
			}
			else {
				$("#qx").prop("checked",false);
			}

		})


		//删除操作
		$("#deletebtn").click(function () {
				var parm="";

				var $xz=$("input[name=xz]:checked");

			  if ($xz.length==0){
			  	alert("请勾选你要删除的列表");
			  }
              else {

              	  if (confirm("你确定要删除吗")) {
					  for (var i = 0; i < $xz.length; i++) {
						  parm += 'id=' + $($xz[i]).val();
						  if (i < $xz.length - 1) {
							  parm += '&';
						  }
					  }

					  $.ajax({
						  url: "user/workbench/delete",
						  data: parm,
						  dataType: "json",
						  type: "post",
						  success: function (data) {
							  if (data.success) {
								  pageList(1
										  ,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
							  } else {
								  alert("删除失败");
							  }

						  }
					  })
				  }
			  }
		})

		//修改模态窗口赋值
		$("#editBtn").click(function () {
			   var $xz=$("input[name=xz]:checked");

			   if ($xz.length==0){
			   	alert("请勾选你要修改的列表")
			   }else if ($xz.length>1){
			   	alert("勾选的列表数只能为一")
			   }
			   else{
				   $.ajax({
					   url : "user/workbench/edit",
					   data :{
					   	"id":$xz.val()
					   },
					   dataType:"json",
					   type:"post",
					   success:function (data) {
						   $("#edit-owner").empty();
                        $.each(data.ulist,function (i,n) {
							$("#edit-owner").append("<option value='"+n.id+"'>"+n.name+"</option>");
						})
					   	$("#edit-id").val(data.a.id);
					   	$("#edit-name").val(data.a.name);
					   	$("#edit-startTime").val(data.a.startDate);
					   	$("#edit-endTime").val(data.a.endDate);
					   	$("#edit-cost").val(data.a.cost);
					   	$("#edit-describe").val(data.a.description);
					   }
				   })

				   $("#editActivityModal").modal("show");
			   }
		})

          //update
       $("#update").click(function () {

		  $.ajax({
			  url : "user/workbench/update",
			  data :{
			  	    "id":$.trim($("#edit-id").val()),
				   "owner":$.trim($("#edit-owner").val()),
				   "name":$.trim($("#edit-name").val()),
				   "startDate":$.trim($("#edit-startTime").val()),
				   "endDate":$.trim($("#edit-endTime").val()),
				   "cost":$.trim($("#edit-cost").val()),
				   "description":$.trim($("#edit-describe").val())
			  },
			  dataType:"json",
			  type:"post",
			  success:function (data) {
			  	  if (data.success){
					  pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
							  ,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

					  $("#editActivityModal").modal("hide");
			  	  }else{
			  	  	alert("修改失败");
				  }
			  }
		  })
	  })

	});

	//pageNo是页码数   pageSize每页展现的记录数
	function  pageList(pageNo,pageSize) {
           //在再次点击其他页码时，如果全选复选框处于勾选状态，则改为不勾选
		 $("#qx").prop("checked",false);
		 //将隐藏域当中的对象赋值给查询框
		$("#search-name").val($.trim($("#hidden-name").val()));
		$("#search-username").val($.trim($("#hidden-owner").val()));
		$("#search-startTime").val($.trim($("#hidden-startDate").val()));
		$("#search-endTime").val($.trim($("#hidden-endDate").val()));
		$.ajax({
			type:"get",
			data:{
				"pageNo":pageNo,
				"pageSize":pageSize,
				"name":$.trim($("#search-name").val()),
				"owner":$.trim($("#search-username").val()),
				"startDate":$.trim($("#search-startTime").val()),
				"endDate":$.trim($("#search-endTime").val())
			},
			url:"user/workbench/pageList",
			dataType:"json",
			success:function(data) {
				/*$("#tbodypage").text("");*/
				var html="";
				$.each(data.dataList,function (i,n) {
					html+="<tr class='active'>";
					html+="<td><input type='checkbox' name='xz' value='"+n.id+"'/></td>";
					html+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='user/workbench/det?id="+n.id+"';\">"+n.name+"</a></td>";
					html+="<td>"+n.owner+"</td>";
					html+="<td>"+n.startDate+"</td>";
					html+="<td>"+n.endDate+"</td>";
					html+="</tr>";

					/*$("#tbodypage").append("<tr class='active'>")
							.append("<td><input type='checkbox' name='xz' value='"+n.id+"'/></td>")
							.append("<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detail.jsp';\">"+n.name+"</a></td>")
							.append("<td>"+n.owner+"</td>")
							.append("<td>"+n.startDate+"</td>")
							.append("<td>"+n.endDate+"</td>")
							.append("</tr>")*/
				})
				$("#tbodypage").html(html);


				 var totalPages=data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;

				$("#activityPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});


			}
		})

	}
</script>
</head>
<body>


   <!--隐藏域-->
   <input type="hidden" id="hidden-name">
   <input type="hidden" id="hidden-owner">
   <input type="hidden" id="hidden-startDate">
   <input type="hidden" id="hidden-endDate">



	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="modalform">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startTime">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endTime">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="savebtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					<input type="hidden" id="edit-id"/>
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">

								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startTime" value="2020-10-10">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endTime" value="2020-10-20">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>

					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="update">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-username">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control  time" type="text" id="search-startTime" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control time" type="text" id="search-endTime">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="search">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="addbtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deletebtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td ><input type="checkbox" id="qx"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="tbodypage">
						<%--<tr class="active">
							<td><input type="checkbox" /></td>
	<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">

				 <div id="activityPage">

				 </div>



		</div>

		</div>

		</div>
		</body>
		</html>