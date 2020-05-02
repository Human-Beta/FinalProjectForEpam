<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
	<fmt:message key='user_list_jsp.head.title' var="title"/>
	<%@ include file="/WEB-INF/jspf/head.jspf"%>
	<body>
		<div class="wrapper">

			<div class="back-color"></div>
			
			<%@ include file="/WEB-INF/jspf/header.jspf"%>

			<div class="group">
				
				<%@ include file="/WEB-INF/jspf/sidebar.jspf"%>

				<div id="content">

					<button class="button-add" onclick="overlayOn('overlay-user')"><fmt:message key="user_list_jsp.button.register_user" /></button>
					<div id="overlay-user" class="overlay">
						<div class=overlay-background onclick="overlayOff('overlay-user')"></div>	
						<div class="window">
							<form id="form" method="post" action="controller">
								<input type="hidden" name="command" value="registerUser">
	
								
								<div class="prop">
									<span><fmt:message key="common.label.user_login" />:</span>
									<input type="text" name="userLogin" required pattern="^[\p{L}\d_-]{3,16}$" title="3 to 16 letters, numbers or '-'/'_'">
								</div>


								<div class="prop">
									<span><fmt:message key="common.label.user_password" />:</span>
									<input type="password" name="userPassword" required pattern="^.{5,}$" title="3,or more, symbols">
								</div>

								<div class="prop">
									<span><fmt:message key="common.label.user_first_name" />:</span>
									<input type="text" name="userFirstName" required pattern="^\p{L}{3,255}$" title="3 to 255 letters">
								</div>

								<div class="prop">
									<span><fmt:message key="common.label.user_last_name" />:</span>
									<input type="text" name="userLastName" required  pattern="^\p{L}{3,255}$" title="3 to 255 letters">
								</div>
								
								<div class="prop">
									<span><fmt:message key="common.label.user_role" />:</span>
									<select name="userRoleId" required>
										<c:forEach var="i" begin="0" end="${fn:length(requestScope.userRoles) - 1}" step="1">
											<c:set var="role" value="${requestScope.userRoles[i]}" />
											<option value="${role.ordinal()}">${role.getName()}</option>
										</c:forEach>
									</select>
								</div>
								
								<button id="submit-button" type="submit"><fmt:message key="user_list_jsp.form.button.submit_register_user" /></button>
							</form>
						</div>
					</div>
				
					<c:if test="${empty requestScope.userBeans}"><fmt:message key='common.message.no_users' /></c:if>
					<c:if test="${not empty requestScope.userBeans}">
						<table id="user-list-table" class="table-css">
							<tr>
								<th class="column-num">№</th>
								<th class="column-num"><fmt:message key="common.label.id" /></th>
								<th class="column-word"><fmt:message key="common.label.user_login" /></th>
								<th class="column-word"><fmt:message key="common.label.user_first_name" /></th>
								<th class="column-word"><fmt:message key="common.label.user_last_name" /></th>
								<th class="column-word"><fmt:message key="common.label.user_role" /></th>
							</tr>
	
							<c:set var="k" value="1" />
							<c:forEach var="userBean" items="${requestScope.userBeans}">
								<tr>
									<td class="column-num">${k}</td>
									<td class="column-num">${userBean.id}</td>
									<td class="column-word">${userBean.login}</td>
									<td class="column-word">${userBean.firstName}</td>
									<td class="column-word">${userBean.lastName}</td>
									<td class="column-word">${userBean.roleName}</td>
								</tr>
								<c:set var="k" value="${k + 1}" />
							</c:forEach>
	
						</table>
					</c:if>
				</div>
			</div>

			<%@ include file="/WEB-INF/jspf/footer.jspf"%>

		</div>
	</body>
</html>