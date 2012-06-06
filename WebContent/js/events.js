// This is the javascrip file that handle all the AJAX for the main Events.html
// page

// Formats dates in the jqgrid
function dateCellFormatter(cellvalue, options, rowObject) {
	return $.datepicker.formatDate('MM dd, yy', new Date(cellvalue));
}

// Not used, could format prices in the jqgrid
function priceCellFormatter(cellvalue, options, rowObject) {
	return "$" + cellvalue;
}

// This is called when a row (event) in the jqgrid is selected to display the
// event details
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

				$.getJSON('VenueServlet?id=' + event.venue_id, function(data) {
					if (data != null) {
						var venue = data.venue;
						$("#venue_desc").empty();
						$("#venue_desc").append(venue.description);
						$("#venue_address").empty();
						$("#venue_address").append(venue.address);
						$("#venue_city").empty();
						$("#venue_city").append(venue.city + ",");
						$("#venue_state").empty();
						$("#venue_state").append(venue.state);
						$("#venue_zip").empty();
						$("#venue_zip").append(venue.zip);
					}
				});

				$("#detail_wrapper").show();
			}
		});
	}
}

// This is called when a category is selected from the category dropdown to
// display only those events
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

// This is called when a venue is selected from the venue dropdown to
// display only those events
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

// This is called when the search button is clicked (or enter pressed) to only
// display matching events
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

// This is called when the reset button is clicked
function resetSearch() {
	$("#search").val("");
	searchEvents();
}

// This is called when the "load demo events/etc..." button is clicked
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

// This loads the categories into the category dropdown
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

// This loads the venues into the venue dropdown
function loadVenues() {
	$("#venues").html("");
	$("<option value='-1'>All</option>").appendTo("#venues");
	$.getJSON('VenueServlet', function(data) {
		data.venues.forEach(function(item) {
			$(
					"<option value='" + item.venue.id + "'>" + item.venue.name
							+ "</option>").appendTo("#venues");
		});
	});
}

// This hides the event details section
function hideDetails() {
	$("#detail_wrapper").hide();
	jQuery("#event_table").resetSelection();
}

// This is called when the buy ticket button is clicked.
// Currently only displays a place-holder dialog
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

// Called by the browser when the DOM is ready for manipulation
$(document).ready(function() {
	// Setup demo button
	$("#demo_button").button();
	$("#demo_button").click(loadDemoStuff);

	// Setup categories dropdown
	loadCategories();
	$("#categories").change(selectedCategory);

	// Setup venues dropdown
	loadVenues();
	$("#venues").change(selectedVenue);

	// Setup search field/button and reset button
	$("#search_button").button();
	$("#search_button").click(searchEvents);
	$("#reset_button").button();
	$("#reset_button").click(resetSearch);
	$("#search").keypress(function(e) {
		code = (e.keyCode ? e.keyCode : e.which);
		if (code == 13) {
			e.preventDefault();
			searchEvents();
		}
	});

	// Setup buttons in event detail view
	$("#buy_button").button();
	$("#buy_button").click(buyTicket);
	$("#cancel_button").button();
	$("#cancel_button").click(hideDetails);

	// Build jqgrid table of events
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

	// Add refresh button to events table
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