<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<div id="login-back-color" class="back-color"></div>
	<div id="wrapper">
		<div id="login">
			<form id="form-login" method="post" action="controller">
				<div id="login-inputs">
					<input type="hidden" name="command" value="login">
					<c:if test="${not empty param.command}">
						<input type="hidden" name="forwardCommand" value="${param.command}">
					</c:if>
					<input class="input-login input-login-text" type="text" name="login" placeholder="login" required pattern="^[\p{L}\d_-]{3,16}$" title="3 to 16 letters, numbers or '-'/'_'">
					<input class="input-login input-login-text" type="password" placeholder="password" required name="password">
					<input id="input-login-submit" class="input-login" type="submit" value="Login">
				</div>
			</form>
		</div>
	</div>
</body>
</html>