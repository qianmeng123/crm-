<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <base href="<%=basePath%>">
    <title>Insert title here</title>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="ECharts/echarts.min.js"></script>

    <script type="text/javascript">

        $(function() {

            $.ajax({
                url:"user/chart/transaction",
                data:{

                },
                dataType:"json",
                type:"get",
                success:function (data) {
                    getCharts(data);
                }
            });
        });


        function getCharts(data) {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            // 指定图表的配置项和数据
            option = {
                title: {
                    text: 'Funnel'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c}%'
                },
                toolbox: {
                    feature: {
                        dataView: { readOnly: false },
                        restore: {},
                        saveAsImage: {}
                    }
                },
                legend: {
                    data: ['Show', 'Click', 'Visit', 'Inquiry', 'Order']
                },
                series: [
                    {
                        name: 'Funnel',
                        type: 'funnel',
                        left: '10%',
                        top: 60,
                        bottom: 60,
                        width: '80%',
                        min: 0,
                        max: data.total,
                        minSize: '0%',
                        maxSize: '100%',
                        sort: 'descending',
                        gap: 2,
                        label: {
                            show: true,
                            position: 'inside'
                        },
                        labelLine: {
                            length: 10,
                            lineStyle: {
                                width: 1,
                                type: 'solid'
                            }
                        },
                        itemStyle: {
                            borderColor: '#fff',
                            borderWidth: 1
                        },
                        emphasis: {
                            label: {
                                fontSize: 20
                            }
                        },
                        data:data.dataList

                        /*[
                            { value: 60, name: 'Visit' },
                            { value: 40, name: 'Inquiry' },
                            { value: 20, name: 'Order' },
                            { value: 80, name: 'Click' },
                            { value: 100, name: 'Show' }
                        ]*/
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }

    </script>
</head>
<body>
<div id="main" style="width: 600px;height:400px;"></div>
</body>
</html>