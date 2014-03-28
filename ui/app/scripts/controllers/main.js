'use strict';

angular.module('nothanks-ui').controller('MainCtrl',
		function($scope, $location, Authentication) {
			if (!Authentication.isLoggedIn()) {
				$location.path("/login");
			}
		});
