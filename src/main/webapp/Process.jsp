<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
</head>

<body>

	<script type="text/javascript">
		function process_memory(name, memory) {
			this.name = name;
			this.memory = memory;
		}
		$(document).ready(function () {
			function getData(url) {
				var k;
				$.ajax({
					type: "GET",
					url: "/" + url,
					async: false,
					data: "",
					success: function (data) {
						k = data;
						//console.log(k);
					}
				});
				return k;
			}

			var objectList = new Array();


			var k = getData("metricbeat/getProcess");
			//console.log("k:" + k.length)
			var arr = eval("(" + k + ")");
			//console.log("arr:" + arr.length)
			for (var a in arr) {
				// console.log(arr[a]);
				//console.log(arr[a].system.process.name + ":" + arr[a].system.process.memory.rss.pct);
				objectList.push(new process_memory(arr[a].system.process.name,arr[a].system.process.memory.rss.pct))
			}

			objectList.sort(function(a,b){
				return b.memory-a.memory;//大->小
			});

			for(var i=0;i<objectList.length;i++){
            	document.writeln(objectList[i].name + "---" + objectList[i].memory);
            }

		});


	</script>

</body>

</html>