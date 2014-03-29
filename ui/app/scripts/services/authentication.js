'use strict';

angular.module('nothanks-ui').service(
    'Authentication',
    function Authentication($http, Connection) {
        var _loggedIn = false;

        this.addUser = function (credentials, onSuccess, onFailure) {
            console.debug('creating new user: ' + credentials.email + ' ('
                + credentials.password + ')');

            var message = {
                cmd: 'createUser',
                data: {
                    email: credentials.email,
                    password: credentials.password
                }
            };

            Connection.send(message).then(onSuccess, onFailure);
        };

        this.logIn = function (credentials, onSuccess, onFailure) {
            console.debug('Logging ' + credentials.email + ' in');

            var message = {
                cmd: 'logIn',
                data: {
                    email: credentials.email,
                    password: credentials.password
                }
            };

            Connection.send(message).then(function (data) {
                // Successful login needs to be intercepted
                _loggedIn = true;
                onSuccess(data);
            }, onFailure);
        };

        this.isLoggedIn = function () {
            return _loggedIn;
        };
    });
