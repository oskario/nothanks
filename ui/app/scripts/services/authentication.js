'use strict';

angular.module('nothanks-ui')
    .service('Authentication', function Authentication() {
        this.create = function (email, password) {
            alert('Creating new account ' + email);
        };

        this.logIn = function (email, password) {
            alert('Logging ' + email + ' in');
        };
    });
