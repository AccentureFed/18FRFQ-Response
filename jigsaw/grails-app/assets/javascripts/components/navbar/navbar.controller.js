'use strict';

angular.module('jigsawApp')
    .controller('NavbarController', function ($scope, $location, $state) {
        $scope.$state = $state;
    });
