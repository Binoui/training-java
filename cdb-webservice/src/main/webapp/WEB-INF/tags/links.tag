<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@attribute name="target" required="true"%>
<%@attribute name="pageIndex" required="false"%>
<%@attribute name="itemsPerPage" required="false"%>
<%@attribute name="computerId" required="false"%>
<%@attribute name="search" required="false"%>
<%@attribute name="sortBy" required="false"%>
<%@attribute name="oldSort" required="false"%>
<%@attribute name="ascending" required="false"%>

<c:set var="path" value="/ComputerDatabase/computer/" />

<c:choose>
	<c:when test="${not empty target}">
		<c:choose>
			<c:when test="${target.equals('dashboard')}">
				<c:set var="path" value="${path.concat('dashboard?')}" />

				<c:if test="${search != null}">
					<c:set var="path" value="${path.concat('&search=').concat(search)}" />
				</c:if>

				<c:if test="${sortBy != null}">
					<c:choose>
						<c:when test="${not empty oldSort && oldSort.equals(sortBy) && ascending.matches('true|false')}">
							<c:set var="path"
								value="${path.concat('&ascending=').concat(not ascending)}" />
						</c:when>
						<c:otherwise>
							<c:set var="path"
								value="${path.concat('&ascending=').concat('true')}" />
						</c:otherwise>
					</c:choose>
					<c:set var="path" value="${path.concat('&sortBy=').concat(sortBy)}" />
				</c:if>
			</c:when>

			<c:when test="${target.equals('addComputer')}">
				<c:set var="path" value="${path.concat('add?')}" />
			</c:when>

			<c:when test="${target.equals('editComputer')}">
				<c:set var="path" value="${path.concat('edit?')}" />

				<c:if test="${(computerId != null) && computerId.matches('[0-9]+')}">
					<c:set var="path"
						value="${path.concat('&computerId=').concat(computerId)}" />
				</c:if>
			</c:when>

			<c:when test="${target.equals('delete')}">
				<c:set var="path" value="${path.concat('delete?')}" />
			</c:when>
			
			<c:when test="${target.equals('logout')}">
				<c:set var="path" value="${path.concat('logout?')}" />
			</c:when>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:out value="${path.concat('dashboard?')}" />
	</c:otherwise>
</c:choose>

<c:if test="${(pageIndex != null) && pageIndex.matches('[0-9]+')}">
	<c:set var="path"
		value="${path.concat('&pageNumber=').concat(pageIndex)}" />
</c:if>

<c:if test="${(itemsPerPage != null)}">
	<c:set var="path"
		value="${path.concat('&itemsPerPage=').concat(itemsPerPage)}" />
</c:if>

<c:out value="${path}" escapeXml="false" />