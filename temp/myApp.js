var app = angular.module('myApp', ['ui.router']);

app.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {

  $stateProvider.state('home', {
    url: '/home',
    templateUrl: '/home.html',
    controller: 'MainCtrl',
  })
}]);

app.factory('frontend_server', ['$http', function($http) {
  return {
    getResults: function(color, stop) {
      var req = {
        color: color,
        stop: stop
      }
      return $http({
        method: 'GET',
        url: 'blah',
        params: req
      });
    }
  }
}]);

app.controller('MainCtrl', ['$scope','frontend_server', function($scope, frontend_server) {
  
  $scope.colors = ["Green", "Black", "Orange"];
  $scope.stops = ["stop1", "stop2", "stop3"] ;
  
  $scope.pickedColor = '';
  $scope.pickedStop = '';

  $scope.getResults = function(){
    frontend_server.getResults($scope.pickedColor, $scope.pickedStop).success(function(data) {
      $scope.results = data;
    }).error(function(data) {
      $scope.results = {
        "Value": "it takes 10 minutes"
      };
    });
  };
}]);