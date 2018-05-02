<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<spring:url value="/static/css/bootstrap.min.css"/>"
	rel="stylesheet" media="screen">
<link href="<spring:url value="/static/css/font-awesome.css"/>"
	rel="stylesheet" media="screen">
<link href="<spring:url value="/static/css/main.css"/>" rel="stylesheet"
	media="screen">
</head>
<body onload='document.loginForm.username.focus();'>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand"
				href='<tags:links target="dashboard" ></tags:links>'> <spring:message
					code="dashboard.title" />
			</a>
		</div>
	</header>
	<section id="main">
		<div class="container">

			<tags:addErrors />
			
			<c:if test="${logout != null && not empty logout}">
				<div class="alert alert-success">
					<strong><spring:message code="logout.message"></spring:message></strong>
				</div>
			</c:if>

			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
					<spring:message code="login.header" text="Login" />
					</h1>
					<form:form action="${loginUrl}" modelAttribute="user" method="post"
						class="form-horizontal">
						<fieldset>
							<div class="form-group">
								<label for="username"> <spring:message code="login.name"
										text="Username" />
								</label> <input type="text" class="form-control" id="username"
									name="username"
									placeholder="<spring:message code="login.name" text="" />">
							</div>
							<div class="form-group">
								<label for="password"> <spring:message code="login.pass"
										text="Password" />
								</label> <input type="password" class="form-control" id="password"
									name="password"
									placeholder="<spring:message code="login.pass" text="" />">
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" name="submit"
								value="<spring:message code="login.header" text="Login" />"
								class="btn btn-primary">
						</div>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script src="<c:url value="/static/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/static/js/dashboard.js"/>"></script>
</body>
</html>