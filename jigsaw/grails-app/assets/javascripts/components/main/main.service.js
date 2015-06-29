'use strict';

angular.module('jigsawApp')
    .factory('RecallInfo', function ($http) {

        return {
            getStateCount: function(stateObj, startDate, endDate, callback, error)
            {
                callback = callback || angular.noop;
                error = error || angular.noop;


                $http({url: 'foodRecall/count',
                	method: "GET",
                	params: {stateCode: stateObj, startDate: startDate, endDate: endDate}}).success(function(data, status) {
                    callback(data, status);
                }).
                    error(error);
            },
            getRecallDetail: function(stateObj, startDate, endDate, upc, page, limit, callback, error)
            {
                callback = callback || angular.noop;
                error = error || angular.noop;

                $http({url: 'foodRecall/recalls',
                	method: "GET",
                	params: {stateCode: stateObj, startDate: startDate, endDate: endDate, upc: upc, skip:page, limit: limit}}).success(function(data, status) {
                    callback(data, status);
                }).
                    error(error);
            },
            getAllRecallDetail: function(callback, error)
            {
                callback = callback || angular.noop;
                error = error || angular.noop;

                $http({url: 'foodRecall/recalls',
                	method: "GET"}).success(function(data, status) {
                    callback(data, status);
                }).
                    error(error);
            }
        }
    });
