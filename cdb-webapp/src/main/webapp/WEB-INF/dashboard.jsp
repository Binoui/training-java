<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
			href='<tags:links target="dashboard" ></tags:links>'> <spring:message code="dashboard.title"/> </a>
	</div>
	</header>

	<section id="main">
	<div class="container">

		<tags:addErrors />

		<h1 id="homeTitle">
			<c:out value="${computerCount}" />
			<spring:message code="dashboard.computersFound" />
		</h1>


		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm"
					action="<tags:links target="dashboard" itemsPerPage="${itemsPerPage}" search="${search}" pageIndex="${pageNumber}"/>"
					method="GET" class="form-inline">
					<input type="search" id="searchbox" name="search"
						class="form-control" placeholder="<spring:message code="dashboard.search"/>" /> <input
						type="submit" id="searchsubmit" value="<spring:message code="dashboard.filter"/>"
						class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer"
					href="<tags:links target="addComputer" itemsPerPage="${itemsPerPage}"/>">
					<spring:message code="dashboard.addComputer"/> </a> <a class="btn btn-default" id="editComputer" href="#"
					onclick="$.fn.toggleEditMode();"> <spring:message code="dashboard.delete"/> </a>
			</div>
		</div>
	</div>

	<form id="deleteForm"
		action="<tags:links target="delete" pageIndex="${pageNumber}" search="${search}" itemsPerPage="${itemsPerPage}" />"
		method="POST">
		<input type="hidden" name="selection" value="">
	</form>

	<div class="container" style="margin-top: 10px;">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->

					<th class="editMode" style="width: 60px; height: 22px;"><input
						type="checkbox" id="selectall" /> <span
						style="vertical-align: top;"> - <a href="#"
							id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
								class="fa fa-trash-o fa-lg"></i>
						</a>
					</span></th>
					<th><a
						href="<tags:links target="dashboard" sortBy="name" oldSort="${param.sortBy}" ascending="${param.ascending}" search="${param.search}" itemsPerPage="${itemsPerPage}"/>">
							<spring:message code="computerName" />
					</a></th>
					<th><a
						href="<tags:links target="dashboard" sortBy="introduced" oldSort="${param.sortBy}" ascending="${param.ascending}" search="${param.search}" itemsPerPage="${itemsPerPage}"/>">
							<spring:message code="introduced" />
					</a></th>
					<th><a
						href="<tags:links target="dashboard" sortBy="discontinued" oldSort="${param.sortBy}" ascending="${param.ascending}" search="${param.search}" itemsPerPage="${itemsPerPage}"/>">
							<spring:message code="discontinued" />
					</a></th>
					<th><a
						href="<tags:links target="dashboard" sortBy="company" oldSort="${param.sortBy}" ascending="${param.ascending}" search="${param.search}" itemsPerPage="${itemsPerPage}"/>">
							<spring:message code="company" />
					</a></th>

				</tr>
			</thead>
			<!-- Browse attribute computers -->
			<tbody id="results">
				<c:forEach items="${computers}" var="computer" varStatus="status">
					<tr>
						<td class="editMode"><input type="checkbox" name="cb"
							class="cb" value="${computer.id}"></td>
						<td><a
							href="<tags:links target="editComputer" search="${search}" computerId="${computer.id}" itemsPerPage="${itemsPerPage}"/>"
							onclick=""> <c:out value="${computer.name}" />
						</a></td>
						<td><c:out value="${computer.introduced}" /></td>
						<td><c:out value="${computer.discontinued}" /></td>
						<td><c:out value="${computer.companyDTO.name}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</section>

	<footer class="navbar-fix
					<ed-bottom">
	<div class="container text-center">
		<ul class="pagination">
			<tags:pagination />
		</ul>

		<div class="btn-group btn-group-sm pull-right" role="group">
			<c:forEach var="itemsPerPage" items="10,50,100" varStatus="status">
				<a
					href="<tags:links target="dashboard" search="${search}" pageIndex="0" itemsPerPage="${itemsPerPage}"/>">
					<button type="button" class="btn btn-default">
						<c:out value="${itemsPerPage}" />
					</button>
				</a>
			</c:forEach>
		</div>
	</footer>
	<script src="<c:url value="/static/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/static/js/dashboard.js"/>"></script>

</body>
</html>