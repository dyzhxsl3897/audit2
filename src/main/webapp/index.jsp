<%@ page isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:useBean id="today" class="java.util.Date" scope="page" />
<jsp:useBean id="dateFormatSymbols" class="java.text.DateFormatSymbols" scope="page" />

<tiles:insertDefinition name="mainTmpl">
	<tiles:putAttribute name="js">
		<script src="${pageContext.request.contextPath}/resources/js/index.js"></script>
	</tiles:putAttribute>
	<tiles:putAttribute name="css">
		<link href="${pageContext.request.contextPath}/resources/css/index.css" rel="stylesheet" type="text/css">
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<div data-role="navbar">
			<ul>
				<li><a href="#actionPanel" id="actionPanelA" data-ajax="false" class="ui-icon-tag ui-btn-icon-left">Action</a></li>
				<li><a href="#accountPanel" id="accountPanelA" data-ajax="false" class="ui-icon-user ui-btn-icon-left">Account</a></li>
				<li><a href="${pageContext.request.contextPath}/signOut" data-ajax="false" class="ui-icon-action ui-btn-icon-left">Sign Out</a></li>
			</ul>
		</div>

		<div data-role="collapsible" data-content-theme="false" id="summaryDiv">
			<h4 onclick="$indexJs.calculateSummary();">Summary:</h4>

			<table data-role="table" id="summaryTable" data-mode="columntoggle" class="ui-responsive table-stroke table-stripe"
				data-column-btn-text="Details...">
				<thead>
					<tr>
						<th data-priority="1">Name</th>
						<th data-priority="1">Money</th>
					</tr>
				</thead>
				<tbody id="summaryTbody">
				</tbody>
			</table>
		</div>

		<div data-role="collapsible" data-content-theme="false" data-collapsed="false" id="detailsDiv">
			<h4>Details:</h4>

			<form id="loanForm" action="${pageContext.request.contextPath}/deleteLoan" method="post" data-ajax="false"
				onsubmit="return $indexJs.onDeleteLoan();">
				<table data-role="table" id="loanTable" data-mode="columntoggle" class="ui-responsive table-stroke table-stripe" data-column-btn-text="Details...">
					<thead>
						<tr>
							<th data-priority="1"><input class="checkbox" type="checkbox" id="checkAll" name="checkAll" onclick="$indexJs.checkAll();" /></th>
							<th data-priority="1">Name</th>
							<th data-priority="1">Money</th>
							<th data-priority="2">Notes</th>
							<th data-priority="3">Date</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${sessionScope.loans}" var="loan">
							<c:choose>
								<c:when test="${sessionScope.currentUser.username eq loan.fromUser.username}">
									<tr id="to_${loan.toUser.firstName}_${loan.toUser.lastName}_${loan.money}">
										<td><input class="checkbox" type="checkbox" name="loanCheckbox" id="loanCheckbox_${loan.loanId}"
											onchange="$indexJs.checkCheckbox(this);" value="${loan.loanId}" /></td>
										<td>
											<div style="color: red">${loan.toUser.firstName}&nbsp;${loan.toUser.lastName}</div>
										</td>
										<td>
											<div style="color: red">
												<fmt:formatNumber value="${loan.money}" type="currency" />
											</div>
										</td>
										<td>${loan.notes}</td>
										<td><fmt:formatDate type="date" value="${loan.loanTime}" /></td>
									</tr>
								</c:when>
								<c:when test="${sessionScope.currentUser.username eq loan.toUser.username}">
									<tr id="from_${loan.fromUser.firstName}_${loan.fromUser.lastName}_${loan.money}">
										<td><input type="checkbox" name="loanCheckbox" id="loanCheckbox_${loan.loanId}" onchange="$indexJs.checkCheckbox(this);"
											value="${loan.loanId}" /></td>
										<td>
											<div style="color: blue">${loan.fromUser.firstName}&nbsp;${loan.fromUser.lastName}</div>
										</td>
										<td>
											<div style="color: blue">
												<fmt:formatNumber value="${loan.money}" type="currency" />
											</div>
										</td>
										<td>${loan.notes}</td>
										<td><fmt:formatDate type="date" value="${loan.loanTime}" /></td>
									</tr>
								</c:when>
							</c:choose>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>

		<div data-role="panel" id="actionPanel" data-position="left" data-display="overlay">
			<div style="margin: 10px;">
				<div>
					<a href="#" data-rel="close" class="ui-btn ui-corner-all ui-btn-icon-right ui-icon-back">Close</a>
				</div>
				<div>
					<a href="${pageContext.request.contextPath}/refresh" data-ajax="false" class="ui-btn ui-corner-all ui-btn-icon-right ui-icon-refresh">Refresh</a>
				</div>
				<div>
					<a href="#" class="ui-btn ui-corner-all ui-btn-icon-right ui-icon-delete" onclick="loanForm.submit()">Delete</a>
				</div>
				<div id="createLoanFormDiv" data-role="collapsible" data-iconpos="right">
					<h4 style="text-align: center;">Create</h4>

					<form id="createLoanForm" action="${pageContext.request.contextPath}/createLoan" method="post" data-ajax="false"
						onsubmit="return $indexJs.onCreateLoan();">
						<select id="moneyDirectionSelect" name="moneyDirectionSelect">
							<option value="expense">Expense</option>
							<option value="income">Income</option>
						</select> <select id="friendSelect" name="friendSelect">
							<c:if test="${0 lt sessionScope.currentUser.friends.size()}">
								<c:forEach items="${sessionScope.currentUser.friends}" var="friend">
									<option value="${friend.userId}">${friend.firstName}&nbsp;${friend.lastName}</option>
								</c:forEach>
							</c:if>
						</select> <input id="moneyInput" name="moneyInput" type="number" step="0.01" min="0.01" required="required" placeholder="Money" />

						<c:choose>
							<c:when test="${today.month + 1 gt 9 and today.date gt 9}">
								<input id="loanTimeInput" name="loanTimeInput" type="date" value="${today.year + 1900}-${today.month + 1}-${today.date}" />
							</c:when>
							<c:when test="${today.month + 1 gt 9 and today.date le 9}">
								<input id="loanTimeInput" name="loanTimeInput" type="date" value="${today.year + 1900}-${today.month + 1}-0${today.date}" />
							</c:when>
							<c:when test="${today.month + 1 le 9 and today.date gt 9}">
								<input id="loanTimeInput" name="loanTimeInput" type="date" value="${today.year + 1900}-0${today.month + 1}-${today.date}" />
							</c:when>
							<c:when test="${today.month + 1 le 9 and today.date le 9}">
								<input id="loanTimeInput" name="loanTimeInput" type="date" value="${today.year + 1900}-0${today.month + 1}-0${today.date}" />
							</c:when>
						</c:choose>

						<input id="notesInput" name="notesInput" type="text" placeholder="Notes" required="required" data-clear-btn="true" /> <input type="submit"
							value="Create" />
					</form>
				</div>
			</div>
		</div>

		<div data-role="panel" id="accountPanel" data-position="right" data-display="overlay">
			<div style="margin: 10px;">
				<div>
					<a href="#" data-rel="close" class="ui-btn ui-corner-all ui-btn-icon-right ui-icon-back">Close</a>
				</div>

				<div id="userSettingsFormDiv" data-role="collapsible" data-iconpos="right">
					<h4 style="text-align: center;">Settings</h4>

					<form id="usernameForm" action="${pageContext.request.contextPath}/changeUsername" method="post" data-ajax="false"
						onsubmit="return $indexJs.onChangeUsername();">
						<input type="text" id="usernameInput" name="usernameInput" placeholder="${sessionScope.currentUser.username}" /> <input type="submit"
							value="Change username" />
					</form>

					<form id="chnagePasswordForm" action="${pageContext.request.contextPath}/changePassword" method="post" data-ajax="false">
						<input type="password" id="oldPasswordInput" name="oldPasswordInput" placeholder="Old Password" /> <input type="password" id="newPasswordInput"
							name="newPasswordInput" placeholder="New Password" /> <input type="password" id="reNewPasswordInput" name="reNewPasswordInput"
							placeholder="Re-type Password" /> <input type="submit" value="Change password" />
					</form>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
