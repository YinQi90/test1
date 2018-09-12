<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style>
</style>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	if (self != top) {
		top.location = "user?type=showLogin";
	}

	$().ready(function() {
		$("#image").click(function() {
			$(this).attr("src", "user?type=randomImage&" + Math.random());
		})

	})
</script>
</head>
<body>

	<div
		style="width: 600px; margin: 20px auto; margin-top: 120px; background-color: #f4f4f4;">
		<form id="form" action="user?type=doLogin" method="post"
			class="form-horizontal">
			<div class="form-group">
				<label class="col-xs-2 control-label">账号：</label>
				<div class="col-xs-6">
					<input type="text" name="username" class="form-control"
						placeholder="请输入账号" />
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-2 control-label">密码：</label>
				<div class="col-xs-6">
					<input type="password" name="password" class="form-control"
						placeholder="请输入密码" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label">验证：</label>
				<div class="col-xs-3">
					<input type="text" name="random" class="form-control"
						placeholder="请输入验证码" />
				</div>
				<div class="col-xs-4">
					<img id="image" src="user?type=randomImage">
				</div>
			</div>

			<div id="mes" style="height: 30px;">${mes }</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">保存</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>