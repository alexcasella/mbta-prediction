$(document).ready(function() { 
var line_array = ["Green", "Red", "Orange"];
var stops = ["stop1", "stop2", "stop3"] ;

   for (var i = 0; i < line_array.length; i++) {
    $('#lines').append('<option>' + line_array[i] + '</option>');
      }


   
   for (var i = 0; i < stops.length; i++) {
    $('#start_stop').append('<option>' + stops[i] + '</option>');
      }

   
   for (var i = 0; i < stops.length; i++) {
    $('#end_stop').append('<option>' + stops[i] + '</option>');
      }


  $('#menu').hide();

$('#Service_1').click(function() {
   
   $('#menu').toggle();

});

  $('#Service_2').click(function() { 
   alert("Service 2");
  });

  $('#Service_3').click(function() { 
   alert("Service 3");
  });


  });
