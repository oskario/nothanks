'use strict';

angular.module('nothanks-ui')
    .controller('LoginCtrl', function ($scope, Authentication) {
        $scope.create = {};
        $scope.login = {};

        $scope.logIn = function () {
            Authentication.logIn($scope.login.email, $scope.login.password);
        };

        $scope.create = function () {
            Authentication.create($scope.create.email, $scope.create.password);
        };
    });
