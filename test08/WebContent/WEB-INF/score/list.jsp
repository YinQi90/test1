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
</style>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	
</script>
</head>
<body>

	<div id="main">
		<form action="emp" class="form-horizontal" role="form" method="post">
			<div class="form-group">
				<div class="col-sm-2">
					<input type="text" class="form-control" name="name"
						placeholder="姓名" value=${c.emp.name}>
				</div>

				<div class="col-sm-2">
					<select name="depId" class="form-control">
						<option value="">部门</option>
						<c:forEach items="${depList}" var="dep">
							<option value="${dep.id }"
								<c:if test="${dep.id ==c.emp.dep.id}">selected</c:if>>${dep.name}</option>
						</c:forEach>
					</select>
				</div>
				
				<div class="col-sm-3">
					<select name="proId" class="form-control">
						<option value="">项目</option>
						<c:forEach items="${proList}" var="pro">
							<option value="${pro.id }"
								<c:if test="${pro.id ==c.pro.id}">selected</c:if>>${pro.name}</option>
						</c:forEach>
					</select>
				</div>

				<div class="form-group">
					<div class="col-sm-2">
						<input type="text" class="form-control" name="name"
							placeholder="成绩" value=${c.value}>
					</div>

					<div class="col-sm-2">
						<select name="grade" class="form-control">
							<option value="">等级</option>
							<c:forEach items="${grades}" var="grade">
								<option value="${grade}"
									<c:if test="${grade ==c.grade}">selected</c:if>>${grade.value}</option>
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

		<table id="sc" class="table table-bordered table-striped table-hover">
			<thead>
				<tr>
					<th>姓名</th>
					<th>部门</th>
					<th>项目</th>
					<th>成绩</th>
					<th>等级</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="sc">
					<tr data-id="${sc.id}">
						<td>${sc.emp.name}</td>
						<td>${sc.emp.dep.name}</td>
						<td>${sc.pro.name}</td>
						<td>${sc.value}</td>
						<td>${sc.grade}</td>
					</tr>
				</c:forEach>
			</tbody>

		</table>

	</div>
	<div id="main2">
		<ul class="pagination">
			<li><a href="sc?ye=1">首页</a></li>
			<li id="pre"><a href="sc?ye=${p.ye-1}">上一页</a></li>
			<c:forEach begin="${p.beginYe}" end="${p.endYe}" varStatus="status">
				<li <c:if test="${p.ye==status.index}" >class="active"</c:if>><a
					href="sc?ye=${status.index }">${status.index }</a></li>
			</c:forEach>
			<li id="next"><a href="sc?ye=${p.ye+1 }">下一页</a></li>
			<li><a href="sc?ye=${p.maxYe}">尾页</a></li>
		</ul>


	</div>
</body>
</html>