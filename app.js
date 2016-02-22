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
		getResults = function(lines, stops) {
		var results = null;
		var req = {
		    lines: lines,
		    stops: stops
		   }
		$http({
		method: 'GET', 
		url: 'blah',
		params : req
		}).success(function(data){
		     results = data;

		}).error(function(data){

		});

	     return results;
	    }
  	}
}]);

app.controller('MainCtrl', ['$scope', function($scope, backend_api){

	$scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
	    if (angular.isDefined(toState.data.pageTitle)) {
	        $scope.pageTitle = toState.data.pageTitle;
	    }
	});

  	$scope.stops = ['stop 1', 'stop 2', 'stop 3', 'stop 4', 'stop 5'];
  	$scope.lines = ['Green', 'Red', 'Blue', 'Orange', 'Silver'];

  	$scope.results = frontend_server.getResults(lines,stops);
}]);