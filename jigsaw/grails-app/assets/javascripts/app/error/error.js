'use strict';

angular.module('jigsawApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('error', {
                parent: 'site',
                url: '/error',
                data: {
                    roles: [],
                    pageTitle: 'errors.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/error/error.html'
                    }
                }
            })
            .state('accessdenied', {
                parent: 'site',
                url: '/accessdenied',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'app/error/accessdenied.html'
                    }
                }
            });
    });
