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
            $(document).ready(function() {
                function getData(url) {
                    var k;
                    $.ajax({
                        type: "GET",
                        url: "/" + url,
                        async: false,
                        data: "hostname=zhengxian",
                        success: function(data) {
                            k = data;
                            console.log(k);
                        }
                    });
                    return k;
                }
                var k = getData("metricbeat/getFilesystem");
                console.log("k:" + k.length)
                var arr = eval("(" + k + ")");
                console.log("arr:" + arr.length)

                //读取一次，生产dom
                for (var k in arr) {
                    console.log(arr[k]);
                    //读取磁盘名字
                    var d_name = arr[k].system.filesystem.device_name;
                    var sp = d_name.split(":");
                    console.log(sp[0]);
                    var dom = '<div id="progress" class="progress" style="width: 300px;"><span>' + sp[0] + '</span><div id="pro' + sp[0] + '" class="progress-bar" style="width: 0%"></div></div>';
                    $("#hk").before(dom);
                }
                //第一次赋值
                showPro(arr);

                //定时循环



               // showPro(getData("metricbeat/getCPU"));
               setInterval(function() {
                    showPro(eval("(" + getData("metricbeat/getFilesystem") + ")"));
                }, 20000);









                $("#add").click(function() {
                   // var dom = '<div id="progress" class="progress" style="width: 300px;"><div id="pro" class="progress-bar" style="width: 0%"></div></div>';
                   // $("#progress").after(dom);

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
                $("#sub").click(function() {
                    $("#pro").parent().empty();
                    // if (head_value >= 0 && head_value <= 30) {
                    //     $("#pro").removeClass("progress-bar bg-success");
                    // } else if (head_value >= 40 && head_value <= 50) {
                    //     $("#pro").removeClass("progress-bar bg-info");
                    // } else if (head_value > 50 && head_value <= 60) {
                    //     $("#pro").removeClass("progress-bar bg-warning");
                    // } else if (head_value > 60 && head_value <= 100) {
                    //     $("#pro").removeClass("progress-bar bg-danger");
                    // }
                    // end_value -= 10;
                    // head_value = end_value;

                    // if (end_value && end_value <= 30) {
                    //     $("#pro").css("width", end_value + "%").text(end_value + "%");
                    //     $("#pro").addClass("progress-bar bg-success");
                    // } else if (end_value >= 40 && end_value <= 50) {
                    //     $("#pro").css("width", end_value + "%").text(end_value + "%");
                    //     $("#pro").addClass("progress-bar bg-info");
                    // } else if (end_value > 50 && end_value <= 60) {
                    //     $("#pro").css("width", end_value + "%").text(end_value + "%");
                    //     $("#pro").addClass("progress-bar bg-warning");
                    // } else if (end_value > 60 && end_value <= 100) {
                    //     $("#pro").css("width", end_value + "%").text(end_value + "%");
                    //     $("#pro").addClass("progress-bar bg-danger");
                    // }
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

        <hr>
        <div id="progress" class="progress" style="width: 300px;">
            <div id="pro" class="progress-bar" style="width: 0%"></div>
        </div>
        <br>

        <button id="add">add</button>
        <button id="sub">sub</button>
        <hr id="hk">
    </body>

    </html>