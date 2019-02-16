/**
 * 
 */
function getData(url) {
    var k;
    $.ajax({
        type: "GET",
        url: "/" + url,
        async: false,
        data: "",
        success: function (data) {
            k = data;
            console.log(url + "---" + k);
        }
    });
    return k;
}






//传入是json数组
//处理传值即可
function showPro(arr) {
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








function Filesystem(time) {
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
        var dom = '<span>' + sp[0] + '</span><div id="progress" class="progress" style="width: 300px"><div id="pro' + sp[0] + '" class="progress-bar" style="width: 0%"></div></div><br>';
        $("#hk").before(dom);
    }
    //第一次赋值
    showPro(arr);

    setInterval(function () {
        showPro(eval("(" + getData("metricbeat/getFilesystem") + ")"));
    }, time);

}

//------------------process--------------------------
function process_memory(name, memory) {
    this.name = name;
    this.memory = memory;
}
//传入是json数组
//处理传值即可
function showProcess(arr) {
    for (var k = 0; k < arr.length; k++) {
        console.log(arr[k]);
        var d_name = arr[k].name;
        var d_value = arr[k].memory * 100;
        d_value = d_value.toFixed(2); //四舍五入2位

        var sp = d_name.split(".");
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
function Process(time) {
    var k = getData("metricbeat/getProcess");
    console.log("k:" + k.length)
    var arr = eval("(" + k + ")");
    console.log("arr:" + arr.length)

    var objectList = new Array();

    for (var a in arr) {
        //判断是否存在元素
        var flag = objectList.some(item=>{
            if(item.name == arr[a].name){
                return true
            }else{
                return false;
            }
            
        });
        if(flag){
            continue;
        }else{
            objectList.push(new process_memory(arr[a].system.process.name, arr[a].system.process.memory.rss.pct))
        }
    }
    objectList.sort(function (a, b) {
        return b.memory - a.memory;//大->小
    });

    //读取一次，生产dom
    for (var i = 0; i < objectList.length; i++) {
        var d_name = objectList[i].name;
        var sp = d_name.split(".");
        console.log(sp[0]);
        var dom = '<span>' + sp[0] + '</span><div id="progress" class="progress" style="width: 300px;"><div id="pro' + sp[0] + '" class="progress-bar" style="width: 0%"></div></div><br>';
        $("#process_hk").before(dom);
    }
    //第一次赋值
    showProcess(objectList);

    // setInterval(function() {
    //     showPro(eval("(" + getData("metricbeat/getFilesystem") + ")"));
    // }, time);

}






function Metric(dom, dom_name, url, time) {
    var dom = document.getElementById(dom);
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        tooltip: {
            formatter: "{a} <br/>{b} : {c}%"
        },
        //        toolbox: {
        //            feature: {
        //                restore: {},
        //                saveAsImage: {}
        //            }
        //        },
        series: [{
            startAngle: 180, //开始角度 左侧角度
            endAngle: 0, //结束角度 右侧

            name: 'memory指标',
            type: 'gauge',
            detail: {
                formatter: '{value}'
            },
            axisLine: { // 坐标轴线  
                lineStyle: { // 属性lineStyle控制线条样式  
                    color: [
                        [0.2, '#c23531'],
                        [0.8, '#63869e'],
                        [1, '#91c7ae']
                    ]
                }
            },
            data: [{
                value: 100,
                //                name: '内存使用率'
                name: dom_name
            }]
        }]
    };

    //首次读取
    option.series[0].data[0].value = getData(url);
    myChart.setOption(option, true);

    //定时轮询
    setInterval(
        function () {
            // option.series[0].data[0].value = (Math.random() * 100)
            // 		.toFixed(2) - 0;
            option.series[0].data[0].value = getData(url);
            myChart.setOption(option, true);
        }, time);;
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}
