/**
 * 
 */
function showPro(value) {
    //	value = value;
    //	if (head_value >= 0 && head_value <= 30) {
    //		
    //	} else if (head_value >= 40 && head_value <= 50) {
    //		
    //	} else if (head_value > 50 && head_value <= 60) {
    //		
    //	} else if (head_value > 60 && head_value <= 100) {
    //		
    //	}
    //	head_value = value;

    // $("#pro").css("width", head_value + "%");
    // console.log($("#pro").css("width"));
    // var p = $('#pro').width();
    // var p2 = $('#pro').parent().width();
    // var res = parseFloat(p)/parseFloat(p2)
    // console.log(res);
    // var t = $("#pro").css("width", head_value + "%").text(head_value + "%");
    // console.log(t.val());
    // 1先判断前值，然后删除，再判断后值，添加css

    $("#pro").removeClass("progress-bar bg-success");
    $("#pro").removeClass("progress-bar bg-info");
    $("#pro").removeClass("progress-bar bg-warning");
    $("#pro").removeClass("progress-bar bg-danger");
    if (value && value <= 40) {
        $("#pro").css("width", value + "%").text(value + "%");
        $("#pro").addClass("progress-bar bg-success");
    } else if (value > 40 && value <= 50) {
        $("#pro").css("width", value + "%").text(value + "%");
        $("#pro").addClass("progress-bar bg-info");
    } else if (value > 50 && value <= 60) {
        $("#pro").css("width", value + "%").text(value + "%");
        $("#pro").addClass("progress-bar bg-warning");
    } else if (value > 60 && value <= 100) {
        $("#pro").css("width", value + "%").text(value + "%");
        $("#pro").addClass("progress-bar bg-danger");
    }
}

//传入是json数组
//处理传值即可
function showPro2(arr) {
    for (var k in arr) {
        console.log(arr[k]);
        var d_name = arr[k].system.filesystem.device_name;
        var d_value = arr[k].system.filesystem.used.pct * 100;
        d_value = d_value.toFixed(2); //四舍五入2位
        var sp = d_name.split(":");
        console.log(sp[0]);
        //获取对象
        var dom = $("#pro" + sp[0] + "");
        dom.removeClass("progress-bar bg-success");
        dom.removeClass("progress-bar bg-info");
        dom.removeClass("progress-bar bg-warning");
        dom.removeClass("progress-bar bg-danger");
        if (d_value && d_value <= 40) {
            $(dom).css("width", d_value + "%").text(d_value + "%");
            $(dom).addClass("progress-bar bg-success");
        } else if (d_value > 40 && d_value <= 50) {
            $(dom).css("width", d_value + "%").text(d_value + "%");
            $(dom).addClass("progress-bar bg-info");
        } else if (d_value > 50 && d_value <= 60) {
            $(dom).css("width", d_value + "%").text(d_value + "%");
            $(dom).addClass("progress-bar bg-warning");
        } else if (d_value > 60 && d_value <= 100) {
            $(dom).css("width", d_value + "%").text(d_value + "%");
            $(dom).addClass("progress-bar bg-danger");
        }
    }
}
// console.log(arr[k]);
// //读取磁盘名字
// var d_name = arr[k].system.filesystem.device_name;
// var sp = d_name.split(":");
// console.log(sp[0]);
// var dom = '<div id="progress" class="progress" style="width: 300px;"><div id="pro' + sp[0] + '" class="progress-bar" style="width: 0%"></div></div>';
// $("#hk").before(dom);







// $("#pro").removeClass("progress-bar bg-success");
// $("#pro").removeClass("progress-bar bg-info");
// $("#pro").removeClass("progress-bar bg-warning");
// $("#pro").removeClass("progress-bar bg-danger");
// if (value && value <= 40) {
//     $("#pro").css("width", value + "%").text(value + "%");
//     $("#pro").addClass("progress-bar bg-success");
// } else if (value > 40 && value <= 50) {
//     $("#pro").css("width", value + "%").text(value + "%");
//     $("#pro").addClass("progress-bar bg-info");
// } else if (value > 50 && value <= 60) {
//     $("#pro").css("width", value + "%").text(value + "%");
//     $("#pro").addClass("progress-bar bg-warning");
// } else if (value > 60 && value <= 100) {
//     $("#pro").css("width", value + "%").text(value + "%");
//     $("#pro").addClass("progress-bar bg-danger");
// }