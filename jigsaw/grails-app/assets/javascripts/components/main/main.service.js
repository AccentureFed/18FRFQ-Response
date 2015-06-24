'use strict';

angular.module('jigsawApp')
    .factory('RecallInfo', function ($http) {

        return {
            getStateCount: function(stateObj, callback, error)
            {
                callback = callback || angular.noop;
                error = error || angular.noop;


                $http({url: 'foodRecall/count',
                	method: "GET",
                	params: {stateCode: stateObj}}).success(function(data) {
                    callback(data);
                }).
                    error(error);
            },
            getRecallDetail: function(stateObj, page, limit, callback, error)
            {
                callback = callback || angular.noop;
                error = error || angular.noop;

                $http({url: 'foodRecall/getAll',
                	method: "GET",
                	params: {stateCode: stateObj, skip:page, limit: limit}}).success(function(data, headers) {
                    callback(data, headers);
                }).
                    error(error);
            }
        }


    });
