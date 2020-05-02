<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<c:set var="title" value="Error" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<c:if test="${not empty requestScope.errorMessage}">
		<h3>${requestScope.errorMessage}</h3>
	</c:if>
</body>
</html>