// Author: Alex Casella
// Date: Spring 2016

var app = angular.module('mbta', ['ui.router']);


app.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider){

	$stateProvider.state('home', {
		url: '/home',
		templateUrl: '/home.html',
		controller: 'MainCtrl',
		data:{ 
		   pageTitle: 'Boston Now' 
		},
		reloadOnSearch: false
	})

	// If we have a separate results page then this:
	// 
	// }).state('resultsPage', {
	// 	url: '/resultsPage',
	// 	templateUrl: '/resultsPage.html',
	// 	controller: 'MainCtrl',
	// 	data: {
	// 		pageTitle: 'Prediction Results'
	// }

	$urlRouterProvider.otherwise('home');

}]);


// Communication between Angular and Node
app.factory('frontend_server', ['$http', function($http){
	return { 
		getResults : function(line, subline, direction, startStop, endStop, time, timeOfDay) {

			var req = {};

			if (line) req.line = line;
			
			if (subline) req.subline = subline;

			if (direction) req.direction = direction;

			if (startStop) req.startStop = startStop;

			if (endStop) req.endStop = endStop;

			req.timeOfDay = timeOfDay;
			req.time = time;


			return $http({
				method: 'GET', 
				url: 'results',
				// url: 'results?line='+req.line+'&&subline='+req.subline+'&&direction='+req.direction+'&&startStop='+req.startStop+'&&endStop='+req.endStop,
				params : req
			});
		}
	}
}]);


// Service that redirects home page to results page upon submission of form
// When moving to another page you are accessing a different $scope
// so the data will not be there. Use a service to get around this problem
// Then inject GlobalState into controller and set $scope.results = GlobalState.results
// app.factory('GlobalState', [function() {
//    return {
//        results: null
//    };
// }]);



app.controller('MainCtrl', ['$scope', '$state', '$location' ,'frontend_server', function($scope, $state, $location, frontend_server){

	// Page title
	$scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
		if (angular.isDefined(toState.data.pageTitle)) {
			$scope.pageTitle = toState.data.pageTitle;
		}
	});

	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


	$scope.menu = ['one', 'two'];

	$scope.pickedColor = 'default';
	$scope.pickedSubline = '';
	$scope.pickedDirection = '';
	$scope.pickedStartStop = '';
	$scope.pickedColorSwitched = '';
	$scope.pickedSublineSwitched = '';
	$scope.pickedDirectionSwitched = '';
	$scope.pickedEndStop = '';

	$scope.switchLines = ['yes', 'no'];

	// colors
	$scope.lines = ['Green', 'Red', 'Blue', 'Orange'];

	// If color = Green
	$scope.sublines = ['B', 'C', 'D', 'E'];

	$scope.directions = ['Inbound', 'Outbound'];

	$scope.stopSelection = [];

	$scope.stopSelectionSwitched = [];

	var stops = {
		'GreenBInbound': [{"stopName": "South Street", "stopID": "70110"}, {"stopName": "Boston Univ. East", "stopID": "70146"}, {"stopName": "Arlington", "stopID": "70156"}, 
		{"stopName": "Copley", "stopID": "70154"}, {"stopName": "Hynes Convention Center", "stopID": "70152"}, {"stopName": "Chestnut Hill Ave.", "stopID": "70112"}, {"stopName": "Boston Univ. West", "stopID": "70142"}, 
		{"stopName": "Boston College", "stopID": "70106"}, {"stopName": "Boylston", "stopID": "70158"}, {"stopName": "Babcock Street", "stopID": "70136"}, {"stopName": "Warren Street", "stopID": "70124"}, 
		{"stopName": "Science Park", "stopID": "70207"}, {"stopName": "North Station", "stopID": "70205"}, {"stopName": "Lechmere", "stopID": "70209"}, {"stopName": "Haymarket", "stopID": "70203"}, 
		{"stopName": "Packards Corner", "stopID": "70134"}, {"stopName": "Pleasant Street", "stopID": "70138"}, {"stopName": "Griggs Street", "stopID": "70128"}, {"stopName": "Blandford Street", "stopID": "70148"}, 
		{"stopName": "Government Center", "stopID": "70201"}, {"stopName": "Allston Street", "stopID": "70126"}, {"stopName": "Sutherland Road", "stopID": "70116"}, {"stopName": "Washington Street", "stopID": "70120"}, 
		{"stopName": "Boston Univ. Central", "stopID": "70144"}, {"stopName": "Park Street", "stopID": "70200"}, {"stopName": "Kenmore", "stopID": "70150"}, {"stopName": "Chiswick Road", "stopID": "70114"}, 
		{"stopName": "Harvard Ave.", "stopID": "70130"}, {"stopName": "Saint Paul Street", "stopID": "70140"}],
		'GreenBOutbound': [{"stopName": "South Street", "stopID": "70111"}, {"stopName": "Boston Univ. East", "stopID": "70147"}, {"stopName": "Arlington", "stopID": "70157"}, 
		{"stopName": "Washington Street", "stopID": "70121"}, {"stopName": "Saint Paul Street", "stopID": "70141"}, {"stopName": "Hynes Convention Center", "stopID": "70153"}, 
		{"stopName": "Chestnut Hill Ave.", "stopID": "70113"}, {"stopName": "Boston Univ. West", "stopID": "70143"}, {"stopName": "Boston College", "stopID": "70107"}, {"stopName": "Boylston", "stopID": "70159"}, 
		{"stopName": "Babcock Street", "stopID": "70137"}, {"stopName": "Warren Street", "stopID": "70125"}, {"stopName": "Science Park", "stopID": "70208"}, {"stopName": "North Station", "stopID": "70206"}, 
		{"stopName": "Lechmere", "stopID": "70210"}, {"stopName": "Haymarket", "stopID": "70204"}, {"stopName": "Packards Corner", "stopID": "70135"}, {"stopName": "Pleasant Street", "stopID": "70139"}, 
		{"stopName": "Sutherland Road", "stopID": "70117"}, {"stopName": "Chiswick Road", "stopID": "70115"}, {"stopName": "Blandford Street", "stopID": "70149"}, {"stopName": "Government Center", "stopID": "70202"}, 
		{"stopName": "Allston Street", "stopID": "70127"}, {"stopName": "Griggs Street", "stopID": "70129"}, {"stopName": "Park Street", "stopID": "70196"}, {"stopName": "Boston Univ. Central", "stopID": "70145"}, 
		{"stopName": "Kenmore", "stopID": "70151"}, {"stopName": "Copley", "stopID": "70155"}, {"stopName": "Harvard Ave.", "stopID": "70131"}],
		'GreenCInbound': [{"stopName": "Kent Street", "stopID": "70216"}, {"stopName": "Washington Square", "stopID": "70230"}, {"stopName": "Arlington", "stopID": "70156"}, 
		{"stopName": "Saint Mary Street", "stopID": "70212"}, {"stopName": "Saint Paul Street", "stopID": "70218"}, {"stopName": "Hynes Convention Center", "stopID": "70152"}, 
		{"stopName": "Coolidge Corner", "stopID": "70220"}, {"stopName": "Summit Ave.", "stopID": "70224"}, {"stopName": "Englewood Ave.", "stopID": "70236"}, {"stopName": "Cleveland Circle", "stopID": "70238"}, 
		{"stopName": "North Station", "stopID": "70205"}, {"stopName": "Hawes Street", "stopID": "70214"}, {"stopName": "Haymarket", "stopID": "70203"}, {"stopName": "Tappan Street", "stopID": "70232"}, 
		{"stopName": "Dean Road", "stopID": "70234"}, {"stopName": "Fairbanks Street", "stopID": "70228"}, {"stopName": "Lechmere", "stopID": "70209"}, {"stopName": "Boylston", "stopID": "70158"}, 
		{"stopName": "Government Center", "stopID": "70201"}, {"stopName": "Science Park", "stopID": "70207"}, {"stopName": "Park Street", "stopID": "70200"}, {"stopName": "Kenmore", "stopID": "70150"}, 
		{"stopName": "Brandon Hall", "stopID": "70226"}, {"stopName": "Copley", "stopID": "70154"}],
		'GreenCOutbound': [{"stopName": "Kent Street", "stopID": "70215"}, {"stopName": "Hynes Convention Center", "stopID": "70153"}, {"stopName": "Arlington", "stopID": "70157"}, 
		{"stopName": "Saint Mary Street", "stopID": "70211"}, {"stopName": "Saint Paul Street", "stopID": "70217"}, {"stopName": "Washington Square", "stopID": "70229"}, {"stopName": "Boylston", "stopID": "70159"}, 
		{"stopName": "Summit Ave.", "stopID": "70223"}, {"stopName": "Englewood Ave.", "stopID": "70235"}, {"stopName": "Cleveland Circle", "stopID": "70237"}, {"stopName": "North Station", "stopID": "70206"}, 
		{"stopName": "Hawes Street", "stopID": "70213"}, {"stopName": "Haymarket", "stopID": "70204"}, {"stopName": "Tappan Street", "stopID": "70231"}, {"stopName": "Dean Road", "stopID": "70233"}, 
		{"stopName": "Fairbanks Street", "stopID": "70227"}, {"stopName": "Lechmere", "stopID": "70210"}, {"stopName": "Coolidge Corner", "stopID": "70219"}, {"stopName": "Government Center", "stopID": "70202"}, 
		{"stopName": "Science Park", "stopID": "70208"}, {"stopName": "Park Street", "stopID": "70197"}, {"stopName": "Kenmore", "stopID": "70151"}, {"stopName": "Brandon Hall", "stopID": "70225"}, {"stopName": "Copley", "stopID": "70155"}],
		'GreenDInbound': [{"stopName": "Arlington", "stopID": "70156"}, {"stopName": "Woodland", "stopID": "70162"}, {"stopName": "Hynes Convention Center", "stopID": "70152"}, 
		{"stopName": "Beaconsfield", "stopID": "70176"}, {"stopName": "Riverside", "stopID": "70160"}, {"stopName": "Brookline Village", "stopID": "70180"}, {"stopName": "Newton Centre", "stopID": "70170"}, 
		{"stopName": "Chestnut Hill", "stopID": "70172"}, {"stopName": "North Station", "stopID": "70205"}, {"stopName": "Lechmere", "stopID": "70209"}, {"stopName": "Haymarket", "stopID": "70203"}, 
		{"stopName": "Brookline Hills", "stopID": "70178"}, {"stopName": "Newton Highlands", "stopID": "70168"}, {"stopName": "Waban", "stopID": "70164"}, {"stopName": "Longwood", "stopID": "70182"}, 
		{"stopName": "Fenway", "stopID": "70186"}, {"stopName": "Boylston", "stopID": "70158"}, {"stopName": "Government Center", "stopID": "70201"}, {"stopName": "Science Park", "stopID": "70207"}, {"stopName": "Reservoir", "stopID": "70174"}, 
		{"stopName": "Park Street", "stopID": "70200"}, {"stopName": "Kenmore", "stopID": "70150"}, {"stopName": "Copley", "stopID": "70154"}, {"stopName": "Eliot", "stopID": "70166"}],
		'GreenDOutbound': [{"stopName": "Arlington", "stopID": "70157"}, {"stopName": "Woodland", "stopID": "70163"}, {"stopName": "Hynes Convention Center", "stopID": "70153"}, 
		{"stopName": "Beaconsfield", "stopID": "70177"}, {"stopName": "Riverside", "stopID": "70161"}, {"stopName": "Brookline Village", "stopID": "70181"}, {"stopName": "Newton Centre", "stopID": "70171"}, 
		{"stopName": "Boylston", "stopID": "70159"}, {"stopName": "North Station", "stopID": "70206"}, {"stopName": "Lechmere", "stopID": "70210"}, {"stopName": "Haymarket", "stopID": "70204"}, {"stopName": "Brookline Hills", "stopID": "70179"}, 
		{"stopName": "Newton Highlands", "stopID": "70169"}, {"stopName": "Waban", "stopID": "70165"}, {"stopName": "Longwood", "stopID": "70183"}, {"stopName": "Fenway", "stopID": "70187"}, {"stopName": "Chestnut Hill", "stopID": "70173"}, 
		{"stopName": "Government Center", "stopID": "70202"}, {"stopName": "Science Park", "stopID": "70208"}, {"stopName": "Park Street", "stopID": "70198"}, {"stopName": "Reservoir", "stopID": "70175"}, 
		{"stopName": "Kenmore", "stopID": "70151"}, {"stopName": "Copley", "stopID": "70155"}, {"stopName": "Eliot", "stopID": "70167"}],
		'GreenEInbound': [{"stopName": "Fenwood Road", "stopID": "70252"}, {"stopName": "Riverway", "stopID": "70256"}, {"stopName": "Prudential", "stopID": "70240"}, {"stopName": "Northeastern University", "stopID": "70244"}, 
		{"stopName": "Park Street", "stopID": "70200"}, {"stopName": "Arlington", "stopID": "70156"}, {"stopName": "Mission Park", "stopID": "70254"}, {"stopName": "Longwood Medical Area", "stopID": "70248"}, 
		{"stopName": "Copley", "stopID": "70154"}, {"stopName": "Museum of Fine Arts", "stopID": "70246"}, {"stopName": "Symphony", "stopID": "70242"}, {"stopName": "Back of the Hill", "stopID": "70258"}, 
		{"stopName": "Lechmere", "stopID": "70209"}, {"stopName": "North Station", "stopID": "70205"}, {"stopName": "Boylston", "stopID": "70158"}, {"stopName": "Haymarket", "stopID": "70203"}, {"stopName": "Brigham Circle", "stopID": "70250"}, 
		{"stopName": "Government Center", "stopID": "70201"}, {"stopName": "Science Park", "stopID": "70207"}, {"stopName": "Heath Street", "stopID": "70260"}],
		'GreenEOutbound': [{"stopName": "Prudential", "stopID": "70239"}, {"stopName": "Longwood Medical Area", "stopID": "70247"}, {"stopName": "Riverway", "stopID": "70255"}, 
		{"stopName": "Boylston", "stopID": "70159"}, {"stopName": "Back of the Hill", "stopID": "70257"}, {"stopName": "Park Street", "stopID": "70199"}, {"stopName": "Arlington", "stopID": "70157"}, 
		{"stopName": "Mission Park", "stopID": "70253"}, {"stopName": "Fenwood Road", "stopID": "70251"}, {"stopName": "Copley", "stopID": "70155"}, {"stopName": "Museum of Fine Arts", "stopID": "70245"}, 
		{"stopName": "Symphony", "stopID": "70241"}, {"stopName": "Northeastern University", "stopID": "70243"}, {"stopName": "North Station", "stopID": "70206"}, {"stopName": "Lechmere", "stopID": "70210"}, 
		{"stopName": "Haymarket", "stopID": "70204"}, {"stopName": "Brigham Circle", "stopID": "70249"}, {"stopName": "Government Center", "stopID": "70202"}, {"stopName": "Science Park", "stopID": "70208"}, 
		{"stopName": "Heath Street", "stopID": "70259"}],
		'RedInbound': [{"stopName": "Fields Corner", "stopID": "70090"}, {"stopName": "Downtown Crossing", "stopID": "70078"}, {"stopName": "Alewife", "stopID": "70061"}, {"stopName": "Porter", "stopID": "70066"}, 
		{"stopName": "Kendall/MIT", "stopID": "70072"}, {"stopName": "JFK/Umass", "stopID": "70096"}, {"stopName": "Andrew", "stopID": "70084"}, {"stopName": "Harvard", "stopID": "70068"}, {"stopName": "Ashmont", "stopID": "70094"}, 
		{"stopName": "Central", "stopID": "70070"}, {"stopName": "Quincy Center", "stopID": "70102"}, {"stopName": "North Quincy", "stopID": "70098"}, {"stopName": "South Station", "stopID": "70080"}, 
		{"stopName": "Quincy Adams", "stopID": "70104"}, {"stopName": "Shawmut", "stopID": "70092"}, {"stopName": "Davis", "stopID": "70064"}, {"stopName": "Braintree", "stopID": "70105"}, {"stopName": "Park Street", "stopID": "70076"}, 
		{"stopName": "Wollaston", "stopID": "70100"}, {"stopName": "Charles/MGH", "stopID": "70074"}, {"stopName": "Savin Hill", "stopID": "70088"}, {"stopName": "Broadway", "stopID": "70082"}],
		'RedOutbound': [{"stopName": "Downtown Crossing", "stopID": "70077"}, {"stopName": "Fields Corner", "stopID": "70089"}, {"stopName": "Ashmont", "stopID": "70093"}, 
		{"stopName": "Alewife", "stopID": "70061"}, {"stopName": "Porter", "stopID": "70065"}, {"stopName": "Kendall/MIT", "stopID": "70071"}, {"stopName": "JFK/Umass", "stopID": "70085"}, {"stopName": "Andrew", "stopID": "70083"}, 
		{"stopName": "Harvard", "stopID": "70067"}, {"stopName": "Central", "stopID": "70069"}, {"stopName": "Quincy Center", "stopID": "70101"}, {"stopName": "North Quincy", "stopID": "70097"}, 
		{"stopName": "South Station", "stopID": "70079"}, {"stopName": "Quincy Adams", "stopID": "70103"}, {"stopName": "Shawmut", "stopID": "70091"}, {"stopName": "Davis", "stopID": "70063"}, {"stopName": "Braintree", "stopID": "70105"}, 
		{"stopName": "Park Street", "stopID": "70075"}, {"stopName": "Wollaston", "stopID": "70099"}, {"stopName": "Charles/MGH", "stopID": "70073"}, {"stopName": "Savin Hill", "stopID": "70087"}, {"stopName": "Broadway", "stopID": "70081"}],
		'BlueInbound': [{"stopName": "Orient Heights", "stopID": "70052"}, {"stopName": "Bowdoin", "stopID": "70038"}, {"stopName": "State Street", "stopID": "70042"}, 
		{"stopName": "Wood Island", "stopID": "70050"}, {"stopName": "Airport", "stopID": "70048"}, {"stopName": "Aquarium", "stopID": "70044"}, {"stopName": "Beachmont", "stopID": "70056"}, {"stopName": "Wonderland", "stopID": "70060"}, 
		{"stopName": "Suffolk Downs", "stopID": "70054"}, {"stopName": "Revere Beach", "stopID": "70058"}, {"stopName": "Maverick", "stopID": "70046"}, {"stopName": "Government Center", "stopID": "70040"}],
		'BlueOutbound': [{"stopName": "Orient Heights", "stopID": "70051"}, {"stopName": "Bowdoin", "stopID": "70038"}, {"stopName": "State Street", "stopID": "70041"}, 
		{"stopName": "Government Center", "stopID": "70039"}, {"stopName": "Wood Island", "stopID": "70049"}, {"stopName": "Airport", "stopID": "70047"}, {"stopName": "Aquarium", "stopID": "70043"}, {"stopName": "Beachmont", "stopID": "70055"}, 
		{"stopName": "Wonderland", "stopID": "70060"}, {"stopName": "Revere Beach", "stopID": "70057"}, {"stopName": "Maverick", "stopID": "70045"}, {"stopName": "Suffolk Downs", "stopID": "70053"}],
		'OrangeInbound': [{"stopName": "Stony Brook", "stopID": "70005"}, {"stopName": "Massachusetts Ave.", "stopID": "70013"}, {"stopName": "Oak Grove", "stopID": "70036"}, 
		{"stopName": "Assembly", "stopID": "70279"}, {"stopName": "Community College", "stopID": "70029"}, {"stopName": "Forest Hills", "stopID": "70001"}, {"stopName": "State Street", "stopID": "70023"}, 
		{"stopName": "Green Street", "stopID": "70003"}, {"stopName": "Malden Center", "stopID": "70035"}, {"stopName": "Chinatown", "stopID": "70019"}, {"stopName": "Sullivan Square", "stopID": "70031"}, 
		{"stopName": "Tufts Medical Center", "stopID": "70017"}, {"stopName": "Wellington", "stopID": "70033"}, {"stopName": "North Station", "stopID": "70027"}, {"stopName": "Jackson Square", "stopID": "70007"}, 
		{"stopName": "Downtown Crossing", "stopID": "70021"}, {"stopName": "Ruggles", "stopID": "70011"}, {"stopName": "Back Bay", "stopID": "70015"}, {"stopName": "Haymarket", "stopID": "70025"}, 
		{"stopName": "Roxbury Crossing", "stopID": "70009"}],
		'OrangeOutbound': [{"stopName": "Stony Brook", "stopID": "70004"}, {"stopName": "Haymarket", "stopID": "70024"}, {"stopName": "Assembly", "stopID": "70278"}, 
		{"stopName": "Community College", "stopID": "70028"}, {"stopName": "Forest Hills", "stopID": "70001"}, {"stopName": "Chinatown", "stopID": "70018"}, {"stopName": "Sullivan Square", "stopID": "70030"}, 
		{"stopName": "State Street", "stopID": "70022"}, {"stopName": "Wellington", "stopID": "70032"}, {"stopName": "Tufts Medical Center", "stopID": "70016"}, {"stopName": "Roxbury Crossing", "stopID": "70008"}, 
		{"stopName": "Ruggles", "stopID": "70010"}, {"stopName": "Back Bay", "stopID": "70014"}, {"stopName": "Jackson Square", "stopID": "70006"}, {"stopName": "Malden Center", "stopID": "70034"}, 
		{"stopName": "North Station", "stopID": "70026"}, {"stopName": "Downtown Crossing", "stopID": "70020"}, {"stopName": "Green Street", "stopID": "70002"}, {"stopName": "Oak Grove", "stopID": "70036"}, 
		{"stopName": "Massachusetts Ave.", "stopID": "70012"}]
	};


	$scope.setStopSelection = function() {
		if ($scope.pickedColor == 'Green') { // Green line

			// console.log($scope.pickedColor + $scope.pickedSubline + $scope.pickedDirection);
			// console.log(stops[$scope.pickedColor + $scope.pickedSubline + $scope.pickedDirection]);

			$scope.stopSelection = stops[$scope.pickedColor + $scope.pickedSubline + $scope.pickedDirection];

		} else if ($scope.pickedColor && $scope.pickedDirection) { // Red, Blue, Orange lines

			$scope.stopSelection = stops[$scope.pickedColor + $scope.pickedDirection];
		} 
	}

	$scope.setStopSelectionSwitched = function() {
		if ($scope.pickedColorSwitched == 'Green') {

			$scope.stopSelectionSwitched = stops[$scope.pickedColorSwitched + $scope.pickedSublineSwitched + $scope.pickedDirectionSwitched];

		} else if ($scope.pickedColorSwitched && $scope.pickedDirectionSwitched) { 

			$scope.stopSelectionSwitched = stops[$scope.pickedColorSwitched + $scope.pickedDirectionSwitched];
		} 
	}
	

	$scope.timeOfDays = ['Morning', 'Afternoon', 'Evening'];

	$scope.timesMorning = [{"timeOption": "5:00 AM", "timeDigit": "5"}, {"timeOption": "5:30 AM", "timeDigit": "530"}, {"timeOption": "6:00 AM", "timeDigit": "6"}, 
	{"timeOption": "6:30 AM", "timeDigit": "630"}, {"timeOption": "7:00 AM", "timeDigit": "7"}, {"timeOption": "7:30 AM", "timeDigit": "730"}, {"timeOption": "8:00 AM", "timeDigit": "8"}, 
	{"timeOption": "8:30 AM", "timeDigit": "830"},{"timeOption": "9:00 AM", "timeDigit": "9"}, {"timeOption": "9:30 AM", "timeDigit": "930"}, 
	 {"timeOption": "10:00 AM", "timeDigit": "10"}, {"timeOption": "10:30 AM", "timeDigit": "1030"}, {"timeOption": "11:00 AM", "timeDigit": "11"}, {"timeOption": "11:30 AM", "timeDigit": "1130"}];

	$scope.timesAfternoon = [{"timeOption": "12:00 PM", "timeDigit": "12"}, {"timeOption": "12:30 PM", "timeDigit": "1230"}, 
	{"timeOption": "1:00 PM", "timeDigit": "13"}, {"timeOption": "1:30 PM", "timeDigit": "1330"}, {"timeOption": "2:00 PM", "timeDigit": "14"}, 
	{"timeOption": "2:30 PM", "timeDigit": "1430"}, {"timeOption": "3:00 PM", "timeDigit": "15"}, {"timeOption": "3:30 PM", "timeDigit": "1530"}, 
	{"timeOption": "4:00 PM", "timeDigit": "16"}, {"timeOption": "4:30 PM", "timeDigit": "1630"}];

	$scope.timesEvening = [{"timeOption": "5:00 PM", "timeDigit": "17"}, {"timeOption": "5:30 PM", "timeDigit": "1730"}, {"timeOption": "6:00 PM", "timeDigit": "18"},
	{"timeOption": "6:30 PM", "timeDigit": "1830"}, {"timeOption": "7:00 PM", "timeDigit": "19"}, {"timeOption": "7:30 PM", "timeDigit": "1930"}, 
	{"timeOption": "8:00 PM", "timeDigit": "20"}, {"timeOption": "8:30 PM", "timeDigit": "2030"}, {"timeOption": "9:00 PM", "timeDigit": "21"},
	{"timeOption": "9:30 PM", "timeDigit": "2130"}, {"timeOption": "10:00 PM", "timeDigit": "22"}, {"timeOption": "10:30 PM", "timeDigit": "2230"}, 
	{"timeOption": "11:00 PM", "timeDigit": "23"}, {"timeOption": "11:30 PM", "timeDigit": "2330"}, {"timeOption": "12:00 AM", "timeDigit": "0"}, 
	{"timeOption": "12:30 AM", "timeDigit": "030"}, {"timeOption": "1:00 AM", "timeDigit": "1"}];


	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	// maps URL to dropdown
	var params = $location.search()

	if (params.time) {
		$scope.pickedTime = params.time;
	}

	if (params.timeOfDay) {
		$scope.pickedTimeOfDay = params.timeOfDay;
	}

	if (params.line) {
		$scope.pickedColor = params.line;
	}

	if (params.subline) {
		$scope.pickedSubline = params.subline;
	}

	if (params.direction) {
		$scope.pickedDirection = params.direction;
	}

	if (params.startStop) {
		if ($scope.pickedColor == 'Green') { 

			$scope.stopSelection = stops[$scope.pickedColor + $scope.pickedSubline + $scope.pickedDirection];

		} else if ($scope.pickedColor && $scope.pickedDirection) { 

			$scope.stopSelection = stops[$scope.pickedColor + $scope.pickedDirection];
		} 
		
		$scope.pickedStartStop = params.startStop;
	}

	if (params.endStop) {
		if ($scope.pickedColor !== 'default' && $scope.pickedColor == 'Green') { 

			$scope.stopSelection = stops[$scope.pickedColor + $scope.pickedSubline + $scope.pickedDirection];

		} else if ($scope.pickedColor && $scope.pickedDirection) { 

			$scope.stopSelection = stops[$scope.pickedColor + $scope.pickedDirection];
		} 
		
		$scope.pickedEndStop = params.endStop;
	}

	if (params.switchLines) {
		$scope.switchLines = params.switchLines;
	}

	if (params.line2) {
		$scope.pickedColorSwitched = params.line2;
	}

	if (params.subline2) {
		$scope.pickedSublineSwitched = params.subline2;
	}

	if (params.direction2) {
		$scope.pickedDirectionSwitched = params.direction2;
	}

	if (params.switchLines === 'yes' && params.endStop) {
		if ($scope.switchLines && $scope.pickedColorSwitched == "Green") { 

			$scope.stopSelectionSwitched = stops[$scope.pickedColorSwitched + $scope.pickedSublineSwitched + $scope.pickedDirectionSwitched];

		} else if ($scope.pickedColorSwitched && $scope.pickedDirectionSwitched) { 

			$scope.stopSelectionSwitched = stops[$scope.pickedColorSwitched + $scope.pickedDirectionSwitched];
		} 
		
		$scope.pickedEndStop = params.endStop;
	}


	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


	// Communication bewteen UI and Angular
	// Gets JSON object from FE server
	$scope.getResults = function() {

		// This makes url dynamic
		$location.search('timeOfDay', $scope.pickedTimeOfDay);
		$location.search('time', $scope.pickedTime);
		

		$location.search('line', $scope.pickedColor);
		
		if ($scope.pickedColor != 'Green'){
			$scope.pickedSubline = '';
			$location.search('subline', '');
		} else {
			$location.search('subline', $scope.pickedSubline);
		}

		$location.search('direction', $scope.pickedDirection);
		$location.search('startStop', $scope.pickedStartStop);
		$location.search('switchLines', $scope.switchLines);
		$location.search('line2', $scope.pickedColorSwitched);
		$location.search('subline2', $scope.pickedSublineSwitched);
		$location.search('direction2', $scope.pickedDirectionSwitched);
		$location.search('endStop', $scope.pickedEndStop);
		
		
		frontend_server.getResults(
			$scope.pickedColor, $scope.pickedSubline, $scope.pickedDirection, 
			$scope.pickedStartStop, $scope.pickedEndStop, $scope.pickedTime, 
			$scope.pickedTimeOfDay)
		.success(function(data) {
			
			$scope.results = data;

			// v This is if I want results to be shown on a new page
			//GlobalState.results = data;
			//$state.go('resultsPage');

		}).error(function(err) {
			//GlobalState.results = {
			$scope.results = {
				"Result": "Internal error"
			};
		});
	};

	// $scope.results = GlobalState.results;


	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


	// Client side input validation
	$scope.validateForm = function() {

		// Menu 1

		var colorSelection = document.getElementById("pickedColor");

		if (colorSelection.validity.valueMissing) {
			colorSelection.setCustomValidity("Please select a MBTA color");
		} else {
			colorSelection.setCustomValidity("");
		}

		var directionSelection = document.getElementById("pickedDirection");

		if (directionSelection.validity.valueMissing) {
			directionSelection.setCustomValidity("Please select a direction of travel");
		} else {
			directionSelection.setCustomValidity("");
		}

		var startstopSelection = document.getElementById("pickedStartStop");

		if (startstopSelection.validity.valueMissing) {
			startstopSelection.setCustomValidity("Please choose a starting stop");
		} else {
			startstopSelection.setCustomValidity("");
		}


		// Menu 2


		// Menu 3

		var timeSelection = document.getElementById("pickedTimeOfDay3");

		if (timeSelection.validity.valueMissing) {
			timeSelection.setCustomValidity("Please select a time of travel");
		} else {
			timeSelection.setCustomValidity("");
		}

		var colorSelection = document.getElementById("pickedColor3");

		if (colorSelection.validity.valueMissing) {
			colorSelection.setCustomValidity("Please select a MBTA color");
		} else {
			colorSelection.setCustomValidity("");
		}

		var directionSelection = document.getElementById("pickedDirection3");

		if (directionSelection.validity.valueMissing) {
			directionSelection.setCustomValidity("Please select a direction of travel");
		} else {
			directionSelection.setCustomValidity("");
		}

		var startstopSelection = document.getElementById("pickedStartStop3");

		if (startstopSelection.validity.valueMissing) {
			startstopSelection.setCustomValidity("Please choose a starting stop");
		} else {
			startstopSelection.setCustomValidity("");
		}
	}
}]);



// For toggling between services and changing lines to display maps
app.controller('serviceCtrl', ['$scope', function($scope){
	$scope.model = {
		showmenu: 1
	};

	var lineUrls = {
		default: '/images/mbta_map.jpg',
		Red: '/images/Red.png',
		Green: '/images/Green.png',
		Blue: '/images/Blue.png',
		Orange: '/images/Orange.png'
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


	// Toggle alert detail
	$scope.showAlertDetail = false;

	$scope.toggleAlertDetail = function() {
        $scope.showAlertDetail = !$scope.showAlertDetail;
    };
}]);


// app.controller('backgroundImageCtrl', function($scope, $http) {

//     $scope.setBackground = {
//         'background' : 'url(/images/mbta_map.jpg)'
//     };

// })
