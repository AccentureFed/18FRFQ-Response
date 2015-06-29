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
            }
        }
    });