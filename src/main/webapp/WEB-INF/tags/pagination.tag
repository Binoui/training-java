<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %> 

<c:set var="range" scope="page"
	value="${pageCount < 5 ? pageCount : 5}" />

<c:set var="half" scope="page" value="${range / 2}" />
<c:set var="half" scope="page" value="${half - (half % 1)}" />

<c:set var="start" scope="page" value="${pageNumber - half}" />
<c:set var="stop" scope="page" value="${pageNumber + half}" />

<c:set var="tmp" scope="page" value="${start}" />

<c:set var="start" scope="page" value="${tmp <= 0 ? 0 : start}" />
<c:set var="stop" scope="page" value="${tmp <= 0 ? range-1 : stop}" />

<c:set var="tmp" scope="page" value="${stop}" />
<c:set var="stop" scope="page" value="${tmp > pageCount - 1 ? pageCount - 1 : stop }" />
<c:set var="stop" scope="page" value="${stop < 0 ? 0 : stop }" />

<li>
	<a href="<tags:links target="dashboard" pageNumber="${pageNumber - 1 < 0 ? 0 : pageNumber - 1}" itemsPerPage="${itemsPerPage}"/>"
	aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
</li>

<c:forEach var="i" begin="${start}" end="${stop}" step="1">
	<li>
		<a href="<tags:links target="dashboard" pageNumber="${i}" itemsPerPage="${itemsPerPage}"/>"><c:out value="${i + 1}"/></a> 
	</li>
</c:forEach> 

<li>
	<a href="<tags:links target="dashboard" pageNumber="${pageNumber > pageCount ? pageCount - 1 : pageNumber + 1}" itemsPerPage="${itemsPerPage}"/>"
	aria-label="Next"> <span aria-hidden="true">&raquo;</span></a>
</li>