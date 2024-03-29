<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>monitor</title>

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


<script type="text/javascript"
	src="./bootstrap-4.0.0-dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="bootstrap-4.0.0-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="dist/font-awesome.min.css">
<link rel="stylesheet" href="dist/sidebar-menu.css">
<style type="text/css">
.main-sidebar {
	min-height: 100%;
	height: 100%;
	width: 230px;
	background-color: #222d32;
}

</style>


</head>

<body>
	<br>
	<div class="container-fiuled">
		<div class="row">
			<div class="col-md-2">
				<aside class="main-sidebar">
					<section class="sidebar">
						<ul class="sidebar-menu">
							<li class="header">MAIN NAVIGATION</li>
							<li class="treeview"><a href="#"> <i
									class="fa fa-dashboard"></i> <span>Dashboard</span> <i
									class="fa fa-angle-left pull-right"></i>
							</a>
								<ul class="treeview-menu">
									<li><a href="" id="systemInfo"><i
											class="fa fa-circle-o"></i>系统信息</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i>
											Dashboard v2</a></li>
								</ul></li>
							<li class="treeview"><a href="#"> <i
									class="fa fa-files-o"></i> <span>Layout Options</span> <span
									class="label label-primary pull-right">4</span>
							</a>
								<ul class="treeview-menu" style="display: none;">
									<li><a href="#"><i class="fa fa-circle-o"></i> Top
											Navigation</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Boxed</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Fixed</a></li>
									<li class=""><a href="#"><i class="fa fa-circle-o"></i>
											Collapsed Sidebar</a></li>
								</ul></li>
							<li><a href="#"> <i class="fa fa-th"></i> <span>Widgets</span>
									<small class="label pull-right label-info">new</small>
							</a></li>
							<li class="treeview"><a href="#"> <i
									class="fa fa-pie-chart"></i> <span>Charts</span> <i
									class="fa fa-angle-left pull-right"></i>
							</a>
								<ul class="treeview-menu">
									<li><a href="#"><i class="fa fa-circle-o"></i> ChartJS</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Morris</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Flot</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Inline
											charts</a></li>
								</ul></li>
							<li class="treeview"><a href="#"> <i
									class="fa fa-laptop"></i> <span>UI Elements</span> <i
									class="fa fa-angle-left pull-right"></i>
							</a>
								<ul class="treeview-menu">
									<li><a href="#"><i class="fa fa-circle-o"></i> General</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Icons</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Buttons</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Sliders</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i>
											Timeline</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Modals</a></li>
								</ul></li>
							<li class="treeview"><a href="#"> <i class="fa fa-edit"></i>
									<span>Forms</span> <i class="fa fa-angle-left pull-right"></i>
							</a>
								<ul class="treeview-menu">
									<li><a href="#"><i class="fa fa-circle-o"></i> General
											Elements</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i>
											Advanced Elements</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Editors</a></li>
								</ul></li>
							<li class="treeview"><a href="#"> <i class="fa fa-table"></i>
									<span>Tables</span> <i class="fa fa-angle-left pull-right"></i>
							</a>
								<ul class="treeview-menu">
									<li><a href="#"><i class="fa fa-circle-o"></i> Simple
											tables</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Data
											tables</a></li>
								</ul></li>
							<li><a href="#"> <i class="fa fa-calendar"></i> <span>Calendar</span>
									<small class="label pull-right label-danger">3</small>
							</a></li>
							<li><a href="#"> <i class="fa fa-envelope"></i> <span>Mailbox</span>
									<small class="label pull-right label-warning">12</small>
							</a></li>
							<li class="treeview"><a href="#"> <i
									class="fa fa-folder"></i> <span>Examples</span> <i
									class="fa fa-angle-left pull-right"></i>
							</a>
								<ul class="treeview-menu">
									<li><a href="#"><i class="fa fa-circle-o"></i> Invoice</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Profile</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Login</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i>
											Register</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i>
											Lockscreen</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> 404
											Error</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> 500
											Error</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Blank
											Page</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Pace
											Page</a></li>
								</ul></li>
							<li class="treeview"><a href="#"> <i class="fa fa-share"></i>
									<span>Multilevel</span> <i class="fa fa-angle-left pull-right"></i>
							</a>
								<ul class="treeview-menu">
									<li><a href="#"><i class="fa fa-circle-o"></i> Level
											One</a></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Level
											One <i class="fa fa-angle-left pull-right"></i></a>
										<ul class="treeview-menu">
											<li><a href="#"><i class="fa fa-circle-o"></i> Level
													Two</a></li>
											<li><a href="#"><i class="fa fa-circle-o"></i> Level
													Two <i class="fa fa-angle-left pull-right"></i></a>
												<ul class="treeview-menu">
													<li><a href="#"><i class="fa fa-circle-o"></i>
															Level Three</a></li>
													<li><a href="#"><i class="fa fa-circle-o"></i>
															Level Three</a></li>
												</ul></li>
										</ul></li>
									<li><a href="#"><i class="fa fa-circle-o"></i> Level
											One</a></li>
								</ul></li>
							<li><a href="#"><i class="fa fa-book"></i> <span>Documentation</span></a></li>
							<li class="header">LABELS</li>
							<li><a href="#"><i class="fa fa-circle-o text-red"></i>
									<span>Important</span></a></li>
							<li><a href="#"><i class="fa fa-circle-o text-yellow"></i>
									<span>Warning</span></a></li>
							<li><a href="#"><i class="fa fa-circle-o text-aqua"></i>
									<span>Information</span></a></li>
						</ul>
					</section>
				</aside>

			</div>
			<div class="col-md-10">
				<div class="row">
					<div class="col-md-5">
						<div id="momory" style="height: 500px; width: 500px;"></div>
					</div>
					<div class="col-md-5">
						<div id="cpu" style="height: 500px; width: 500px;"></div>
					</div>

				</div>

				<div class="row">
					<div class="col-md-5">
						<h1>磁盘状态</h1>
						<hr id="hk">
					</div>
					
					<div class="col-md-3">
						<h1>磁盘状态</h1>
						<hr id="process_hk">
					</div>
				</div>

			</div>
		</div>


	</div>




	<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
	<script src="dist/sidebar-menu.js"></script>
	<script type="text/javascript" src="./js/Metricbeat.js"></script>
	<script>
		$.sidebarMenu($('.sidebar-menu'))

		$("#systemInfo").click(function() {
			$("#container").load("memory");
			alert(123);
		});

		Metric("momory", "内存使用率", "metricbeat/getMemory", 60000);
		Metric("cpu", "CPU使用率", "metricbeat/getCPU", 60000);
		Filesystem(60000);
		Process(60000);
	</script>


</body>

</html>