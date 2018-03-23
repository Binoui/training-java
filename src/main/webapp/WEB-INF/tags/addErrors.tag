<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${error != null && not empty error}">
	<div class="alert alert-danger">
		<strong>Error ! </strong><c:out value="${error}"/>
	</div>
</c:if>