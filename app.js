var application_root = __dirname;
var express = require("express");
var app = express();
var http = require('http').Server(app);
var io = require('socket.io')(http);   
var path = require("path");
var request = require("request");


app.use('/', express.static(__dirname + '/public'));


app.get('/', function(req, res) {
  res.sendfile('index.html');
});

//Launch serverindex

http.listen(3000, function () {
  console.log('Example app listening on port 3000!');
});



io.on('connection', function(socket){
  console.log('a client connected');
  socket.on('file',function(file){
  	console.log(file);
  	var query = 'http://www.LocationOfServer.com' + '/' + file.Service + '/' + file.Line + '/' + file.Start 
  	console.log(query); 
  	request(query, function (error, response, body) { //might need to type cast query as string as requst is expecting a string
    if (!error && response.statusCode == 200) {
        console.log(body); 
    }
		});
	});
});

// request('http://www.modulus.io', function (error, response, body) {
//     if (!error && response.statusCode == 200) {
//         console.log(body); 
//     }
// });