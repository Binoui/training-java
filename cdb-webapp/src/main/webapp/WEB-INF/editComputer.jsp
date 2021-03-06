<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value="/static/css/bootstrap.min.css"/>"
	rel="stylesheet" media="screen">
<link href="<c:url value="/static/css/font-awesome.css"/>"
	rel="stylesheet" media="screen">
<link href="<c:url value="/static/css/main.css"/>" rel="stylesheet"
	media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand"
				href="<tags:links target="dashboard" itemsPerPage="${itemsPerPage}"/>">
				<spring:message code="dashboard.title" />
			</a>

			<form action="<tags:links target="logout"/>" method="get">
				<button type="submit" style="margin-top: 8px; float: right"
					class="btn btn-default">Logout</button>
			</form>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<form:form action="/cdb-webapp/computer/edit" method="POST"
						modelAttribute="computerDTO" id="editComputerForm"
						name="editComputerForm">
						<div class="label label-default pull-right">
							id:
							<c:out value="${computerDTO.id}" />
						</div>
						<tags:addErrors />
						<h1>
							<spring:message code="edit.title" />
						</h1>

						<fieldset>
							<form:input type="hidden" value="${computer.id}"
								name="computerId" id="computerId" path="id" />

							<div class="form-group">
								<form:label for="computerName" path="name">
									<spring:message code="computerName" />
								</form:label>
								<form:input type="text" class="form-control" path="name"
									name="computerName" id="computerName"
									placeholder="Computer name" value="${computer.name}"
									pattern="^[\wÀ-ÿ]+[\wÀ-ÿ_\-'\+\* ]+$" required="required" />
							</div>
							<div class="form-group">
								<form:label for="introduced" path="introduced">
									<spring:message code="introduced" />
								</form:label>
								<form:input type="date" class="form-control" path="introduced"
									name="introduced" data-validation-optional="true"
									data-validation="date" data-validation-format="yyyy-mm-dd"
									value="${computer.introduced}" id="introduced"
									placeholder="Introduced date" />
							</div>
							<div class="form-group">
								<form:label for="discontinued" path="discontinued">
									<spring:message code="discontinued" />
								</form:label>
								<form:input type="date" class="form-control" path="discontinued"
									name="discontinued" id="discontinued" data-validation="date"
									data-validation-optional="true"
									data-validation-format="yyyy-mm-dd"
									value="${computer.discontinued}"
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
							<input type="submit" value="Edit" class="btn btn-primary">
							<spring code="edit.editButton" />
							</input> or <a
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
		$('#editComputerForm').validate({
			lang : 'en',
			modules : 'html5'
		})
	</script>
</body>
</html>