'use strict';

angular.module('nothanks-ui').controller('LoginCtrl',
		function($scope, $location, Authentication) {
			$scope.newUser = {};
			$scope.login = {};

			$scope.logIn = function(email, password) {
				$scope.resetErrors();
				Authentication.logIn({
					email : email,
					password : password
				}, $scope.onLoggedIn, $scope.onLogInError);
			};

			$scope.onLoggedIn = function() {
				alert('Logged in correctly');
				$location.path("/");
			};

			$scope.onLogInError = function(data) {
				$scope.login.error = data.message;
			};

			$scope.createNewUser = function(email, password) {
				$scope.resetErrors();
				Authentication.addUser({
					email : email,
					password : password
				}, $scope.onNewUserCreated, $scope.onNewUserError);
			};

			$scope.onNewUserCreated = function() {
				alert('User created!');
			};

			$scope.onNewUserError = function(data) {
				$scope.newUser.error = data.message;
			};

			$scope.resetErrors = function() {
				$scope.newUser.error = undefined;
				$scope.login.error = undefined;
			};
		});
