<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style>
#main {
	width: 600px;
	margin: 0 auto;
	margin-top: 20px
}

#main2 {
	width: 600px;
	margin: 0 auto;
}

#emp .select {
	background: #81DAF5;
}

#emp td {
	width: 200px;
}

#emp input {
	width: 100px;
}

#emp select {
	width: 100px;
}

#emp img {
	width: 30px;
	height: 30px;
}

#bigPhoto {
	display: none;
	position: absolute;
}
#bigPhoto img{
	width: 150px;
	height: 150px;
}
</style>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$()
			.ready(
					function() {
						var selectId = -1;
						$("#showAdd").click(function() {
							location.href = "emp?type=showAdd"
						})
						$("#showAdd2").click(function() {
							location.href = "emp?type=showAdd2"
						})
						$("#showmodify")
								.click(
										function() {
											if (selectId > -1) {
												location.href = "emp?type=showmodify&id="
														+ selectId
											} else {
												alert("请选中一条信息");
											}
										})
						$("#delete").click(
								function() {
									if (selectId > -1) {
										location.href = "emp?type=delete&id="
												+ selectId
									} else {
										alert("请选中一条信息");
									}
								})

						function doBatch(type) {
							var length = $("#emp .select").length;
							// var ids = "";
							var ids = new Array();
							if (length > 0) {
								$("#emp .select").each(
										function(index, element) {
											//字符串拼接
											//ids += $(this).data("id") + ",";
											ids.push($(this).data("id"));
										})
								//ids = ids.substring(0, ids.length - 1);
								location.href = "emp?type=" + type + "&ids="
										+ ids;
							} else {
								alert("请选中数据");
							}
						}

						$("#deleteBatch").click(function() {
							doBatch("deleteBatch");

						})

						$("#showmodifyBatch1").click(function() {
							doBatch("showmodifyBatch1");

						})

						$("#showmodifyBatch2").click(function() {
							doBatch("showmodifyBatch2");

						})

						$("tr").click(function() {
							$(this).toggleClass("select");
							//selectId=$(this).children().eq(0).text();
							selectId = $(this).data("id");
						})

						$("tr")
								.dblclick(
										function() {

											$(this).unbind("dblclick");
											$(this).unbind("click");
											$(this).addClass("modifyEmp");
											var name = $(this).children().eq(0)
													.text()
											$(this)
													.children()
													.eq(0)
													.html(
															"<input type ='text' name= 'name' value='"+name+"'/>");
											var sex = $(this).children().eq(1)
													.text()
											var select = "";
											if (sex == "男") {
												select = "<select name='sex'><option selected value='男'>男</option><option value='女'>女</option></select>";
											} else {
												select = "<select name='sex'><option value='男'>男</option><option selected value='女'>女</option></select>";
											}
											$(this).children().eq(1).html(
													select);
											var age = $(this).children().eq(2)
													.text()
											$(this)
													.children()
													.eq(2)
													.html(
															"<input type='text' name='age' value='"+age+"'/>");
										})

						$("#modifyBatch3")
								.click(
										function() {
											var emps = "";
											$(".modifyEmp")
													.each(
															function(index,
																	element) {
																var id = $(this)
																		.data(
																				"id");
																var name = $(
																		this)
																		.find(
																				"[name = name]")
																		.val();
																var sex = $(
																		this)
																		.find(
																				"[name = sex]")
																		.val();
																var age = $(this).find("[name = age]").val();
																emps += id+ ","+ name+ ","+ sex+ ","+ age+ ";";
															})
											emps = emps.substring(0,
													emps.length - 1)
											window.location.href = "emp?type=modifyBatch2&emps="
													+ emps;
										})
						

						if (${p.ye }<= 1) {
							$("#pre").addClass("disabled");
							$("#pre").find("a").attr("onclick", "return false");
						}

						if (${p.ye}>=${p.maxYe}) {
							$("#next").addClass("disabled");
							$("#next").find("a").attr("onclick", "return false");
						}
						
						$("#emp img").hover(function(event){
							var photo = $(this).attr("src");
							$("#bigPhoto img").attr("src",photo);
							$("#bigPhoto").show();
							$("#bigPhoto").css({left:event.pageX+20,top:event.pageY+5});
							
						},function(){
							$("#bigPhoto").hide();
						})
						
					})
</script>
</head>
<body>

	<div id="main">
		<form action="emp" class="form-horizontal" role="form" method="post">
			<div class="form-group">
				<div class="col-sm-3">
					<input type="text" class="form-control" name="name"
						placeholder="输入姓名" value=${c.name}>
				</div>
				<div class="col-sm-2">
					<select name="sex" class="form-control">
						<option value="">性别</option>
						<option value="男" <c:if test="${c.sex =='男'}">selected</c:if>>男</option>
						<option value="女" <c:if test="${c.sex =='女'}">selected</c:if>>女</option>
					</select>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="age" placeholder="年龄"
						value=${c.age!=-1?c.age:''}>
				</div>


				<div class="col-sm-3">
					<select name="depId" class="form-control">
						<option value="">部门</option>
						<c:forEach items="${depList}" var="dep">
							<option value="${dep.id }"
								<c:if test="${dep.id ==c.dep.id}">selected</c:if>>${dep.name}</option>

						</c:forEach>
					</select>
				</div>


				<div class="form-group">
					<div class=col-sm-3">
						<button type="submit" class="btn btn-primary">搜索</button>
					</div>
				</div>
			</div>

		</form>
		<table id="emp" class="table table-bordered table-striped table-hover">
			<thead>
				<tr>
					<th>ID</th>
					<th>姓名</th>
					<th>性别</th>
					<th>年龄</th>
					<th>部门</th>
					<th>照片</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="emp">
					<tr data-id="${emp.id}">
						<td>${emp.id}</td>
						<td>${emp.name}</td>
						<td>${emp.sex}</td>
						<td>${emp.age}</td>
						<td>${emp.dep.name}</td>
						<td><c:if test="${not empty emp.photo}">
								<img src="pic/${emp.photo }" />
							</c:if></td>
					</tr>
				</c:forEach>
			</tbody>

		</table>
		<button id="showAdd" type="button" class="btn btn-primary">增加</button>
		<button id="showAdd2" type="button" class="btn btn-primary">增加2</button>
		<button id="showmodify" type="button" class="btn btn-warning">修改</button>
		<button id="delete" type="button" class="btn btn-danger">删除</button>
		<button id="showmodifyBatch1" type="button" class="btn btn-primary">批量修改1</button>
		<button id="showmodifyBatch2" type="button" class="btn btn-primary">批量修改2</button>
		<button id="modifyBatch3" type="button" class="btn btn-primary">批量修改3</button>
		<button id="deleteBatch" type="button" class="btn btn-primary">批量删除</button>
	</div>
	<div id="main2">
		<ul class="pagination">
			<li><a
				href="emp?ye=1&name=${c.name}&sex=${c.sex}&age=${c.age!=-1?c.age:''}&depId=${c.dep.id}">首页</a></li>
			<li id="pre"><a
				href="emp?ye=${p.ye-1}&name=${c.name}&sex=${c.sex}&age=${c.age!=-1?c.age:''}&depId=${c.dep.id}">上一页</a></li>
			<c:forEach begin="${p.beginYe}" end="${p.endYe}" varStatus="status">
				<li <c:if test="${p.ye==status.index}" >class="active"</c:if>><a
					href="emp?ye=${status.index }&name=${c.name}&sex=${c.sex}&age=${c.age!=-1?c.age:''}&depId=${c.dep.id}">${status.index }</a></li>
			</c:forEach>
			<li id="next"><a
				href="emp?ye=${p.ye+1 }&name=${c.name}&sex=${c.sex}&age=${c.age!=-1?c.age:''}&depId=${c.dep.id}">下一页</a></li>
			<li><a
				href="emp?ye=${p.maxYe}&name=${c.name}&sex=${c.sex}&age=${c.age!=-1?c.age:''}&depId=${c.dep.id}">尾页</a></li>
		</ul>
	</div>
	<div id="bigPhoto">
		<img src="" />
	</div>
</body>
</html>