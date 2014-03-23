'use strict';

angular.module('nothanks-ui')
    .controller('LoginCtrl', function ($scope) {
        $scope.create = {};
        $scope.login = {};

        $scope.logIn = function () {
            alert('Logging ' + $scope.login.email + ' in...');
        };

        $scope.create = function () {
            alert('Creating new account ' + $scope.create.email);
        };
    });
