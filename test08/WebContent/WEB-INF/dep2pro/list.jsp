<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f"%>
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

#pro .select {
	background: #81DAF5;
}

#pro td {
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
	$().ready(function() {
		var selectId = -1;
		$("#add").click(function() {
			var proId=$("#seclectPro").val();
			location.href = "d2p?type=add&&depId=${dep.id}&proId="+proId;
		}) 
		 
		
		<c:if test="${f:length(noList)==0}">
		$("#add").unbind("click");
		$("#add").addClass("disabled");
		</c:if>

		$("#delete").click(function() {
			if (selectId > -1) {
				location.href = "d2p?type=delete&depId=${dep.id}&proId=" + selectId;
			} else {
				alert("请选中一条信息");
			}
		})

		$("tr").click(function() {
							$(this).toggleClass("select");
							//selectId=$(this).children().eq(0).text();
							selectId = $(this).data("id");
						})
	})
</script>
</head>
<body>

	<div id="main">
		<h3>${dep.name}</h3>

		<table id="pro" class="table table-bordered table-striped table-hover">
			<thead>
				<tr>
					<th>ID</th>
					<th>名称</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="pro">
					<tr data-id="${pro.id}">
						<td>${pro.id}</td>
						<td>${pro.name}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>

			<div class="col-sm-4">
				<select class="form-control" id="seclectPro">
					<c:forEach items="${noList }" var="pro">
						<option value="${pro.id }">${pro.name}</option>
					</c:forEach>
				</select>

			</div>
			<c:if test="">

			</c:if>
			<button id="add" type="button" class="btn btn-primary">增加</button>
			<button id="delete" type="button" class="btn btn-danger">删除</button>
		</div>
	</div>

</body>
</html>