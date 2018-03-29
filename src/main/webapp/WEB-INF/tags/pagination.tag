<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %> 

<c:choose>
	<c:when test="${pageNumber <= 0}">
		<c:set var="previous" value="0"/>
		<c:set var="start" value="0"/>
		<c:set var="stop"  value="${pageCount - pageNumber > 4 ? 4 : pageCount - 1}"/>
		<c:set var="next"  value="${pageNumber >= pageCount - 1 ? pageNumber : pageNumber + 1}"/>
	</c:when>
	<c:when test="${pageNumber == 1}">
		<c:set var="previous" value="0"/>
		<c:set var="start" value="0"/>
		<c:set var="stop"  value="${pageCount - pageNumber > 4 ? 4 : pageCount - 1}"/>
		<c:set var="next"  value="${pageNumber >= pageCount - 1 ? pageNumber : pageNumber + 1}"/>
	</c:when>
	
	<c:when test="${pageNumber >= (pageCount - 1)}">
		<c:set var="previous" value="${pageNumber - 1}"/>
		<c:set var="start" value="${pageNumber > 4 ? pageNumber - 4 : 0}"/>
		<c:set var="stop"  value="${pageNumber}"/>
		<c:set var="next"  value="${pageNumber}"/>
	</c:when>
	
	<c:when test="${pageNumber == (pageCount - 2)}">
		<c:set var="previous" value="${pageNumber - 1}"/>
		<c:set var="start" value="${pageNumber > 3 ? pageNumber - 3 : 0}"/>
		<c:set var="stop"  value="${pageNumber + 1}"/>
		<c:set var="next"  value="${pageNumber + 1}"/>
	</c:when>
	
	<c:otherwise>
		<c:set var="previous" value="${pageNumber - 1}"/>
		<c:set var="start" value="${pageNumber - 2}"/>
		<c:set var="stop"  value="${pageNumber + 2}"/>
		<c:set var="next" value="${pageNumber + 1}"/>
	</c:otherwise>
</c:choose>

<li>
	<a href="<tags:links target="dashboard" pageIndex="0" search="${param.search}" itemsPerPage="${itemsPerPage}"/>"
	aria-label="First"><span aria-hidden="true">&laquo;</span></a>
</li>

<li>
	<a href="<tags:links target="dashboard" search="${param.search}" pageIndex="${previous}" itemsPerPage="${itemsPerPage}"/>"
	aria-label="Previous"><span aria-hidden="true">&lt;</span></a>
</li>

<c:if test="${start < 0}">
	<c:set var="stop" value="0"/>
</c:if>

<c:if test="${stop < 0}">
	<c:set var="stop" value="0"/>
</c:if>

<c:forEach var="i" begin="${start}" end="${stop}" step="1">
	<li>	
		<a
			<c:choose>
				<c:when test="${i == pageNumber}">
					 class=disabled style=color:grey
				</c:when>
				<c:otherwise>
					 href="<tags:links target="dashboard" sortBy="${sortBy}" pageIndex="${i}" search="${param.search}" itemsPerPage="${itemsPerPage}"/>"	
				</c:otherwise>
			</c:choose> 
		><c:out value="${i + 1}"/>
		</a> 
	</li>
</c:forEach> 

<li>
	<a href="<tags:links target="dashboard" pageIndex="${next}" search="${param.search}" itemsPerPage="${itemsPerPage}"/>"
	aria-label="Next"> <span aria-hidden="true">&gt;</span></a>
</li>

<li>
	<a href="<tags:links target="dashboard" pageIndex="${pageCount - 1}" search="${param.search}" itemsPerPage="${itemsPerPage}"/>"
	aria-label="Last"> <span aria-hidden="true">&raquo;</span></a>
</li>