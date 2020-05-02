<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<%@ page import="com.google.gson.Gson"  %>

<html>
<fmt:message key='all_request_list_jsp.head.title' var="title"/>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<div id="wrapper">

		<div class="back-color"></div>

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<div class="group">
			<%@ include file="/WEB-INF/jspf/sidebar.jspf"%>

			<div id="content">
			
				<div id="overlay-car-list" class="overlay">
					<div class=overlay-background onclick="overlayOff('overlay-car-list')"></div>	
				
					<div id="window-overlay-car-list" class="window">
						<div class="prop">
							<span class="alias"><fmt:message key="common.label.car_model" />:</span>
							<span id="car-model"></span>
						</div>
						<div class="prop">
							<span class="alias"><fmt:message key="common.label.car_power_of_engine" />:</span>
							<div>
								<span id="car-engine-power"></span>
								<span>±</span>
								<input type="number" id="ep-range" value=0 min="0" max="50" oninput="filterCarList()">
							</div>
						</div>
						
						<div class="prop">
							<span class="alias"><fmt:message key="common.label.car_type_of_engine" />:</span>
							<span id="car-engine-type"></span>
						</div>
						
						<div class="prop">
							<span class="alias"><fmt:message key="common.label.car_number_of_seats" />:</span>
							<span id="car-num-of-seats"></span>
						</div>
						
						<div id="overlay-car-list-table">
							<table>
								<tr>
									<th class="column-num"><fmt:message key="common.label.id" /></th>
									<th class="column-word"><fmt:message key="common.label.car_model" /></th>
									<th class="column-num"><fmt:message key="common.label.car_power_of_engine" /></th>
									<th class="column-word"><fmt:message key="common.label.car_type_of_engine" /></th>
									<th class="column-num"><fmt:message key="common.label.car_number_of_seats" /></th>
									<th></th>
								</tr>
								<c:forEach var="carBean" items="${carBeans}">
									<tr class="car-row">
										<td class="column-num">${carBean.id}</td>
										<td class="column-word">${carBean.modelName}</td>
										<td class="column-num">${carBean.enginePower}</td>
										<td class="column-word">${carBean.engineTypeName}</td>
										<td class="column-num">${carBean.numOfSeats}</td>
										<td>
											<form action="controller" method="post">
												<input type="hidden" name="command" value="assignCarAndUserForFlight">
												<input type="hidden" name="carId" value="${carBean.id}">
												<input class="flight-id" type="hidden" name="flightId">
												<input class="user-id" type="hidden" name="userId">
												<input class="request-id" type="hidden" name="requestId">
												<button type="submit">assign</button>
											</form>
										</td>
									</tr>
								</c:forEach>	
							</table>	
						</div>
					</div>
				</div>
				<c:if test="${empty requestScope.requestBeansMap}"><fmt:message key='common.message.no_requests' /></c:if>
				<c:if test="${not empty requestScope.requestBeansMap}">
					<table id="all-requests-table">
						<tr>
							<th class="column-num">№</th>
							<th class="column-num"><fmt:message key="common.label.id" /></th>
							<th class="column-word"><fmt:message key="common.label.request_user_login" /></th>
							<th class="column-num"><fmt:message key="common.label.request_flight_id" /></th>
							<th class="column-word"><fmt:message key="common.label.request_flight_direction" /></th>
							<th class="column-word"><fmt:message key="common.label.request_car_model" /></th>
							<th class="column-num"><fmt:message key="common.label.request_car_engine_power" /></th>
							<th class="column-word"><fmt:message key="common.label.request_car_engine_type" /></th>
							<th class="column-num"><fmt:message key="common.label.request_car_num_of_seats" /></th>
							<th class="column-button"></th>
						</tr>
						<c:set var="k" value="1" />
						<c:forEach var="requestBean" items="${requestScope.requestBeansMap}">
						
							<tr>
								<td class="column-num"><c:out value="${k}" /></td>
								<td class="column-num">${requestBean.key.id}</td>
								<td class="column-word"><a href="controller?command=user&id=${requestBean.key.userId}">${requestBean.key.userLogin}</a></td>
								<c:set var="k" value="${k+1}" />
								<td class="column-num"><a href="controller?command=flight&id=${requestBean.key.flightId}">${requestBean.key.flightId}</a></td>
								<td class="column-word">${requestBean.key.flightDirection}</td>
								<td class="column-word" class="column-word">${requestBean.key.carModelName}</td>
								<td class="column-num">${requestBean.key.carEnginePower}</td>
								<td class="column-word">${requestBean.key.carEngineTypeName}</td>
								<td class="column-num">${requestBean.key.carNumOfSeats}</td>
								
								<td class="column-button"><button onClick='showCarList(${jsonCarBeans}, ${requestBean.value})'><fmt:message key="all_request_list_jsp.button.assign_car" /></button></td>
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