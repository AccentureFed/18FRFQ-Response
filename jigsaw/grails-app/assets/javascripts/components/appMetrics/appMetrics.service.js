'use strict';

angular.module('jigsawApp')
    .factory('MetricsService', function ($http) {

        return {
            getMetrics: function(callback, error)
            {
                callback = callback || angular.noop;
                error = error || angular.noop;

                $http({url: 'metrics/metrics',
                	method: "GET"}).success(function(data, status) {
                    callback(data, status);
                }).
                    error(error);
            },
            getHealth: function(callback, error)
            {
                callback = callback || angular.noop;
                error = error || angular.noop;

                $http({url: 'metrics/healthcheck',
                	method: "GET"}).success(function(data, status) {
                    callback(data, status);
                }).
                    error(error);
            },
            getThreads: function(callback, error)
            {
                callback = callback || angular.noop;
                error = error || angular.noop;

                $http({url: 'metrics/threads',
                	method: "GET"}).success(function(data, status) {
                    callback(data, status);
                }).
                    error(error);
            }
        }
    });