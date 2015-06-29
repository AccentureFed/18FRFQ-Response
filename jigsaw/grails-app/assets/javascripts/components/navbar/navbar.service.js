'use strict';

angular.module('jigsawApp')
    .factory('NavInfo', function ($http) {

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

                $http({url: 'application/appSettings',
                	method: "GET"}).success(function(data, status) {
                    callback(data, status);
                }).
                    error(error);
            },
            updateAppSettings: function(appSettings, callback, error)
            {
                callback = callback || angular.noop;
                error = error || angular.noop;
                $http.post('application/updateSettings/', {settings: appSettings}).success(function(status) {
                    callback(status);
                }).
                    error(error);
            }
        }
    });