<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Filesystem</title>
	<link href="./bootstrap-4.0.0-dist/css/bootstrap.min.css" rel="stylesheet">
	<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
		<script type="text/javascript" src="./js/filesystem.js"></script>
	<script type="text/javascript" src="./bootstrap-4.0.0-dist/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function () {
			var head_value = 0,end_value = 0;
			// function showPro(value) {
			// 	var end_value = value;
			// 	if (head_value >= 0 && head_value <= 30) {
			// 		$("#pro").removeClass("progress-bar bg-success");
			// 	} else if (head_value >= 40 && head_value <= 50) {
			// 		$("#pro").removeClass("progress-bar bg-info");
			// 	} else if (head_value > 50 && head_value <= 60) {
			// 		$("#pro").removeClass("progress-bar bg-warning");
			// 	} else if (head_value > 60 && head_value <= 100) {
			// 		$("#pro").removeClass("progress-bar bg-danger");
			// 	}
			// 	var head_value = end_value;

			// 	// $("#pro").css("width", head_value + "%");
			// 	// console.log($("#pro").css("width"));
			// 	//var p = $('#pro').width();
			// 	//var p2 = $('#pro').parent().width();
			// 	//var res = parseFloat(p)/parseFloat(p2)
			// 	//console.log(res);
			// 	// var t = $("#pro").css("width", head_value + "%").text(head_value + "%");
			// 	// console.log(t.val());
			// 	//1先判断前值，然后删除，再判断后值，添加css

			// 	if (end_value && end_value <= 40) {
			// 		$("#pro").css("width", end_value + "%").text(end_value + "%");
			// 		$("#pro").addClass("progress-bar bg-success");
			// 	} else if (end_value > 40 && end_value <= 50) {
			// 		$("#pro").css("width", end_value + "%").text(end_value + "%");
			// 		$("#pro").addClass("progress-bar bg-info");
			// 	} else if (end_value > 50 && end_value <= 60) {
			// 		$("#pro").css("width", end_value + "%").text(end_value + "%");
			// 		$("#pro").addClass("progress-bar bg-warning");
			// 	} else if (end_value > 60 && end_value <= 100) {
			// 		$("#pro").css("width", end_value + "%").text(end_value + "%");
			// 		$("#pro").addClass("progress-bar bg-danger");
			// 	}
			// }

			//setInterval(function () {showPro(Math.random() * 100);}, 2000);
			
			function getData(url) {
				var k;
				$.ajax({
					type : "GET",
					url : "/" + url,
					async : false,
					data : "",
					success : function(data) {
						k = data;
						console.log(k);
					}
				});
				return k;
			}
			var k =getData("getFilesystem");
			console.log("k:"+k.length)
			var arr = eval("(" + k + ")");
			console.log("arr:"+arr.length)
			
			
			
			
			
			
			
			
			
			
			$("#add").click(function () {
				if (head_value >= 0 && head_value <= 30) {
					$("#pro").removeClass("progress-bar bg-success");
				} else if (head_value >= 40 && head_value <= 50) {
					$("#pro").removeClass("progress-bar bg-info");
				} else if (head_value > 50 && head_value <= 60) {
					$("#pro").removeClass("progress-bar bg-warning");
				} else if (head_value > 60 && head_value <= 100) {
					$("#pro").removeClass("progress-bar bg-danger");
				}
				end_value += 1;
				head_value = end_value;

				if (end_value && end_value <= 30) {
					$("#pro").css("width", end_value + "%").text(end_value + "%");
					$("#pro").addClass("progress-bar bg-success");
				} else if (end_value >= 40 && end_value <= 50) {
					$("#pro").css("width", end_value + "%").text(end_value + "%");
					$("#pro").addClass("progress-bar bg-info");
				} else if (end_value > 50 && end_value <= 60) {
					$("#pro").css("width", end_value + "%").text(end_value + "%");
					$("#pro").addClass("progress-bar bg-warning");
				} else if (end_value > 60 && end_value <= 100) {
					$("#pro").css("width", end_value + "%").text(end_value + "%");
					$("#pro").addClass("progress-bar bg-danger");
				}
			});
			$("#sub").click(function () {
				if (head_value >= 0 && head_value <= 30) {
					$("#pro").removeClass("progress-bar bg-success");
				} else if (head_value >= 40 && head_value <= 50) {
					$("#pro").removeClass("progress-bar bg-info");
				} else if (head_value > 50 && head_value <= 60) {
					$("#pro").removeClass("progress-bar bg-warning");
				} else if (head_value > 60 && head_value <= 100) {
					$("#pro").removeClass("progress-bar bg-danger");
				}
				end_value -= 10;
				head_value = end_value;

				if (end_value && end_value <= 30) {
					$("#pro").css("width", end_value + "%").text(end_value + "%");
					$("#pro").addClass("progress-bar bg-success");
				} else if (end_value >= 40 && end_value <= 50) {
					$("#pro").css("width", end_value + "%").text(end_value + "%");
					$("#pro").addClass("progress-bar bg-info");
				} else if (end_value > 50 && end_value <= 60) {
					$("#pro").css("width", end_value + "%").text(end_value + "%");
					$("#pro").addClass("progress-bar bg-warning");
				} else if (end_value > 60 && end_value <= 100) {
					$("#pro").css("width", end_value + "%").text(end_value + "%");
					$("#pro").addClass("progress-bar bg-danger");
				}
			});


			// 		$("#prog").addClass("progress-bar bg-success");
			// 	} else if (value >= 30 && value <= 60) {
			// 		$("#prog").removeClass("progress-bar bg-success");
			// 		$("#prog").addClass("progress-bar bg-info");
			// 	} else if (value >= 60 && value <= 90) {
			// 		$("#prog").removeClass("progress-bar bg-info");
			// 		$("#prog").addClass("progress-bar bg-warning");
			// 	} else if (value >= 90 && value < 100) {
			// 		$("#prog").removeClass("progress-bar bg-warning");
			// 		$("#prog").addClass("progress-bar bg-danger");
			// 	} else {
			// 		setTimeout(reset, 3000);
			// 		return;
			// 	}


			// var value = 0;
			// var time = 50;
			// //进度条复位函数
			// function reset() {
			// 	value = 0
			// 	$("#prog").removeClass("progress-bar-success").css("width",
			// 		"0%").text("等待启动");
			// 	//setTimeout(increment,5000);
			// }
			// //百分数增加，0-30时为红色，30-60为黄色，60-90为蓝色，>90为绿色
			// function increment() {
			// 	value += 1;
			// 	$("#prog").css("width", value + "%").text(value + "%");
			// 	if (value >= 0 && value <= 30) {
			// 		$("#prog").addClass("progress-bar bg-success");
			// 	} else if (value >= 30 && value <= 60) {
			// 		$("#prog").removeClass("progress-bar bg-success");
			// 		$("#prog").addClass("progress-bar bg-info");
			// 	} else if (value >= 60 && value <= 90) {
			// 		$("#prog").removeClass("progress-bar bg-info");
			// 		$("#prog").addClass("progress-bar bg-warning");
			// 	} else if (value >= 90 && value < 100) {
			// 		$("#prog").removeClass("progress-bar bg-warning");
			// 		$("#prog").addClass("progress-bar bg-danger");
			// 	} else {
			// 		setTimeout(reset, 3000);
			// 		return;
			// 	}

			// 	st = setTimeout(increment, time);
			// }

			// increment();
			// //进度条停止与重新开始
			// $("#stop").click(function () {
			// 	if ("stop" == $("#stop").val()) {
			// 		//$("#prog").stop();
			// 		clearTimeout(st);
			// 		$("#prog").css("width", "0%").text("等待启动");
			// 		$("#stop").val("start").text("重新开始");
			// 	} else if ("start" == $("#stop").val()) {
			// 		increment();
			// 		$("#stop").val("stop").text("停止");
			// 	}
			// });
			// //进度条暂停与继续
			// $("#pause").click(function () {
			// 	if ("pause" == $("#pause").val()) {
			// 		//$("#prog").stop();
			// 		clearTimeout(st);
			// 		$("#pause").val("goon").text("继续");
			// 	} else if ("goon" == $("#pause").val()) {
			// 		increment();
			// 		$("#pause").val("stop").text("暂停");
			// 	}
			// });
		});
	</script>
</head>

<body>
	<!-- <div class="progress progress-striped active">
		<div id="prog" class="progress-bar" role="progressbar" aria-valuenow="" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
			<span id="proglabel">正在启动，请稍后......</span>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-4 col-sm-6">
			<button id="pause" class="btn btn-primary" value="pause">暂停</button>
			<button id="stop" class="btn btn-primary" value="stop">停止</button>
			<button id="goon" class="btn btn-primary">继续<button>
		</div>
	</div> -->

	<hr>
	<div class="progress" style="width: 300px;">
		<div id="pro" class="progress-bar" style="width: 0%"></div>
	</div>
	<br>
	<!-- <div class="progress">
		<div class="progress-bar bg-success" style="width: 40%"></div>
	</div>
	<br>
	<div class="progress">
		<div class="progress-bar bg-info" style="width: 50%"></div>
	</div>
	<br>
	<div class="progress">
		<div class="progress-bar bg-warning" style="width: 60%"></div>
	</div>
	<br>
	<div class="progress">
		<div class="progress-bar bg-danger" style="width: 70%"></div>
	</div> -->
	<button id="add">add</button>
	<button id="sub">sub</button>
</body>

</html>