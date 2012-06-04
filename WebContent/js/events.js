function dateCellFormatter(cellvalue, options, rowObject) {
	return $.datepicker.formatDate('MM dd, yy', new Date(cellvalue));
}

function priceCellFormatter(cellvalue, options, rowObject) {
	return "$" + cellvalue;
}

function selectedRow(id) {
	if (id != null) {
		console.log("selected row id " + id);
		$.getJSON('EventServlet?id=' + id, function(data) {
			if (data != null) {
				var event = data.event;
				$("#event_name").empty();
				$("#event_name").append(event.name);
				$("#event_desc").empty();
				$("#event_desc").append(event.description);
				$("#event_venue").empty();
				$("#event_venue").append(event.venue);
				$("#event_date").empty();
				$("#event_date").append(
						$.datepicker.formatDate('MM dd, yy', new Date(
								event.date)));
				$("#event_price").empty();
				$("#event_price").append("$" + event.cost);
				$("#event_desc").empty();
				$("#event_desc").append(event.description);
				$("#event_seats").empty();
				$("#event_seats").append(event.available);
				$("#detail_wrapper").show();
			}
		});
	}
}

function selectedCategory() {
	console.log("selected category id " + $("#categories").val());
	// Clear search text
	$("#search").val("");
	// Set venue to all
	$("#venues").val(-1);
	var id = $("#categories").val();
	if (id == -1) {
		jQuery("#event_table").setGridParam({
			datatype : 'json',
			url : 'EventServlet'
		});
	} else {
		jQuery("#event_table").setGridParam({
			datatype : 'json',
			url : 'EventServlet?categoryid=' + id
		});
	}
	// Reload table
	jQuery("#event_table").trigger("reloadGrid");
}

function selectedVenue() {
	console.log("selected venue id " + $("#venues").val());
	// Clear search text
	$("#search").val("");
	// Set category to all
	$("#categories").val(-1);
	var id = $("#venues").val();
	if (id == -1) {
		jQuery("#event_table").setGridParam({
			datatype : 'json',
			url : 'EventServlet'
		});
	} else {
		jQuery("#event_table").setGridParam({
			datatype : 'json',
			url : 'EventServlet?venueid=' + id
		});
	}
	// Reload table
	jQuery("#event_table").trigger("reloadGrid");
}

function searchEvents() {
	// Set category to all
	$("#categories").val(-1);
	// Set venue to all
	$("#venues").val(-1);
	var text = $("#search").val();
	var url = 'EventServlet';
	if (text.length > 0) {
		url = url + '?search=' + text;
	}
	jQuery("#event_table").setGridParam({
		datatype : 'json',
		url : url
	});
	// Reload table
	jQuery("#event_table").trigger("reloadGrid");
}

function resetSearch() {
	$("#search").val("");
	searchEvents();
}

function loadDemoStuff() {
	console.log('loading demo data...');
	$.getJSON('EventServlet?demo', function(data) {
		console.log('Got response: ' + data.result);
		if (data.result == "success") {
			// Reload table
			jQuery("#event_table").setGridParam({
				datatype : 'json'
			});
			jQuery("#event_table").trigger("reloadGrid");
			// Reload categories
			loadCategories();
			// Reload venues
			loadVenues();
			console.log('loaded demo data');
		}
	});
	// Hide button
	$("#demo_button").hide();
}

function loadCategories() {
	$("#categories").html("");
	$("<option value='-1'>All</option>").appendTo("#categories");
	$.getJSON('CategoryServlet', function(data) {
		data.categories.forEach(function(item) {
			$(
					"<option value='" + item.category.id + "'>"
							+ item.category.name + "</option>").appendTo(
					"#categories");
		});
	});
}

function loadVenues() {
	$("#venues").html("");
	$("<option value='-1'>All</option>").appendTo("#venues");
	$.getJSON('VenueServlet', function(data) {
		data.venues.forEach(function(item) {
			$(
					"<option value='" + item.venue.id + "'>"
							+ item.venue.name + "</option>").appendTo(
					"#venues");
		});
	});
}

function hideDetails() {
	$("#detail_wrapper").hide();
}

function buyTicket() {
	$("#dialog-not-implemented").dialog({
		modal : true,
		buttons : {
			ok : function() {
				$(this).dialog("close");
			}
		}
	});
}

$(document).ready(function() {
	// Demo button
	$("#demo_button").button();
	$("#demo_button").click(loadDemoStuff);

	// Categories
	loadCategories();
	$("#categories").change(selectedCategory);
	
	// Venues
	loadVenues();
	$("#venues").change(selectedVenue);

	// Search
	$("#search_button").button();
	$("#search_button").click(searchEvents);
	$("#reset_button").button();
	$("#reset_button").click(resetSearch);

	// Detail
	hideDetails();
	$("#buy_button").button();
	$("#buy_button").click(buyTicket);
	$("#cancel_button").button();
	$("#cancel_button").click(hideDetails);

	// Build table
	$("#event_table").jqGrid({
		url : 'EventServlet',
		datatype : 'json',
		mtype : 'GET',
		jsonReader : {
			root : "events",
			repeatitems : false,
			id : 'event.id'
		},
		colNames : [ 'Name', 'Venue', 'Date', 'Category' ],
		colModel : [ {
			name : 'event.name',
			index : 'name',
			width : 100
		}, {
			name : 'event.venue',
			index : 'venue',
			width : 75
		}, {
			name : 'event.date',
			index : 'date',
			width : 75,
			formatter : dateCellFormatter
		}, {
			name : 'event.category',
			index : 'category',
			width : 75
		} ],
		gridview : true,
		autowidth : true,
		height : 'auto',
		loadonce : true,
		pager : jQuery('#pager'),
		caption : 'All Events',
		onSelectRow : selectedRow,
		hidegrid : false
	});

	// Add refresh button
	jQuery("#event_table").jqGrid('navGrid', '#pager', {
		edit : false,
		view : false,
		add : false,
		del : false,
		search : false,
		beforeRefresh : function() {
			// Reload categories
			loadCategories();
			// Reload venues
			loadVenues();
			// Reload table
			$(this).jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		}
	});
});