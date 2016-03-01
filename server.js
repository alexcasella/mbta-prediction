var express = require("express");
var app = express();

app.use(express.static(__dirname));

app.get("/results", function (req, res) {
	
	var m = /stop\s(\d)/;
		
	var startMatch = m.exec( req.query.startStop );

	var startVal = 0;
	if (startMatch && startMatch.length > 1) {
		startVal = parseInt(startMatch[1]);
	}

	var endVal = 0;
	var endMatch = m.exec( req.query.endStop );
	if (endMatch && endMatch.length > 1) {
		endVal = parseInt(endMatch[1]);
	}

	res.json({Line: req.query.line, StartStop: startVal, EndStop: endVal});
});

app.listen(3000, function () {
	console.log("Listening on port 3000");
});
