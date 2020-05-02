<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<%@ page import="ua.nure.shishov.finaltask.db.UserRole" %>

<c:set var="userRole" value="${userRole}"></c:set>

<html>
<fmt:message key='car_list_jsp.head.title' var="title"/>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<div id="wrapper">

		<div class="back-color"></div>

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<div class="group">
			<%@ include file="/WEB-INF/jspf/sidebar.jspf"%>

			<div id="content">
				<c:if test="${userRole == UserRole.ADMIN}">
					<button class="button-add" onclick="addCar('<fmt:message key="car_list_jsp.form.button.add_car" />')"><fmt:message key="car_list_jsp.button.add_car" /></button>
					<div id="overlay-car" class="overlay">
						<div class=overlay-background onclick="overlayOff('overlay-car')"></div>	
						<div class="window">
							<form id="form" method="post" action="controller">
								<input id="command" type="hidden" name="command">
	
								<div class="prop">
									<span><fmt:message key="common.label.id" />:</span>
									<span></span>
								</div>
								
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
									<input id="car-engine-power" type="number" name="carEnginePower" value="100" required min="100" max="10000" title="100 - 10 000"></div>
								
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
								
								<div class="prop">
									<span><fmt:message key="common.label.car_state" />:</span>
									<select name="carStateId" required>
										<c:forEach var="i" begin="0" end="${fn:length(requestScope.carStates) - 1}" step="1">
											<option value="${i}">${requestScope.carStates[i].toString()}</option>
										</c:forEach>
									</select>
								</div>
								
								<button id="submit-button" type="submit"></button>
							</form>
						</div>
					</div>
				
				</c:if>
				<c:if test="${empty requestScope.carBeans}"><fmt:message key='common.message.no_cars' /></c:if>
				<c:if test="${not empty requestScope.carBeans}">
					<table id="car-list-table">
						<tr>
							<th class="column-num">№</th>
							<th class="column-num"><fmt:message key="common.label.id" /></th>
							<th class="column-word"><fmt:message key="common.label.car_model" /></th>
							<th class="column-num"><fmt:message key="common.label.car_power_of_engine" /></th>
							<th class="column-word"><fmt:message key="common.label.car_type_of_engine" /></th>
							<th class="column-num"><fmt:message key="common.label.car_number_of_seats" /></th>
							<th class="column-word"><fmt:message key="common.label.car_state" /></th>
							<c:if test="${userRole == UserRole.ADMIN}">
								<th class="column-button"></th>
								<th class="column-button"></th>
							</c:if>
						</tr>
	
						<c:set var="k" value="1" />
						<c:forEach var="carBean" items="${requestScope.carBeans}">
							<tr>
								<td class="column-num">${k}</td>
								<td class="column-num">${carBean.id}</td>
								<td class="column-word">${carBean.modelName}</td>
								<td class="column-num">${carBean.enginePower}</td>
								<td class="column-word">${carBean.engineTypeName}</td>
								<td class="column-num">${carBean.numOfSeats}</td>
								<td class="column-word">${carBean.stateName}</td>
								<c:if test="${userRole == UserRole.ADMIN}">
									<td class="column-button"><button onclick="changeCar(${carBean.id}, ${carBean.enginePower}, '<fmt:message key="car_list_jsp.form.button.change_car" />')"><fmt:message key="car_list_jsp.button.change_car" /></button></td>
									<td class="column-button"><a href="controller?command=deleteCar&carId=${carBean.id}&forwardCommand=carList"><fmt:message key="car_list_jsp.button.delete_car" /></a></td>
								</c:if>
								<c:set var="k" value="${k + 1}" />
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