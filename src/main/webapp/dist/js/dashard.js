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

function initUpsert() {

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

	$.ajax({
		url : "data/list",
		method : "GET",
		success : configDeviceList
	});

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

	$(wrapper).on("click", ".remove_field", function(e) { // user click on
		// remove text
		e.preventDefault();
		var id = $(this).parent('div').find("#id").val();
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
		$(this).parent('div').remove();
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

	$(wrapper).on("click", ".remove_field", function(e) { // user click on
		// remove text
		e.preventDefault();
		var id = $(this).parent('div').children().filter("#id").val();
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
		$(this).parent('div').remove();
	})
};
// ------------------------------------------------
function addSwitch(x, switchObj) {
	var addSwitchTemplate = $('#switch-template').clone();
	
	$("#switches_wrapper").append(addSwitchTemplate);
	$(addSwitchTemplate).attr("id", switchObj.id);
	$(addSwitchTemplate).removeClass("hidden");
	$(addSwitchTemplate).find('#id').val(switchObj.id).attr("name",
			"switches[" + x + "].id");
	$(addSwitchTemplate).find('#name').val(switchObj.name).attr("name",
			"switches[" + x + "].name");
	$(addSwitchTemplate).find('#description').val(switchObj.description).attr(
			"name", "switches[" + x + "].description");
	$(addSwitchTemplate).find('#pin').val(switchObj.pin).attr("name",
			"switches[" + x + "].pin");
	$(addSwitchTemplate).find('#parentId').val(switchObj.parentId).attr(
			"name", "switches[" + x + "].parentId");
}

function addMetrics(x, metricsObj) {
	var addMetricsTemplate = $('#metrics-template').clone();
	$("#metrics_wrapper").append(addMetricsTemplate);
	$(addMetricsTemplate).attr("id", metricsObj.id);
	$(addMetricsTemplate).removeClass("hidden");
	$(addMetricsTemplate).find('#id').val(metricsObj.id).attr("name",
			"metrics[" + x + "].id");
	$(addMetricsTemplate).find('#code').val(metricsObj.code).attr("name",
			"metrics[" + x + "].code");
	$(addMetricsTemplate).find('#type').val(metricsObj.type).attr("name",
			"metrics[" + x + "].type");
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
						new Date(data[x].date));
			}
		}
	});

	setTimeout(pollMetrics, 1000);
}


function configDeviceView(data) {

	$("#name").text(data.name);
	$("#description").text(data.description);
	$("#accessId").last().text(data.accessId);
	for (var x = 0; x < data.metrics.length; x++) {
		viewMetricsTemplate = $("#metrics-template").clone();
		$(viewMetricsTemplate).attr("id", "");
		$(viewMetricsTemplate).removeClass("hidden");
		$("#metrics_wrapper").append(viewMetricsTemplate);
		$(viewMetricsTemplate).attr("id", data.metrics[x].id);
		$(viewMetricsTemplate).find("#name").text(data.metrics[x].name);
		$(viewMetricsTemplate).find("#type").text(data.metrics[x].type);
		
		$(viewMetricsTemplate).find("#value").text(data.metrics[x].value);
		//switchToggle($(viewMetricsTemplate).find("#state"));
		$(viewMetricsTemplate).find("#date").text(
				new Date(data.metrics[x].date));
	}
	for (var x = 0; x < data.switches.length; x++) {
		viewSwitchTemplate = $("#switches-template").clone();
		$(viewSwitchTemplate).attr("id", data.switches[x].id);
		$(viewSwitchTemplate).removeClass("hidden");
		$("#switches_wrapper").append(viewSwitchTemplate);
		$(viewSwitchTemplate).attr('id', data.switches[x].id);
		$(viewSwitchTemplate).find("#name").text(data.switches[x].name);
		$(viewSwitchTemplate).find("#id").text(data.switches[x].id);
		$(viewSwitchTemplate).find("#description").text(
				data.switches[x].description);
		$(viewSwitchTemplate).find("#pin").last().text(data.switches[x].pin);
	//	$(viewSwitchTemplate).find("#parentId").text(data.switches[x].parentId);
		$(viewSwitchTemplate).find("#state").prop(
				"checked", data.switches[x].state == 1);
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
	for (var x = 0; x < data.length; x++) {
		var row = $("thead #deviceRow").clone();
		$(row).removeClass("hidden");
		$(row).children().filter("#name").text(data[x].name);
		$(row).children().filter("#accessId").text(data[x].accessId);
		$(row).children().filter("#description").text(data[x].description);
		row.html(row.html().replace(/\?id=/g, "?id=" + data[x].id));
		$("#devicesList").append(row);
	}
}
// ------------------------------------------------
function initView() {
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