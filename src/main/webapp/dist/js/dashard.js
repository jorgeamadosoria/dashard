$.extend({
	getUrlVars : function() {
		var vars = [], hash;
		var hashes = window.location.href.slice(
				window.location.href.indexOf('?') + 1).split('&');
		for (var i = 0; i < hashes.length; i++) {
			hash = hashes[i].split('=');
			vars.push(hash[0]);
			vars[hash[0]] = hash[1];
		}
		return vars;
	},
	getUrlVar : function(name) {
		return $.getUrlVars()[name];
	}
});


function initUsername(){
	$.ajax({
		url : "data/username",
		method : "GET",
		success : function(data){
			$("a#user").text(data.name);
			alert(data.authorities);
			//$("a#users_link").hide();
		}
	});
}

function initUpsert() {
	initUsername();
	
	var id = $.getUrlVar('id');
	if (id == null) {
		var data = {
			id : '',
			name : '',
			description : '',
			metrics : {},
			switches : {}
		};
		configDeviceUpsert(data);
	} else
		$.ajax({
			url : "data/view",
			data : {
				"id" : id
			},
			method : "GET",
			success : configDeviceUpsert
		});

}

function initList() {
	initUsername();
	
	$.ajax({
		url : "data/list",
		method : "GET",
		success : configDeviceList
	});

}

function initUsers() {
	
	initUsername();
	
	$("#add_user").on("click",function(e){ 
		addUserRow($("#usersList").children("tr").length,{username:"",password:""});
	});
	
	$("tbody#usersList").on("click","a#edit_link",function(e){ 
		$("form#user_form").find("input#username").val($(this).attr("data-user"));
		$("form#user_form").find("input#password").val($(this).attr("data-password"));
		return false;
	});
	
	$.ajax({
		url : "	users/list",
		method : "GET",
		success : configUsersList
	});

}


function configUsersList(data) {
	for (var x = 0; x < data.length; x++) {
		addUserRow(data[x]);
	}
}

function addUserRow(user){
	var row = $("thead #userRow").clone();
	$(row).removeClass("hidden");
	$(row).find("#username").text(user.username);
	$(row).find("#password").text(user.password);
	$(row).find("a#edit_link").attr("data-password",user.password);
	$(row).find("a#edit_link").attr("data-user",user.username);
	row.html(row.html().replace(/\?username=/g, "?username=" + user.username));
	$("#usersList").append(row);
}


function upsertMetricsAddButton(x) {
	var wrapper = $("#metrics_wrapper"); // Fields wrapper
	var add_button = $("#metrics_add_button"); // Add button ID

	$(add_button).click(function(e) { // on add input button click
		e.preventDefault();
		addMetrics(x++, {
			id : 0,
			name : '',
			code : ''
		})
	});

	$(wrapper).on("click", "button.remove_field", function(e) { // user click on
		// remove text
		e.preventDefault();
		var id = $(this).data("id");
		if (id != 0) {
			$.ajax({
				url : "data/metrics/delete",
				data : {
					"id" : id
				},
				method : "POST",
				success : function(data) {
				}
			});
		}
		$(this).parents('tr').remove();
	})
};

function upsertSwitchesAddButton(x) {
	var wrapper = $("#switches_wrapper"); // Fields wrapper
	var add_button = $("#switches_add_button"); // Add button ID

	$(add_button).click(function(e) { // on add input button click
		e.preventDefault();
		addSwitch(x++, {
			id : 0,
			parentId : 0
		});
	});

	$(wrapper).on("click", "button.remove_field", function(e) { // user click on
		// remove text
		e.preventDefault();
		var id = $(this).data("id");
		if (id != 0) {
			$.ajax({
				url : "data/switches/delete",
				data : {
					"id" : id
				},
				method : "POST",
				success : function(data) {
				}
			});
		}
		$(this).parents('tr').remove();
	})
};
// ------------------------------------------------
function addSwitch(x, switchObj) {
	var addSwitchTemplate = $('#switch-template').clone();

	$("#switches_wrapper").append(addSwitchTemplate);
	$(addSwitchTemplate).attr("id", switchObj.id);
	$(addSwitchTemplate).removeClass("hidden");
	$(addSwitchTemplate).find('#enabled').attr("name",
			"switches[" + x + "].enabled").prop("checked",switchObj.enabled);
	$(addSwitchTemplate).find('input#id').val(switchObj.id).attr("name",
			"switches[" + x + "].id");
	$(addSwitchTemplate).find('span#id').text(switchObj.id);
	$(addSwitchTemplate).find('.remove_field').data("id",switchObj.id);
	$(addSwitchTemplate).find('#name').val(switchObj.name).attr("name",
			"switches[" + x + "].name");
	$(addSwitchTemplate).find('#description').val(switchObj.description).attr(
			"name", "switches[" + x + "].description");
	$(addSwitchTemplate).find('#pin').val(switchObj.pin).attr("name",
			"switches[" + x + "].pin");
	$(addSwitchTemplate).find('#parentId').val(switchObj.parentId).attr("name",
			"switches[" + x + "].parentId");
}

function addMetrics(x, metricsObj) {
	var addMetricsTemplate = $('#metrics-template').clone();
	$("#metrics_wrapper").append(addMetricsTemplate);
	$(addMetricsTemplate).attr("id", metricsObj.id);
	$(addMetricsTemplate).removeClass("hidden");
	$(addMetricsTemplate).find('input#id').val(metricsObj.id).attr("name",
			"metrics[" + x + "].id");
	$(addMetricsTemplate).find('span#id').text(metricsObj.id);
	$(addMetricsTemplate).find('.remove_field').data("id",metricsObj.id);
	$(addMetricsTemplate).find('#enabled').attr("name",
			"metrics[" + x + "].enabled").prop("checked",metricsObj.enabled);
	
	$(addMetricsTemplate).find('#code').val(metricsObj.code).attr("name",
			"metrics[" + x + "].code");
	$(addMetricsTemplate).find('#type').attr("name", "metrics[" + x + "].type")
			.data("icon", metricsObj.type).iconpicker();
	// .val(metricsObj.type).attr("name","metrics[" + x + "].type");
	$(addMetricsTemplate).find('#name').val(metricsObj.name).attr("name",
			"metrics[" + x + "].name");
}

function configDeviceUpsert(data) {
	$("#id").val(data.id);
	$("#name").val(data.name);
	$("#description").val(data.description);
	var x = 0;
	for (; x < data.metrics.length; x++) {

		addMetrics(x, data.metrics[x]);
	}
	upsertMetricsAddButton(x);
	x = 0;
	for (; x < data.switches.length; x++) {
		addSwitch(x, data.switches[x]);
	}
	upsertSwitchesAddButton(x);
}

function pollMetrics() {
	var id = $.getUrlVar('id');

	$.ajax({
		url : "data/metrics",
		data : {
			"id" : id
		},
		method : "GET",
		success : function(data) {
			for (var x = 0; x < data.length; x++) {
				$("#metrics_wrapper div#" + data[x].id + " #value").text(
						data[x].value);
				$("#metrics_wrapper div#" + data[x].id + " #date").text(
						data[x].dateString);
			}
		}
	});

	setTimeout(pollMetrics, 1000);
}

function configDeviceView(data) {
	
	$("#name").text(data.name);
	$("#description").text(data.description);
	$("#accessId").text(data.accessId).collapser({
	    mode: 'chars',
	    showText : "Show",
	    hideText : "Hide",
	    truncate: 20
	});
	for (var x = 0; x < data.metrics.length; x++) {
		if (!data.metrics[x].enabled)
			continue;
		viewMetricsTemplate = $("#metrics-template").clone();
		$(viewMetricsTemplate).attr("id", "");
		$(viewMetricsTemplate).removeClass("hidden");
		$(viewMetricsTemplate).find('#enabled').prop("checked",data.metrics[x].enabled);
		$("#metrics_wrapper").append(viewMetricsTemplate);
		$(viewMetricsTemplate).attr("id", data.metrics[x].id);
		$(viewMetricsTemplate).find("#name").text(data.metrics[x].name);
		$(viewMetricsTemplate).find("#type").addClass(
				"wi " + data.metrics[x].type);
		$(viewMetricsTemplate).find("#value").text(data.metrics[x].value);
		// switchToggle($(viewMetricsTemplate).find("#state"));
		$(viewMetricsTemplate).find("#date").text(data.metrics[x].dateString);
	}
	for (var x = 0; x < data.switches.length; x++) {
		if (!data.switches[x].enabled)
			continue;
		viewSwitchTemplate = $("#switches-template").clone();
		$(viewSwitchTemplate).attr("id", data.switches[x].id);
		$(viewSwitchTemplate).removeClass("hidden");
		$("#switches_wrapper").append(viewSwitchTemplate);
		$(viewMetricsTemplate).find('#enabled').prop("checked",data.switches[x].enabled);
		$(viewSwitchTemplate).find("#name").text(data.switches[x].name);
		$(viewSwitchTemplate).find("#id").text(data.switches[x].id);
		$(viewSwitchTemplate).find("#description").text(
				data.switches[x].description);
		// $(viewSwitchTemplate).find("#parentId").text(data.switches[x].parentId);
		$(viewSwitchTemplate).find("#state").prop("checked",
				data.switches[x].state == 1);
		$(viewSwitchTemplate).find(".onoffswitch").data("id",

		data.switches[x].id).on("click", function(e) {
			var id = $(this).data('id');
			e.preventDefault();
			$.ajax({
				url : "data/toggle",
				data : {
					"deviceId" : $.getUrlVar('id'),
					"id" : id
				},
				method : "POST",
				success : updateSwitchState
			});

		})
	}

	pollMetrics();
}

function updateSwitchState(data) {
	for (var x = 0; x < data.length; x++) {
		$("#switches_wrapper").find('div#' + data[x].id + " #state").prop(
				"checked", data[x].state == 1);
	}
}

function configDeviceList(data) {
	$("#add_device").on("click",function(e){
		$.get("upsert.html");
	});
	for (var x = 0; x < data.length; x++) {
		var row = $("thead #deviceRow").clone();
		$(row).removeClass("hidden");
		$(row).children().filter("#name").text(data[x].name);
		$(row).children().filter("#description").text(data[x].description);
		row.html(row.html().replace(/\?id=/g, "?id=" + data[x].id));
		$("#devicesList").append(row);
	}
}

// ------------------------------------------------
function initView() {
	
	initUsername();
	
	var id = $.getUrlVar('id');
	$.ajax({
		url : "data/view",
		data : {
			"id" : id
		},
		method : "GET",
		beforeSend : function(data) {

		},
		success : configDeviceView
	});
}