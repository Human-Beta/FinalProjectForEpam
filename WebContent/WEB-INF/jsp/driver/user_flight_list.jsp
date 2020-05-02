<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<%@ page import="ua.nure.shishov.finaltask.db.FlightStatus" %>

<html lang="html">
<fmt:message key='user_flight_list_jsp.head.title' var="title" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>

	<div id="wrapper">
	
		<div class="back-color"></div>

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<div class="group">
			<%@ include file="/WEB-INF/jspf/sidebar.jspf"%>

			<div id="content">

				<%@ include file="/WEB-INF/jspf/sort.jspf"%>
				<c:if test="${empty requestScope.flightBeans}"><fmt:message key='common.message.no_flights' /></c:if>
				<c:if test="${not empty requestScope.flightBeans}">
					<table id="flight-list-table" class="flight-table table">
						<tr>
							<th class="column-num">№</th>
							<th class="column-num"><fmt:message key='common.label.id' /></th>
							<th class="column-word"><fmt:message key='common.label.flight_direction' /></th>
							<th class="column-date"><fmt:message key='common.label.flight_date_of_creation' /></th>
							<th class="column-word"><fmt:message key='common.label.flight_status' /></th>
							<th class="column-word"><fmt:message key='common.label.flight_car_model' /></th>
							<th class="column-button"></th>
							<th class="column-button"></th>
						</tr>
	
						<c:set var="k" value="1" />
						<c:forEach var="flightBean" items="${requestScope.flightBeans}">
							<tr>
								<td class="column-num"><c:out value="${k}" /></td>
								<td class="column-num"><a href="controller?command=flight&id=${flightBean.id}">${flightBean.id}</a></td>
								<td class="column-word">${flightBean.direction}</td>
								<td class="column-date">${flightBean.dateOfCreation}</td>
								<td class="column-word">${flightBean.statusName}</td>
								<td class="column-word"><a href="controller?command=car&carId=${flightBean.carId}">${flightBean.carModelName}</a></td>
								<td class="column-button">
									<c:if test="${flightBean.statusName ne FlightStatus.CLOSED.getName() && flightBean.statusName ne FlightStatus.CANCELED.getName()}">
										<form action="controller" method="post">
											<input type="hidden" name="command" value="finishFlight">
											<input type="hidden" name="flightId" value="${flightBean.id}">
											<input type="hidden" name="carId" value="${flightBean.carId}">
											<select name="carStateId" required>
												<c:forEach var="i" begin="0" end="${fn:length(requestScope.carStates) - 1}" step="1">
													<option value="${i}">${requestScope.carStates[i].toString()}</option>
												</c:forEach>
											</select>
											<button type="submit"><fmt:message key='user_flight_list_jsp.form.button.finish_flight' /></button>
										</form>
									</c:if>
								</td>
								<td class="column-button">
									<c:if test="${flightBean.statusName ne FlightStatus.CLOSED.getName() && flightBean.statusName ne FlightStatus.CANCELED.getName()}">
										<form action="controller" method="post">
											<input type="hidden" name="command" value="cancelFlight">
											<input type="hidden" name="flightId" value="${flightBean.id}">
											<button type="submit"><fmt:message key='user_flight_list_jsp.form.button.cancel_flight' /></button>
										</form>
									</c:if>
								</td>
								<c:set var="k" value="${k+1}" />
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