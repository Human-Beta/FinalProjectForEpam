<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<fmt:setLocale value="${param.locale}" scope="session" />

<fmt:setBundle basename="resources"/>

<c:set var="currentLocale" value="${param.locale}" scope="session" />

<c:set var="url" value="/controller?command=${param.forwardCommand}" />

<%-- <c:if test="${not empty param.sort}">
	<c:set var="url" value="${url}&sort=${param.sort}" />
</c:if>
--%>
<mytags:redirectToCommand commandName="${param.forwardCommand}" sort="${param.sort}" />

<%-- <c:redirect context="${pageContext.request.contextPath}" url="${url}" />
 --%>
 