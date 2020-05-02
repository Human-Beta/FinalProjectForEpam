<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<%@ page import="ua.nure.shishov.finaltask.db.UserRole"%>

<html>
<fmt:message key='flight_list_jsp.head.title' var="title"/>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<div id="wrapper">
		<div class="back-color"></div>

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<div class="group">
			<%@ include file="/WEB-INF/jspf/sidebar.jspf"%>

			<c:if test="${sessionScope.userRole eq UserRole.DRIVER}">

				<div id="overlay-req" class="overlay">
					<div class=overlay-background onclick="overlayOff('overlay-req')"></div>	
					<div class="window">
						<form method="post" action="controller">
							<input type="hidden" name="command" value="makeRequest">
							<input id="flight-id" type="hidden" name="flightId" required>
							
							<div class="prop">
								<span><fmt:message key="common.label.car_model" />:</span>
								<select name="carModelId" required>
									<c:forEach var="model" items="${requestScope.carModels}">
										<option value="${model.id}">${model.name}</option>
									</c:forEach>
								</select>
							</div>
	
							<div class="prop">
								<span><fmt:message key="common.label.car_power_of_engine" />:</span>
								<input type="number" name="carEnginePower" value="100" required min="100" max="10000" title="100 - 10 000">
							</div>

							<div class="prop">
								<span><fmt:message key="common.label.car_type_of_engine" />:</span>
								<select name="carEngineTypeId" required>
									<c:forEach var="i" begin="0" end="${fn:length(requestScope.carEngineTypes) - 1}" step="1">
										<option value="${requestScope.carEngineTypes[i].ordinal()}">${requestScope.carEngineTypes[i].toString()}</option>
									</c:forEach>
								</select>
							</div>
							<div class="prop">
								<span><fmt:message key="common.label.car_number_of_seats" />:</span>
								<select name="carNumOfSeats" required>
									<c:forEach var="i" begin="2" end="6" step="1">
										<option value="${i}">${i}</option>
									</c:forEach>
								</select>
							</div>
							<input type="submit" value="<fmt:message key='flight_list_jsp.form.button.make_request' />">
							
						</form>
					</div>
				</div>
				
			</c:if>

			<div id="content">
			
				<%@ include file="/WEB-INF/jspf/sort.jspf"%>
				
				<c:if test="${sessionScope.userRole eq UserRole.DISPATCHER || sessionScope.userRole eq UserRole.ADMIN}">
					<button class="button-add" onclick="overlayOn('overlay-flight')"><fmt:message key='flight_list_jsp.button.add_flight' /></button>
					<div id="overlay-flight" class="overlay">
						<div class=overlay-background onclick="overlayOff('overlay-flight')"></div>	
						<div class="window">
							<form method="post" action="controller">
								<input type="hidden" name="command" value="addFlight">

								<div class="prop">
									<span><fmt:message key='common.label.flight_from_city' />:</span>
									<select name="fromCityId" required>
										<c:forEach var="city" items="${requestScope.cities}">
											<option value="${city.id}">${city.name}</option>
										</c:forEach>
									</select>
								</div>

								<div class="prop">
									<span><fmt:message key='common.label.flight_to_city' />:</span>
									<select name="toCityId" required>
										<c:forEach var="city" items="${requestScope.cities}">
											<option value="${city.id}">${city.name}</option>
										</c:forEach>
									</select>
								</div>

								<input type="submit" value="<fmt:message key='flight_list_jsp.form.button.add_flight' />">
							</form>
						</div>
					</div>
				</c:if>
				<c:if test="${empty requestScope.flightBeans}"><fmt:message key='common.message.no_flights' /></c:if>
				<c:if test="${not empty requestScope.flightBeans}">
					<table id="flight-list-table" class="table-css">
						<tr>
							<th class="column-num">№</th>
							<th class="column-num"><fmt:message key='common.label.id' /></th>
							<th class="column-word"><fmt:message key='common.label.flight_direction' /></th>
							<th class="column-date"><fmt:message key='common.label.flight_date_of_creation' /></th>
							<c:if test="${sessionScope.userRole eq UserRole.DRIVER}">
								<th class="column-button"></th>
							</c:if>
							<c:if test="${sessionScope.userRole eq UserRole.DISPATCHER || sessionScope.userRole eq UserRole.ADMIN}">
								<th class="column-word"><fmt:message key='common.label.flight_status' /></th>
								<th class="column-word"><fmt:message key='common.label.flight_user_login' /></th>
								<th class="column-num"><fmt:message key='common.label.flight_car_id' /></th>
								<th class="column-word"><fmt:message key='common.label.flight_car_model' /></th>
							</c:if>
						</tr>
	
						<c:set var="k" value="1" />
						<c:forEach var="flightBean" items="${requestScope.flightBeans}">
							<tr>
								<td class="column-num"><c:out value="${k}" /></td>
								<td class="column-num">${flightBean.id}</td>
								<td class="column-word">${flightBean.direction}</td>
								<td class="column-date">${flightBean.dateOfCreation}</td>
								<c:if test="${sessionScope.userRole eq UserRole.DRIVER}">
									<td class="column-button">
										<c:set var="contains" value="false" />
										<c:forEach var="userRequest" items="${requestScope.userRequestBeans}">
	
											<c:if test="${userRequest.flightId eq flightBean.id}">
												<c:set var="contains" value="true" />
											</c:if>
	
										</c:forEach>
										<c:choose>
											<c:when test="${contains == true}">
												<span><fmt:message key='flight_list_jsp.label.chosen_flight' /></span>
											</c:when>
											<c:otherwise>
												<button onclick="overlayReqOn(${flightBean.id})"><fmt:message key='flight_list_jsp.button.choose_flight' /></button>
											</c:otherwise>
										</c:choose>
									</td>
								</c:if>
								<c:if test="${sessionScope.userRole eq UserRole.DISPATCHER || sessionScope.userRole eq UserRole.ADMIN}">
									<td class="column-word">${flightBean.statusName}</td>
	
									<td class="column-word"><a href="controller?command=user&id=${flightBean.userId}">${flightBean.userLoginName}</a></td>
	
									<td class="column-num"><a href="controller?command=car&id=${flightBean.carId}">${flightBean.carId == 0? '' : flightBean.carId}</a></td>
									<td class="column-word">${flightBean.carModelName}</td>
								</c:if>
								<c:set var="k" value="${k+1}" />
							</tr>
						</c:forEach>
					</table>
				</c:if>
				
			</div>
		</div>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%></div>
</body>
</html>
