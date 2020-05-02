<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<fmt:message key='user_request_list_jsp.head.title' var="title" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<div id="wrapper">

		<div class="back-color"></div>
	
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
	
		<div class="group">
			<%@ include file="/WEB-INF/jspf/sidebar.jspf"%>

			<div id="content">
				<c:if test="${empty requestScope.userRequestBeans}"><fmt:message key='common.message.no_requests' /></c:if>
				<c:if test="${not empty requestScope.userRequestBeans}">
					<table id="user-requests-table">
						<tr>
							<th class="column-num">№</th>
							<th class="column-num"><fmt:message key="common.label.request_flight_id" /></th>
							<th class="column-word"><fmt:message key="common.label.request_flight_direction" /></th>
							<th class="column-word"><fmt:message key="common.label.request_car_model" /></th>
							<th class="column-num"><fmt:message key="common.label.request_car_engine_power" /></th>
							<th class="column-word"><fmt:message key="common.label.request_car_engine_type" /></th>
							<th class="column-num"><fmt:message key="common.label.request_car_num_of_seats" /></th>
						</tr>
						<c:set var="k" value="1" />
						<c:forEach var="requestBean" items="${requestScope.userRequestBeans}">
				
							<tr>
								<td class="column-num"><c:out value="${k}" /></td>
								<c:set var="k" value="${k+1}" />
								<td class="column-num"><a href="controller?command=flight&flightId=${requestBean.flightId}">${requestBean.flightId}</a></td>
								<td class="column-word">${requestBean.flightDirection}</td>
								<td class="column-word">${requestBean.carModelName}</td>
								<td class="column-num">${requestBean.carEnginePower}</td>
								<td class="column-word">${requestBean.carEngineTypeName}</td>
								<td class="column-num">${requestBean.carNumOfSeats}</td>
							</tr>
	
						</c:forEach>
						
					</table>
				</c:if>
			</div>
		</div>		

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
		
	</div>
</body>
</html>