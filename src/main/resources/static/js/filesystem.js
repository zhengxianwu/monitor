/**
 * 
 */


//传入是json数组
//处理传值即可
function showPro(arr) {
    for (var k in arr) {
        console.log(arr[k]);
        var d_name = arr[k].system.filesystem.device_name;
        var d_value = arr[k].system.filesystem.used.pct * 100 / 10;
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
