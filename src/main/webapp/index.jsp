<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Filesystem</title>
<link rel="stylesheet" href="./jquery-ui-1.12.1.custom/jquery-ui.css" />
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script src="./jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
<style type="text/css">
.ui-progressbar {
	background: green;
	padding: 1px;
}

.ui-progressbar-value {
	background: LightBlue;
}
</style>
</head>
<body>

	<div id="progressbar"></div>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#progressbar").progressbar({
				value : 10
			});
		});
	</script>
</body>
</html>