<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

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
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand"
				href="<tags:links target="dashboard" itemsPerPage="${itemsPerPage}"/>">
				<spring:message code="dashboard.title" />
			</a>
			<form action="/ComputerDatabase/logout" method="get">
				<button type="submit" style="margin-top: 8px; float: right"
					class="btn btn-default">Logout</button>
			</form>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<tags:addErrors />
					<h1>
						<spring:message code="add.title" />
					</h1>
					<form:form action="/ComputerDatabase/computer/add" method="POST"
						modelAttribute="computerDTO" id="createComputerForm"
						name="createComputerForm">
						<fieldset>
							<div class="form-group">
								<form:label for="computerName" path="name">
									<spring:message code="computerName" />
								</form:label>
								<form:input type="text" class="form-control" path="name"
									name="computerName" id="computerName"
									placeholder="Computer name"
									pattern="^[\wÀ-ÿ]+[\wÀ-ÿ_\-'\+\* ]+$" required="required" />
							</div>
							<div class="form-group">
								<form:label for="introduced" path="introduced">
									<spring:message code="introduced" />
								</form:label>
								<form:input type="date" class="form-control" path="introduced"
									name="introduced" data-validation-optional="true"
									data-validation="date" data-validation-format="yyyy-mm-dd"
									id="introduced" placeholder="Introduced date" />
							</div>
							<div class="form-group">
								<form:label for="discontinued" path="discontinued">
									<spring:message code="discontinued" />
								</form:label>
								<form:input type="date" class="form-control" path="discontinued"
									name="discontinued" id="discontinued" data-validation="date"
									data-validation-optional="true"
									data-validation-format="yyyy-mm-dd"
									placeholder="Discontinued date" />
							</div>
							<div class="form-group">
								<form:label for="companyId" path="companyDTO">
									<spring:message code="company" />
								</form:label>
								<form:select class="form-control" name="companyId"
									id="companyId" path="companyDTO.id">
									<form:option value="0" label="None" />
									<form:options items="${companies}" itemValue="id" />
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit"
								value="<spring:message code="add.addButton"/>"
								class="btn btn-primary"> or <a
								href="<tags:links target="dashboard" itemsPerPage="${itemsPerPage}"/>"
								class="btn btn-default"><spring:message
									code="addedit.cancel" /></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script
		src="http://cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
	<script>
		$('#createComputerForm').validate({
			lang : 'en',
			modules : 'html5'
		})
	</script>
</body>
</html>