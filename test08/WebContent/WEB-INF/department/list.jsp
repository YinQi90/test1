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
</style>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$()
			.ready(
					function() {
						var selectId = -1;
						$("#showAdd").click(function() {
							location.href = "dep?type=showAdd"
						})
						$("#showmodify").click(function() {
											if (selectId > -1) {
												location.href = "dep?type=showmodify&id="+ selectId
											} else {
												alert("请选中一条信息");
											}
										})
						$("tr").click(function() {
							$(this).toggleClass("select");
							//selectId=$(this).children().eq(0).text();
							selectId = $(this).data("id");
						})
						
						$("#delete").click(
								function() {
									if (selectId > -1) {
										location.href = "dep?type=delete&id="
												+ selectId
									} else {
										alert("请选中一条信息");
									}
								})
								
						$("#manageProject").click(
								function() {
									if (selectId > -1) {
										location.href = "d2p?depId="
												+ selectId
									} else {
										alert("请选中一条信息");
									}
								})
								
						$("#manageProject2").click(
								function() {
									if (selectId > -1) {
										location.href = "d2p?type=m2&depId="
												+ selectId
									} else {
										alert("请选中一条信息");
									}
								})
								
						$("#manageProject3").click(
								function() {
									if (selectId > -1) {
										location.href = "d2p?type=m3&depId="
												+ selectId
									} else {
										alert("请选中一条信息");
									}
								})
								
						$("#manageProject4").click(
								function() {
									if (selectId > -1) {
										location.href = "d2p?type=m4&depId="
												+ selectId
									} else {
										alert("请选中一条信息");
									}
								})	
								
						$("#manageProject5").click(
								function() {
									if (selectId > -1) {
										location.href = "d2p?type=m5&depId="
												+ selectId
									} else {
										alert("请选中一条信息");
									}
								})		

						
						if (${p.ye }<= 1) {
							$("#pre").addClass("disabled");
							$("#pre").find("a").attr("onclick", "return false");
						}

						if (${p.ye}>=${p.maxYe}) {
							$("#next").addClass("disabled");
							$("#next").find("a").attr("onclick", "return false");
						}
					})
</script>
</head>
<body>

	<div id="main">
		<form action="dep" class="form-horizontal" role="form" method="post">
			<div class="form-group">
				<div class="col-sm-3">
					<input type="text" class="form-control" name="name"
						placeholder="请输入名称" value=${c.name}>
				</div>
				<div class="col-sm-3">
					<input type="text" class="form-control" name="empCount"
						placeholder="请输人数" value=${c.empCount!=-1?c.empCount:''}>
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
					<th>名称</th>
					<th>人数</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="dep">
					<tr data-id="${dep.id}">
						<td>${dep.id}</td>
						<td>${dep.name}</td>
						<td><a href="emp?depId=${dep.id}">${dep.empCount}</a></td>
					</tr>
				</c:forEach>
			</tbody>

		</table>
		<button id="showAdd" type="button" class="btn btn-primary">增加</button>
		<button id="showmodify" type="button" class="btn btn-warning">修改</button>
		<button id="delete" type="button" class="btn btn-danger">删除</button>
		<button id="manageProject" type="button" class="btn btn-danger">项目管理</button>
		<button id="manageProject2" type="button" class="btn btn-danger">项目管理2</button>
		<button id="manageProject3" type="button" class="btn btn-danger">项目管理3</button>
		<button id="manageProject4" type="button" class="btn btn-danger">项目管理4</button>
		<button id="manageProject5" type="button" class="btn btn-danger">项目管理5</button>
		
		<div id="main2">
			<ul class="pagination">
				<li id="pre"><a
					href="dep?ye=${p.ye-1}&name=${c.name}&empCount=${c.empCount!=-1?c.empCount:''}">上一页</a></li>
				<c:forEach begin="${p.beginYe}" end="${p.endYe}" varStatus="status">
					<li <c:if test="${p.ye==status.index}" >class="active"</c:if>><a
						href="dep?ye=${status.index }&name=${c.name}&empCount=${c.empCount!=-1?c.empCount:''}">${status.index }</a></li>
				</c:forEach>
				<li id="next"><a
					href="dep?ye=${p.ye+1 }&name=${c.name}&empCount=${c.empCount!=-1?c.empCount:''}	">下一页</a></li>
			</ul>

		</div>
</body>
</html>