'use strict';

angular.module('jigsawApp')
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'app/main/main.html',
                        controller: 'MainController'
                    },
                    'header@': {
                        templateUrl: 'components/navbar/navbar.html',
                        controller: 'NavbarController'
                    }
                }

            });
    }]);

