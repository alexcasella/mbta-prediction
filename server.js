var express = require("express");

var app = express();

var request = require('request');

var http = require('http').Server(app);

app.use(express.static(__dirname));


app.get("/results", function (req, res) {
	
	// Regex for processing stop 1, stop 2, ...
	// var m = /stop\s(\d)/;
		
	// var startMatch = m.exec( req.query.startStop );

	// var startVal = 0;
	// if (startMatch && startMatch.length > 1) {
	// 	startVal = parseInt(startMatch[1]);
	// }

	// var endVal = 0;
	// var endMatch = m.exec( req.query.endStop );
	// if (endMatch && endMatch.length > 1) {
	// 	endVal = parseInt(endMatch[1]);
	// }

	// res.json({
	// 	Line: req.query.line, 
	// 	StartStop: startVal, 
	// 	EndStop: endVal
	// });


	// Front end server response only
	// res.json({
	// 	Line: req.query.line, 
	// 	StartStop: req.query.startStop, 
	// 	EndStop: req.query.endStop
	// });

	var query = 'http://messenger-env.us-west-2.elasticbeanstalk.com/webapi/messages/greenline/' + req.query.startStop;
	console.log(query); 
	request(query, function (error, response, body) { //might need to type cast query as string as requst is expecting a string

	    if (!error && response.statusCode == 200) {
	        console.log(body); 
	        // socket.emit('body', body);
			res.json({
				Line: req.query.line, 
				StartStop: req.query.startStop, 
				EndStop: req.query.endStop,
				alert: body
			});
	    }
	});
});

http.listen(3000, function () {
	console.log("Listening on port 3000");
});