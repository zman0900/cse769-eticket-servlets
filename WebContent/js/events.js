function dateCellFormatter(cellvalue, options, rowObject) {
	return $.datepicker.formatDate('MM dd, yy', new Date(cellvalue));
}

function priceCellFormatter(cellvalue, options, rowObject) {
	return "$" + cellvalue;
}

function selectedRow(id) {
	if (id != null) {
		console.log("selected row id " + id);
	}
}

function selectedCategory() {
	console.log("selected id " + $("#categories").val());
	// Clear search text
	$("#search").val("");
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

function searchEvents() {
	// Set category to all
	$("#categories").val(-1);
	var text = $("#search").val();
	jQuery("#event_table").setGridParam({
		datatype : 'json',
		url : 'EventServlet?search=' + text
	});
	// Reload table
	jQuery("#event_table").trigger("reloadGrid");
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

$(document).ready(
		function() {
			// Demo button
			$("#demo_button").button();
			$("#demo_button").click(loadDemoStuff);

			// Categories
			loadCategories();
			$("#categories").change(selectedCategory);
			
			// Search
			$("#search_button").button();
			$("#search_button").click(searchEvents);

			// Build table
			$("#event_table").jqGrid(
					{
						url : 'EventServlet',
						datatype : 'json',
						mtype : 'GET',
						jsonReader : {
							root : "events",
							repeatitems : false,
							id : 'event.id'
						},
						colNames : [ 'Name', 'Venue', 'Date', 'Category',
								'Description', 'Price' ],
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
						}, {
							name : 'event.description',
							index : 'description',
							sortable : false,
							widht : 200
						}, {
							name : 'event.cost',
							index : 'cost',
							width : 40,
							formatter : priceCellFormatter
						} ],
						gridview : true,
						autowidth : true,
						height : 'auto',
						loadonce : true,
						pager : jQuery('#pager'),
						caption : 'All Events',
						onSelectRow : selectedRow
					});

			// Add refresh button
			jQuery("#event_table").jqGrid('navGrid', '#pager', {
				edit : false,
				view : false,
				add : false,
				del : false,
				search : false,
				beforeRefresh : function() {
					$(this).jqGrid('setGridParam', {
						datatype : 'json'
					}).trigger('reloadGrid');
				}
			});
		});