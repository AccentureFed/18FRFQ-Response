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
                    'navbar@': {
                        templateUrl: 'app/navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content@': {
                        templateUrl: 'app/main/main.html',
                        controller: 'MainController'
                    },
                    'footer@': {
                        templateUrl: 'app/footer/footer.html',
                        controller: 'FooterController'
                    }
                }

            });
    }]);

