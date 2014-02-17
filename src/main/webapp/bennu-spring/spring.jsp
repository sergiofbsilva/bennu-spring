<!DOCTYPE html>
<html>
	<head lang="pt">
		<meta charset="utf-8">
		<script type="text/javascript" src="<%=request.getContextPath() + "/bennu-portal/js/jquery.min.js"%>"></script>
		<script type="text/javascript" src="<%=request.getContextPath() + "/bennu-portal/portal.js"%>"></script>
	</head>
	<body style="display:none;" class="body">
		<div id="portal-container">
			<jsp:include page="<%=request.getAttribute("bennu$targetView").toString()%>" />
		</div>
	</body>
</html>
