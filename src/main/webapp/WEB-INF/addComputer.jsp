<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value="static/css/bootstrap.min.css"/>"
	rel="stylesheet" media="screen">
<link href="<c:url value="static/css/font-awesome.css"/>"
	rel="stylesheet" media="screen">
<link href="<c:url value="static/css/main.css"/>" rel="stylesheet"
	media="screen">

</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand"
				href="<tags:links target="dashboard" itemsPerPage="${itemsPerPage}"/>">
				Application - Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<tags:addErrors />
					<h1>Add Computer</h1>
					<form
						action="<tags:links target="addComputer" itemsPerPage="${itemsPerPage}"/>"
						method="POST" id="createComputerForm">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" name="computerName"
									id="computerName" placeholder="Computer name" 
									pattern="^[\wÀ-ÿ]+[\wÀ-ÿ_\-'\+\* ]+$" required="required">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" name="introduced"
									data-validation-option="true" 
									data-validation="date" data-validation-format="yyyy-mm-dd"
									id="introduced" placeholder="Introduced date">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" name="discontinued"
									id="discontinued" data-validation="date"
									data-validation-option="true" 
									data-validation-format="yyyy-mm-dd"
									placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" name="companyId" id="companyId">
									<option value="0" selected>None</option>
									<c:forEach var="company" items="${companies}">
										<option value="<c:out value="${company.id}"/>">
											<c:out value="${company.name}" />
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a
								href="<tags:links target="dashboard" itemsPerPage="${itemsPerPage}"/>"
								class="btn btn-default">Cancel</a>
						</div>
					</form>
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
			lang:'en',
			modules:'html5'
		})
	</script>
</body>
</html>