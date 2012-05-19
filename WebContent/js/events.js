$(document).ready(
		function() {
			$.getJSON('EventServlet?demo', function(data) {
				console.log(data);
			});

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
							index : 'name'
						}, {
							name : 'event.venue',
							index : 'venue'
						}, {
							name : 'event.date',
							index : 'date',
							align : 'right'
						}, {
							name : 'event.category',
							index : 'category',
						}, {
							name : 'event.description',
							index : 'description',
							sortable : false
						}, {
							name : 'event.cost',
							index : 'cost',
							align : 'right',
							width : 75
						} ],
						gridview : true,
						autowidth : true,
						height : 'auto',
						loadonce : true,
						pager : jQuery('#pager'),
						caption : 'All Events',
						onRowSelect : function(ids) {
							// This doesn't work for some reason
							console.log("Called onRowSelect");
							if (ids != null) {
								alert("selected: " + ids);
							} else {
								alert("nothing selected?");
							}
						}
					});
		});