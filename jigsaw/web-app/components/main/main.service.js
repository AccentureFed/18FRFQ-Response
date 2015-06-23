'use strict';

angular.module('jigsawApp')
    .factory('MainService', function ($resource) {

        return $resource('foodRecall/', {}, {
            'getStateMeta': {method: 'GET', isArray: true},
            'getAlerts': {method: 'GET', isArray: true},
            'recalls': {method: 'GET', isArray: true},
            'register': {method: 'POST'}
        });

    });