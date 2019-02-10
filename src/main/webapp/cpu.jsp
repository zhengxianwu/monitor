<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <!DOCTYPE html>
    <html style="height: 100%">

    <head>
        <meta charset="UTF-8">
        <title>Cpu</title>
        <!-- 默认静态文件路劲static -->
        <script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
        <script type="text/javascript" src="./echarts/echarts.min.js"></script>
        <script type="text/javascript" src="./echarts/echarts-gl.min.js"></script>
        <script type="text/javascript" src="./echarts/ecStat.min.js"></script>
        <script type="text/javascript" src="./echarts/dataTool.min.js"></script>
        <script type="text/javascript" src="./echarts/china.js"></script>
        <script type="text/javascript" src="./echarts/world.js"></script>
        <script type="text/javascript" src="./echarts/api.js"></script>
        <script type="text/javascript" src="./echarts/bmap.min.js"></script>
        <script type="text/javascript" src="./echarts/simplex.js"></script>
        <script type="text/javascript">
            function getData(url) {
                var k;
                $.ajax({
                    type: "GET",
                    url: "/" + url,
                    async: false,
                    data: "",
                    success: function(data) {
                        k = data;
                        console.log(k);
                    }
                });
                return k;
            }
            $(document).ready(function() {

            });
        </script>
    </head>

    <body style="height: 100%; margin: 0">


        <div id="container" style="height: 100%"></div>


        <script type="text/javascript">
            function cpu(dom, url, time) {
                var dom = document.getElementById(dom);
                var myChart = echarts.init(dom);
                var app = {};
                option = null;
                option = {
                    tooltip: {
                        formatter: "{a} <br/>{b} : {c}%"
                    },
                    toolbox: {
                        feature: {
                            restore: {},
                            saveAsImage: {}
                        }
                    },
                    series: [{
                        startAngle: 180, //开始角度 左侧角度
                        endAngle: 0, //结束角度 右侧

                        name: 'cpu指标',
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
                            name: 'CPU使用率'
                        }]
                    }]
                };

                //首次读取
                option.series[0].data[0].value = getData(url);
                myChart.setOption(option, true);

                //定时轮询
                setInterval(
                    function() {
                        // option.series[0].data[0].value = (Math.random() * 100)
                        // 		.toFixed(2) - 0;
                        option.series[0].data[0].value = getData(url);
                        myChart.setOption(option, true);
                    }, time);;
                if (option && typeof option === "object") {
                    myChart.setOption(option, true);
                }
            }


            cpu("container", "metricbeat/getCPU", 60000);

            // var dom = document.getElementById("container");
            // var myChart = echarts.init(dom);
            // var app = {};
            // option = null;
            // option = {
            //     tooltip: {
            //         formatter: "{a} <br/>{b} : {c}%"
            //     },
            //     toolbox: {
            //         feature: {
            //             restore: {},
            //             saveAsImage: {}
            //         }
            //     },
            //     series: [{
            //         startAngle: 180, //开始角度 左侧角度
            //         endAngle: 0, //结束角度 右侧

            //         name: 'cpu指标',
            //         type: 'gauge',
            //         detail: {
            //             formatter: '{value}'
            //         },
            //         axisLine: { // 坐标轴线  
            //             lineStyle: { // 属性lineStyle控制线条样式  
            //                 color: [
            //                     [0.2, '#c23531'],
            //                     [0.8, '#63869e'],
            //                     [1, '#91c7ae']
            //                 ]
            //             }
            //         },
            //         data: [{
            //             value: 100,
            //             name: 'CPU使用率'
            //         }]
            //     }]
            // };

            // setInterval(
            //     function() {
            //         // option.series[0].data[0].value = (Math.random() * 100)
            //         // 		.toFixed(2) - 0;
            //         option.series[0].data[0].value = getData("metricbeat/getCPU");
            //         myChart.setOption(option, true);
            //     }, 60000);;
            // if (option && typeof option === "object") {
            //     myChart.setOption(option, true);
            // }
        </script>
    </body>

    </html> c