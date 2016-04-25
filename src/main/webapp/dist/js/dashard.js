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

	$(add_button)
			.click(
					function(e) { // on add input button click
						e.preventDefault();
						$(wrapper)
								.append(
										'<div>Code:<input name="metrics['
												+ x
												+ '].code"> Name:<input name="metrics['
												+ x
												+ '].name"><button class="remove_field">Remove</button></div>');
						x++;
					});

	$(wrapper).on("click", ".remove_field", function(e) { // user click on
		// remove text
		e.preventDefault();
		$(this).parent('div').remove();
	})
};

function upsertSwitchesAddButton(x) {
	var wrapper = $("#switches_wrapper"); // Fields wrapper
	var add_button = $("#switches_add_button"); // Add button ID

	$(add_button)
			.click(
					function(e) { // on add input button click
						e.preventDefault();
						$(wrapper)
								.append(
										'Name:<input name="switches['
												+ x
												+ '].name"> Description:<input name="switches['
												+ x
												+ '].description"> Pin:<input name="switches['
												+ x
												+ '].pin"><button class="remove_field">Remove</button></div>'); // add
						// input
						// box
						x++; // text box increment
					});

	$(wrapper).on("click", ".remove_field", function(e) { // user click on
		// remove text
		e.preventDefault();
		$(this).parent('div').remove();
	})
};
// ------------------------------------------------
function configDeviceUpsert(data) {
	$("#id").val(data.id);
	$("#name").val(data.name);
	$("#description").val(data.description);
	var x = 0;
	for (; x < data.metrics.length; x++) {
		$("#metrics_wrapper")
				.append(
						'<div><input id="metrics'
								+ x
								+ 'id" name="metrics['
								+ x
								+ '].id" type="hidden" value="'
								+ data.metrics[x].id
								+ '">Code:<input id="metrics'
								+ x
								+ 'code" name="metrics['
								+ x
								+ '].code" value="'
								+ data.metrics[x].code
								+ '"> Name:<input id="metrics'
								+ x
								+ 'name" name="metrics['
								+ x
								+ '].name" value="'
								+ data.metrics[x].name
								+ '"><button class="remove_field">Remove</button></div>');
		$('#metrics_wrapper #metrics' + x + 'code').val(data.metrics[x].code);
		$('metrics_wrapper #metrics' + x + 'name').val(data.metrics[x].name);
	}
	upsertMetricsAddButton(x);
	x = 0;
	for (; x < data.switches.length; x++) {
		$("#switches_wrapper")
				.append(
						'<div><input id="switches'
								+ x
								+ 'id" name="switches['
								+ x
								+ '].id" type="hidden" value="'
								+ data.switches[x].id
								+ '">Name:<input id="switches'
								+ x
								+ 'name" name="switches['
								+ x
								+ '].name" value="'
								+ data.switches[x].name
								+ '"> Description:<input id="switches'
								+ x
								+ 'description" name="switches['
								+ x
								+ '].description" value="'
								+ data.switches[x].description
								+ '"> Pin:<input id="switches'
								+ x
								+ 'pin" name="switches['
								+ x
								+ '].pin" value="'
								+ data.switches[x].pin
								+ '"><button class="remove_field">Remove</button></div>');
		$('#switches_wrapper #switches' + x + 'name').text(
				data.switches[x].name);
		$('#switches_wrapper #switches' + x + 'description').text(
				data.switches[x].description);
		$('#switches_wrapper #switches' + x + 'pin').text(data.switches[x].pin);
	}
	upsertSwitchesAddButton(x);
}

function configDeviceView(data) {
	$("#name").text(data.name);
	$("#description").text(data.description);
	for (var x = 0; x < data.metrics.length; x++) {
		$("#metrics_wrapper").append(
				'<div>Code:<span id="code"></span></br>'
						+ 'Name:<span id="name"></span></br>'
						+ 'Value:<span id="value"></span></br>'
						+ 'Date:<span id="date"></span></br>' + '</div>');
		$("#metrics_wrapper #code").text(data.metrics[x].code);
		$("#metrics_wrapper #name").text(data.metrics[x].name);
		$("#metrics_wrapper #value").text(data.metrics[x].value);
		$("#metrics_wrapper #date").text(new Date(data.metrics[x].date));
	}
	for (var x = 0; x < data.switches.length; x++) {
		$("#switches_wrapper")
				.append(
						'<div class="switch" >Name:<span id="name"></span></br>Description:<span id="description"></span></br>'
								+ 'Pin:<span id="pin"></span></br>'
								+ 'State:<span id="state"></span></br>'
								+ '<button class="switch_toggle">Toggle</button></br>'
								+ '</div>');
		$("#switches_wrapper div.switch").last()
				.attr('id', data.switches[x].id);
		$("#switches_wrapper #name").last().text(data.switches[x].name);
		$("#switches_wrapper #description").last().text(
				data.switches[x].description);
		$("#switches_wrapper #pin").last().text(data.switches[x].pin);
		$("#switches_wrapper #state").last().text(data.switches[x].state);
		$("#switches_wrapper div.switch button").last().attr("data-id",data.switches[x].id);
		$("#switches_wrapper div.switch button").last().on("click", function(e) {
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
}

function updateSwitchState(data){
	for (var x = 0; x < data.length; x++) {
		$("#switches_wrapper div.switch#"+ data[x].id +" #state").text(data[x].state);
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