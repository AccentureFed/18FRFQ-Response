'use strict';

angular.module('jigsawApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('map', {
                parent: 'site',
                url: '/',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'components/map/appMap.html',
                        controller: 'MapController'
                    }
                }

            });
    });