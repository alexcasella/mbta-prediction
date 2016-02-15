$(document).ready(function() { 
var line_array = ["Green", "Red", "Orange"];
var stops = ["stop1", "stop2", "stop3"] ;

  $('#Service_1').click(function() {

   $('#SERVICE').append('<select id="lines" class="form-control"></select>');
   
   for (var i = 0; i < line_array.length; i++) {
    $('#lines').append('<option>' + line_array[i] + '</option>');
      }

  $('#SERVICE').append('<select id="start_stop" class="form-control"></select>');
   
   for (var i = 0; i < stops.length; i++) {
    $('#start_stop').append('<option>' + stops[i] + '</option>');
      }

  $('#SERVICE').append('<select id="end_stop" class="form-control"></select>');
   
   for (var i = 0; i < stops.length; i++) {
    $('#end_stop').append('<option>' + stops[i] + '</option>');
      }

  });

  $('#Service_2').click(function() { 
   alert("Service 2");
  });

  $('#Service_3').click(function() { 
   alert("Service 3");
  });


  });
