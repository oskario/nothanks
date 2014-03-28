'use strict';

angular.module('nothanks-ui').service(
		'Connection',
		function Connection($q, $rootScope) {
			var url = 'ws://localhost:9000/api/ws';
			var Service = {};
			var callbacks = {};
			var currentCallbackId = 0;
			var ws = new WebSocket(url);

			ws.onopen = function() {
				console.log("Socket has been opened!");
			};

			ws.onmessage = function(message) {
				listener(JSON.parse(message.data));
			};

			function sendRequest(request) {
				var defer = $q.defer();
				var callbackId = getCallbackId();
				callbacks[callbackId] = {
					time : new Date(),
					cb : defer
				};
				request.id = callbackId;
				console.log('Sending request', request);
				ws.send(JSON.stringify(request));
				return defer.promise;
			}

			function listener(data) {
				var messageObj = data;
				console.log("Received data from websocket: ", messageObj);
				// If an object exists with callback_id in our callbacks object,
				// resolve it
				if (callbacks.hasOwnProperty(messageObj.id)) {
					// console.log(callbacks[messageObj.id]);

					if (messageObj.cmd != 'error') {
						$rootScope.$apply(callbacks[messageObj.id].cb
								.resolve(messageObj));
					} else {
						$rootScope.$apply(callbacks[messageObj.id].cb
								.reject(messageObj));
					}
					delete callbacks[messageObj.id];
				}
			}
			// This creates a new callback ID for a request
			function getCallbackId() {
				currentCallbackId += 1;
				if (currentCallbackId > 10000) {
					currentCallbackId = 0;
				}
				return currentCallbackId.toString();
			}

			// Define a "getter" for getting customer data
			Service.send = function(message) {
				// Storing in a variable for clarity on what sendRequest returns
				var promise = sendRequest(message);
				return promise;
			}

			return Service;
		});