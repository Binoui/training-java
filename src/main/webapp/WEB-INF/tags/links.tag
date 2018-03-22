<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@attribute name="target" required="true"%>
<%@attribute name="pageNumber" required="false"%>
<%@attribute name="itemsPerPage" required="false"%>

<c:set var="path" value="/ComputerDatabase/"/>

<c:choose>
	<c:when test="${not empty target}">
		<c:choose>
			<c:when test="${target.equals('dashboard')}">
				<c:set var="path" value="${path.concat('Dashboard?')}"/>
			</c:when>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:out value="${path.concat('Dashboard?')}"/>
	</c:otherwise>
</c:choose>

<c:if test="${not empty pageNumber and pageNumber.matches('[0-9]+')}">
	<c:set var="path" value="${path.concat('&pageNumber=').concat(pageNumber)}"/>
</c:if>

<c:if test="${not empty itemsPerPage and itemsPerPage.matches('[0-9]+')}">
	<c:set var="path" value="${path.concat('&itemsPerPage=').concat(itemsPerPage)}"/>
</c:if>


<c:out value="${path}" escapeXml="false"/>