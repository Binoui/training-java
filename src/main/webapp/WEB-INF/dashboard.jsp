<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
		<a class="navbar-brand" href='<tags:links target="dashboard" ></tags:links>'> Application -
			Computer Database </a>
	</div>
	</header>

	<section id="main">
	<div class="container">

		<tags:addErrors/>


		<h1 id="homeTitle">
			<c:out value="${computerCount}" />
			Computers found
		</h1>


		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="#" method="GET" class="form-inline">

					<input type="search" id="searchbox" name="search"
						class="form-control" placeholder="Search name" /> <input
						type="submit" id="searchsubmit" value="Filter by name"
						class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer"
					href="<tags:links target="addComputer" itemsPerPage="${itemsPerPage}"/>">
					Add Computer </a> <a class="btn btn-default" id="editComputer" href="#"
					onclick="$.fn.toggleEditMode();"> Edit </a>
			</div>
		</div>
	</div>

	<form id="deleteForm" action="<tags:links target="delete" pageIndex="${pageNumber}" itemsPerPage="${itemsPerPage}" />" method="POST">
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
					<th>Computer name</th>
					<th>Introduced date</th>
					<!-- Table heade￼
					r for Discontinued Date -->
					<th>Discontinued date</th>
					<!-- Table header for Company -->
					<th>Company</th>

				</tr>
			</thead>
			<!-- Browse attribute computers -->
			<tbody id="results">
				<c:forEach items="${computers}" var="computer" varStatus="status">
					<tr>
						<td class="editMode"><input type="checkbox" name="cb"
							class="cb" value="${computer.id}"></td>
						<td><a href="<tags:links target="editComputer" computerId="${computer.id}" itemsPerPage="${itemsPerPage}"/>" onclick=""> <c:out
									value="${computer.name}" />
						</a></td>
						<td><c:out value="${computer.introduced}" /></td>
						<td><c:out value="${computer.discontinued}" /></td>
						<td><c:out value="${computer.company.name}" /></td>
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
					href="<tags:links target="dashboard" pageIndex="0" itemsPerPage="${itemsPerPage}"/>">
					<button type="button" class="btn btn-default">
						<c:out value="${itemsPerPage}" />
					</button>
				</a>
			</c:forEach>
		</div>
	</footer>
	<script src="<c:url value="static/js/jquery.min.js"/>"></script>
	<script src="<c:url value="static/js/bootstrap.min.js"/>" /></script>
	<script src="<c:url value="static/js/dashboard.js"/>"></script>

</body>
</html>