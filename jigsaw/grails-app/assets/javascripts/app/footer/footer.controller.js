'use strict';

angular.module('jigsawApp')
    .controller('FooterController', function ($scope, $location, $state, FooterInfo, $cookieStore, LoginService) {
        $scope.$state = $state;
        $scope.showMetricsLink = $state.is('home');
        $scope.isAuthenticated = $cookieStore.get("loggedIn") == "true";
        $scope.showLogoutLink = ($state.is('metrics') && $scope.isAuthenticated)
        
        $scope.version = "";

        $scope.getVersion = function(){
        	FooterInfo.getVersion(function(data, status){
        		if (status != null && status == 200 && data != null) {
        			$scope.version = data.version;
        		}
        	}, function(){
        		$scope.version = "1";
        	});
        }
        
        $scope.showMetrics = function () {
            $scope.isAuthenticated = $cookieStore.get("loggedIn") == "true";
            if ($scope.isAuthenticated) {
            	$scope.$state.go('metrics');            	
            } else {
            	$scope.$state.go('login');
            }

        }
        
        $scope.logOut = function() {
        	LoginService.logOut();
            $scope.showMetricsLink = $state.is('home');
            $scope.isAuthenticated = $cookieStore.get("loggedIn") == "true";
            $scope.showLogoutLink = ($state.is('metrics') && $scope.isAuthenticated)
        }
        
        angular.element(document).ready(function () {
            $scope.getVersion();
        });
    });
