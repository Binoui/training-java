<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Error 404</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<spring:url value="/static/css/bootstrap.min.css"/>"
	rel="stylesheet" media="screen">
<link href="<spring:url value="/static/css/font-awesome.css"/>"
	rel="stylesheet" media="screen">
<link href="<spring:url value="/static/css/main.css"/>" rel="stylesheet"
	media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<tags:links target="dashboard"/>">
				<spring:message code="dashboard.title" />
			</a> <span style="float: right; color: white;"></span>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="alert alert-danger">
				<spring:message code="error404.message" />
				<br /> <a href="<tags:links target="dashboard"/>"><spring:message
						code="error.mainpage" /></a>
			</div>
		</div>
	</section>

	<script src="<c:url value="/static/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/static/js/bootstrap.min.js"/>" /></script>
	<script src="<c:url value="/static/js/dashboard.js"/>"></script>
	
</body>
</html>