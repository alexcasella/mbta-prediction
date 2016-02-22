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
	var frontend_server = {};

	// frontend_server.getResults = function() {
	// 	return $http.get('/result').success(function(data){
	// 		angular.copy(data, db.issues);
	// 	});
	// };

	frontend_server.getResults = function() {
      return $http({
        method: 'JSONP', 
        url: 'blah'
      });
    }

    return backend_api;
}]);

app.controller('MainCtrl', ['$scope', function($scope, backend_api){

	$scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
	    if (angular.isDefined(toState.data.pageTitle)) {
	        $scope.pageTitle = toState.data.pageTitle;
	    }
	});

  	// $scope.stops = ['stop 1', 'stop 2', 'stop 3', 'stop 4', 'stop 5'];
  	// $scope.lines = ['Green', 'Red', 'Blue', 'Orange', 'Silver'];

  	$scope.results = backend_api.results;
}]);