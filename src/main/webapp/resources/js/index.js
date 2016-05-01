(function(window, undefined) {

	var $indexJs = {};

	$indexJs.isCreateLoanFormSubmited = false;
	$indexJs.isDeleteLoanFormSubmited = false;
	$indexJs.isChangeUNFormSubmited = false;
	$indexJs.isChangePWFormSubmited = false;

	$indexJs.checkAll = function() {
		if ($("#checkAll").prop("checked")) {
			$("input[type='checkbox'][id^='loanCheckbox_']").each(function() {
				$(this).prop("checked", true);
			});
		} else {
			$("input[type='checkbox'][id^='loanCheckbox_']").each(function() {
				$(this).prop("checked", false);
			});
		}
	};

	$indexJs.checkCheckbox = function(thisCheckbox) {
		if ($(thisCheckbox).prop("checked")) {
			var allChecked = true;
			$("input[type='checkbox'][id^='loanCheckbox_']").each(function() {
				if (!$(this).prop("checked")) {
					allChecked = false;
				}
			});
			if (allChecked) {
				$("#checkAll").prop("checked", true);
			}
		} else {
			$("#checkAll").prop("checked", false);
		}
	};

	$indexJs.onCreateLoan = function() {
		if (!$indexJs.isCreateLoanFormSubmited) {
			$indexJs.isCreateLoanFormSubmited = true;
			return true;
		} else {
			return false;
		}
	};

	$indexJs.onDeleteLoan = function() {
		if (!$indexJs.isDeleteLoanFormSubmited) {
			$indexJs.isDeleteLoanFormSubmited = true;
			return true;
		} else {
			return false;
		}
	};

	$indexJs.onChangeUsername = function() {
		if (!$indexJs.isChangeUNFormSubmited) {
			$indexJs.isChangeUNFormSubmited = true;
			return true;
		} else {
			return false;
		}
	};

	$indexJs.onChangePassword = function() {
		if (!$indexJs.isChangePWFormSubmited) {
			$indexJs.isChangePWFormSubmited = true;
			return true;
		} else {
			return false;
		}
	};

	$indexJs.calculateSummary = function() {
		var summary = {};
		$("tr[id^=to]").each(function() {
			var id = $(this).prop("id");
			var name_money = id.substring(id.indexOf("_") + 1, id.length);
			var name = name_money.substring(0, name_money.lastIndexOf("_"));
			name = name.replace("_", " ");
			var money = parseFloat(name_money.substring(name_money.lastIndexOf("_") + 1, name_money.length));
			if (null == summary[name]) {
				summary[name] = 0.0;
			}
			summary[name] += money;
		});
		$("tr[id^=from]").each(function() {
			var id = $(this).prop("id");
			var name_money = id.substring(id.indexOf("_") + 1, id.length);
			var name = name_money.substring(0, name_money.lastIndexOf("_"));
			name = name.replace("_", " ");
			var money = parseFloat(name_money.substring(name_money.lastIndexOf("_") + 1, name_money.length));
			if (null == summary[name]) {
				summary[name] = 0.0;
			}
			summary[name] -= money;
		});

		var tBody = $("#summaryTbody");
		$(tBody).empty();
		$.each(summary, function(name, money) {
			var tr = document.createElement("tr");
			var tdName = document.createElement("td");
			tdName.innerHTML = name;
			var tdMoney = document.createElement("td");
			if (money > 0) {
				tdMoney.innerHTML = "$" + (Math.round(money * 100) / 100).toFixed(2);
				tdMoney.setAttribute("style", "color: red");
				tdName.setAttribute("style", "color: red");
			} else {
				tdMoney.innerHTML = "$" + (Math.round(-money * 100) / 100).toFixed(2);
				tdMoney.setAttribute("style", "color: blue");
				tdName.setAttribute("style", "color: blue");
			}
			tr.appendChild(tdName);
			tr.appendChild(tdMoney);
			$(tBody).append(tr);
		});
	};

	window.$indexJs = $indexJs;

})(window, undefined);
