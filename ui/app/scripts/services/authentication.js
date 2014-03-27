'use strict';

angular.module('nothanks-ui').service(
		'Authentication',
		function Authentication($http) {
			var _loggedIn = false;

			this.addUser = function(credentials, onSuccess, onFailure) {
				console.debug('creating new user: ' + credentials.email + ' (' + credentials.password
						+ ')');

				$http({
					method : 'POST',
					url : '/api/user',
					data : {
						email : credentials.email,
						password : credentials.password
					}
				}).success(function(data) {
					if (onSuccess)
						onSuccess(data);
				}).error(function(data) {
					if (onFailure)
						onFailure(data);
				});
			};

			this.logIn = function(credentials, onSuccess, onFailure) {
				console.debug('Logging ' + credentials.email + ' in');

				$http({
					method : 'POST',
					url : '/api/login',
					data : {
						email : credentials.email,
						password : credentials.password
					}
				}).success(function(data) {
					_loggedIn = true;
					
					if (onSuccess)
						onSuccess(data);
				}).error(function(data) {
					_loggedIn = false;
					
					if (onFailure)
						onFailure(data);
				});
			};

			this.isLoggedIn = function() {
				return _loggedIn;
			};
		});
