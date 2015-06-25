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
                	params: {stateCode: stateObj, startDate: startDate, endDate: endDate}}).success(function(data) {
                    callback(data);
                }).
                    error(error);
            },
            getRecallDetail: function(stateObj, startDate, endDate, page, limit, callback, error)
            {
                callback = callback || angular.noop;
                error = error || angular.noop;

                $http({url: 'foodRecall/getAll',
                	method: "GET",
                	params: {stateCode: stateObj, startDate: startDate, endDate: endDate, skip:page, limit: limit}}).success(function(data, headers) {
                    callback(data, headers);
                }).
                    error(error);
            },
            getAllRecallDetail: function(callback, error)
            {
                callback = callback || angular.noop;
                error = error || angular.noop;

                $http({url: 'foodRecall/recalls',
                	method: "GET"}).success(function(data, headers) {
                    callback(data, headers);
                }).
                    error(error);
            }
        }
    });
