$(document).ready(function() { 
	var line_array = ["Green", "Red", "Orange"];
	var stops = ["stop1", "stop2", "stop3"] ;
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

