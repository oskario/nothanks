'use strict';

angular.module('nothanks-ui')
    .service('Authentication', function Authentication($http) {
        this.addUser = function (email, password) {
            console.debug('creating new user: ' + email + ' (' + password + ')');

            return $http({
                method: 'POST',
                url: '/api/user',
                data: {
                    email: email,
                    password: password
                }});
        };

        this.logIn = function (email, password) {
            alert('Logging ' + email + ' in');
        };
    });
