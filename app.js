var app = angular.module('mbta', ['ui.router']);

app.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider){

	$stateProvider.state('home', {
		url: '/home',
		templateUrl: '/home.html',
		controller: 'MainCtrl',
		data:{ 
		   pageTitle: 'Boston Now' 
		}
	})
	$urlRouterProvider.otherwise('home');

}]);

app.factory('frontend_server', ['$http', function($http){
	return { 
		getResults : function(line, startStop, endStop) {
			var req = {};

			if (line !== '') req.line = line;
			if (startStop !== '') req.startStop = startStop;
			if (endStop !== '') req.endStop = endStop;

			return $http({
				method: 'GET', 
				url: 'results',
				params : req
			});
		}
	}
}]);

app.controller('MainCtrl', ['$scope', 'frontend_server', function($scope, frontend_server){

	// Page title
	$scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
		if (angular.isDefined(toState.data.pageTitle)) {
			$scope.pageTitle = toState.data.pageTitle;
		}
	});

	$scope.lines = ['Green', 'Red', 'Blue', 'Orange'];

	$scope.stops = ['stop 1', 'stop 2', 'stop 3', 'stop 4', 'stop 5'];

	// This will be sent to the FE server
	$scope.pickedLine = '';

	$scope.pickedStartStop = '';

	$scope.pickedEndStop = '';

	// Gets JSON object from FE server
	$scope.getResults = function() {
		frontend_server.getResults($scope.pickedLine, $scope.pickedStartStop, $scope.pickedEndStop)
		.success(function(data) {
			$scope.results = data;
		}).error(function(err) {
			$scope.results = {
				"Result": "Alex Alex Alex Alex Alex"
			};
		});
	};
}]);

// For toggling between services 
app.controller('serviceCtrl', ['$scope', function($scope){
	$scope.model = {
		showmenu: 1
	};

	var lineUrls = {
		Red: 'Red.png',
		Green: 'Green.png',
		Blue: 'Blue.png',
		Orange: 'Orange.png'
	};

	$scope.getLineUrl = function (name) {
		if (lineUrls[name] !== undefined) {
			return lineUrls[name];
		} else {
			return false;
		}
	};

	$scope.toggleMenu = function(val){
		$scope.model.showmenu = val;
	};
}]);

