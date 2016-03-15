$(document).ready(function() { 
	var obj = {"Copley": "70155", "Boylston": "70159", "Boston College": "70107", "Packards Corner": "70135", "South Street": "70111", "Kenmore": "70151", "Warren Street": "70125", "Boston Univ. East": "70147", "Babcock Street": "70137", "Science Park": "70208", "Harvard Ave.": "70131", "North Station": "70206", "Boston Univ. Central": "70145", "Lechmere": "70210", "Chestnut Hill Ave.": "70113", "Park Street": "70196", "Washington Street": "70121", "Griggs Street": "70129", "Saint Paul Street": "70141", "Arlington": "70157", "Blandford Street": "70149", "Chiswick Road": "70115", "Hynes Convention Center": "70153", "Allston Street": "70127", "Sutherland Road": "70117", "Boston Univ. West": "70143", "Haymarket": "70204", "Pleasant Street": "70139"}
	var line_array = ["Green", "Red", "Orange"];
	var stops = Object.keys(obj) ;
	var times = ["1:00 PM, 2:00 PM, 3:00 PM"];

	for (var i = 0; i < line_array.length; i++) {
		$('.lines').append('<option>' + line_array[i] + '</option>');
	}

	for (var i = 0; i < stops.length; i++) {
		$('.start_stop').append('<option>' + stops[i] + '</option>');
	}


	for (var i = 0; i < stops.length; i++) {
		$('.end_stop').append('<option>' + stops[i] + '</option>');
	}
	
	for (var i = 0; i < times.length; i++) {
		$('#times').append('<option' + times[i] + '</option>');
	}

	$('#menu_service1, #menu_service2, #menu_service3').hide();
	
	
	$('#Service_1').click(function() {
		$('#menu_service2, #menu_service3').hide();
		$('#menu_service1').toggle();
	});

	$('#Service_2').click(function() { 
		$('#menu_service1, #menu_service3').hide();
		$('#menu_service2').toggle();
	});

	$('#Service_3').click(function() { 
		$('#menu_service1, #menu_service2').hide();
		$('#menu_service3').toggle();
	});


});

