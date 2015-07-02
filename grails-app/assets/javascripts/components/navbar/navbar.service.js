'use strict';

angular.module('jigsawApp')
    .factory('NavInfo', function ($http, $resource) {
        return {
            getVersion: function(callback, error)
            {
                callback = callback || angular.noop;
                error = error || angular.noop;

                $http({url: 'application/version',
                	method: "GET"}).success(function(data, status) {
                    callback(data, status);
                }).
                    error(error);
            },
            getAppSettings: function(callback, error)
            {
                callback = callback || angular.noop;
                error = error || angular.noop;

                $http({url: 'appSettings/getSettings',
                	method: "GET"}).success(function(data, status) {
                    callback(data, status);
                }).
                    error(error);
            },
            updateAppSettings: function(appAlert, callback, error)
            {
                callback = callback || angular.noop;
                error = error || angular.noop;
                
                $http({url: 'appSettings/updateSettings',
                	method: "POST",
                	params: {appAlert: appAlert}}).success(function(status) {
                    callback(status);
                }).
                    error(error);
            }
        }
    });