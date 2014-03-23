'use strict';

angular.module('nothanks-ui')
    .controller('LoginCtrl', function ($scope, Authentication) {
        $scope.newUser = {};
        $scope.login = {};

        $scope.logIn = function (email, password) {
            Authentication.logIn(email, password);
        };

        $scope.createNewUser = function (email, password) {
            $scope.newUser.error = undefined;
            Authentication.addUser(email, password)
                .success(function (data) {
                    alert('User created!');
                })
                .error(function (data, status, headers, config) {
                    $scope.newUser.error = data.message;
                });
        };
    });
