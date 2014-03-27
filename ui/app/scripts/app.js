'use strict';

angular.module('nothanks-ui',
		[ 'ngCookies', 'ngResource', 'ngSanitize', 'ngRoute' ]).config(
		function($routeProvider) {
			$routeProvider.when('/', {
				templateUrl : 'views/main.html',
				controller : 'MainCtrl'
			}).when('/login', {
				templateUrl : 'views/login.html',
				controller : 'LoginCtrl'
			}).otherwise({
				redirectTo : '/'
			});
		});