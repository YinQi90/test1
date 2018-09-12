<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>员工管理</title>
<style>
#main {
	overflow: hedden;
}

a {
	color: #fff;
	text-decoration: none;
}

#left {
	width: 300px;
	float: left;
	height: 600px;
	padding-left: 20px;
}

#right {
	width: 800px;
	float: left;
	height: 600px;
}

#top, #bottom {
	clear: both;
	height: 80px;
	background: #337ab7;
	height: 80px;
	position: relative;
}

#count {
	width: 20px;
	height: 20px;
	color: #fff;
	background: red;
	font-weight: bold;
	position: absolute;
	right: 10px;
	bottom: 10px;
}

#top h1 {
	color: #fff;
	margin-left: 100px;
	line-height: 80px;
}

.yi {
	width: 160px;
	height: 40px;
	background: #337ab7;
	color: #fff;
	margin-top: 10px;
	border-radius: 3px;
	text-align: center;
	line-height: 40px;
}

.er {
	list-style: none;
}

.er a {
	color: #000;
}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	var websocket = null;

	//判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		websocket = new WebSocket("ws://192.168.0.156:8080/test08/websocket");
	} else {
		alert('没有建立websocket连接')
	}

	//连接发生错误的回调方法
	websocket.onerror = function() {
		//setMessage("错误");
	};

	//连接成功建立的回调方法
	websocket.onopen = function(event) {
		//	setMessage("建立连接");
	}

	//接收到消息的回调方法
	websocket.onmessage = function(event) {
		 $("#count").html(event.data);
	}

	//连接关闭的回调方法
	websocket.onclose = function() {
		//setMessage("close");
	}

	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口。
	window.onbeforeunload = function() {
		websocket.close();
	}


	//关闭连接
	function closeWebSocket() {
		websocket.close();
	}



	$().ready(function() {

		$(".yi").click(function() {
			$(this).next().slideToggle(500);
		})

	})
</script>
</head>
<body>
	<div id="container">
		<div id="top">
			<h1>员工管理系统</h1>
			<div id="count">${applicationScope.num}</div>
		</div>
		<div id="main">
			<div id="left">
				<div class="yi">员工管理</div>
				<ul class="er">
					<li><a href="emp" target="right">员工管理</a></li>
					<li><a href="emp?type=showAdd" target="right">添加员工</a></li>
				</ul>
				<div class="yi">部门管理</div>
				<ul class="er">
					<li><a href="dep" target="right">部门管理</a></li>
					<li><a href="dep?type=showAdd" target="right">添加部门</a></li>
				</ul>
				<div class="yi">项目管理</div>
				<ul class="er">
					<li><a href="pro" target="right">项目管理</a></li>
					<li><a href="pro?type=showAdd" target="right">添加项目</a></li>
				</ul>
				<div class="yi">绩效管理</div>
				<ul class="er">
					<li><a href="sc" target="right">绩效查看</a></li>
					<li><a href="sc?type=manage" target="right">绩效管理</a></li>
				</ul>

			</div>
			<iframe id="right" name="right" scrolling="no" frameborder="0"
				src="emp"></iframe>
		</div>
		<div id="bottom"></div>
	</div>
</body>
</html>