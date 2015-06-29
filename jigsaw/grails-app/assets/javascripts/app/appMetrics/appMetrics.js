'use strict';

angular.module('jigsawApp')
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('metrics', {
                url: '/appMetrics',
                views: {
                    'navbar@': {
                        templateUrl: 'app/navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content@': {
                        templateUrl: 'app/appMetrics/metrics.html',
                        controller: 'MetricsController'
                    },
                    'footer@': {
                        templateUrl: 'app/footer/footer.html',
                        controller: 'FooterController'
                    }
                }
            });
    }]);

