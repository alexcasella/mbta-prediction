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

	// Only green line stops right now
	$scope.stops = {"Copley": "70154", "Boylston": "70158", "Packards Corner": "70134", "South Street": "70110", 
	"Kenmore": "70150", "Warren Street": "70124", "Boston Univ. East": "70146", "Babcock Street": "70136", 
	"Science Park": "70207", "Harvard Ave.": "70130", "North Station": "70205", "Boston Univ. Central": "70144", 
	"Arlington": "70156", "Park Street": "70200", "Lechmere": "70209", "Washington Street": "70120", 
	"Boston Univ. West": "70142", "Griggs Street": "70128", "Saint Paul Street": "70140", "Boston College": "70106", 
	"Blandford Street": "70148", "Chiswick Road": "70114", "Hynes Convention Center": "70152", "Allston Street": "70126", 
	"Sutherland Road": "70116", "Chestnut Hill Ave.": "70112", "Haymarket": "70203", "Pleasant Street": "70138"};


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
				"Result": "Error"
			};
		});

		// Resets the form fields to blank
		$scope.pickedLine = '';
		$scope.pickedStartStop = '';
		$scope.pickedEndStop = '';
		// This.$setPristine();
		// This.$setUntouched();
	};

}]);

// For toggling between services and changing lines to display maps
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

