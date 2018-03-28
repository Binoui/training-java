<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@attribute name="target" required="true"%>
<%@attribute name="pageIndex" required="false"%>
<%@attribute name="itemsPerPage" required="false"%>
<%@attribute name="computerId" required="false"%>

<c:set var="path" value="/ComputerDatabase/"/>

<c:choose>
	<c:when test="${not empty target}">
		<c:choose>
			<c:when test="${target.equals('dashboard')}">
				<c:set var="path" value="${path.concat('Dashboard?')}"/>
			</c:when>
			
			<c:when test="${target.equals('addComputer')}">
				<c:set var="path" value="${path.concat('AddComputer?')}"/>
			</c:when>
			
			<c:when test="${target.equals('editComputer')}">
				<c:set var="path" value="${path.concat('EditComputer?')}"/>
				
				<c:if test="${(computerId != null) && computerId.matches('[0-9]+')}">
					<c:set var="path" value="${path.concat('&computerId=').concat(computerId)}"/>
				</c:if>
			</c:when>
			
			<c:when test="${target.equals('delete')}">
				<c:set var="path" value="${path.concat('DeleteComputer?')}"/>
			</c:when>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:out value="${path.concat('Dashboard?')}"/>
	</c:otherwise>
</c:choose>

<c:if test="${(pageIndex != null) && pageIndex.matches('[0-9]+')}">
	<c:set var="path" value="${path.concat('&pageNumber=').concat(pageIndex)}"/>
</c:if>

<c:if test="${(itemsPerPage != null) && itemsPerPage.matches('[0-9]+')}">
	<c:set var="path" value="${path.concat('&itemsPerPage=').concat(itemsPerPage)}"/>
</c:if>


<c:out value="${path}" escapeXml="false"/>