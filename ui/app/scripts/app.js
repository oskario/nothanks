'use strict';

angular.module('uiApp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute'
])
    .config(function($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/main.html',
                controller: 'MainCtrl'
            })
            .when('/test', {
                templateUrl: 'views/test.html',
                controller: 'MainCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    });