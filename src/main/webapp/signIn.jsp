<%@ page isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tiles:insertDefinition name="mainTmpl">
	<tiles:putAttribute name="css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/signIn.css" />
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<div id="errMsg">${requestScope.errMsg}</div>

		<div id="headerDiv">We are here to set you free!</div>

		<form id="signInForm" action="${pageContext.request.contextPath}/signIn" method="post" data-ajax="false">
			<div id="signInDiv" class="ui-corner-all custom-corners">
				<div id="formBarDiv" class="ui-bar ui-bar-a">
					<h3>Welcome Back!</h3>
				</div>
				<div id="inputAreaDiv" class="ui-body ui-body-a">
					<div>
						<input type="text" data-clear-btn="true" name="username" id="username" placeholder="Username" value="" />
					</div>
					<div>
						<input type="password" data-clear-btn="true" name="password" id="password" placeholder="Password" value="" autocomplete="off" />
					</div>
					<input id="signInBtn" type="submit" value="Sign In" />
				</div>
			</div>

			<div id="signUpDiv">
				<a id="signUpBtn" href="#" class="ui-btn ui-corner-all ui-shadow ui-btn-inline" data-ajax="false">Sign
					Up</a>
			</div>

			<div id="forgotPasswordDiv">Forgot Password? &middot; I can't help!</div>
		</form>
	</tiles:putAttribute>
</tiles:insertDefinition>