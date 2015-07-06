'use strict';

angular.module('jigsawApp')
    .controller('AuthController', function ($scope, $state, authService, $cookieStore, LoginService) {
        $scope.$state = $state;
        $scope.viewSettings = $state.is('metrics');
        $scope.isAuthenticated = $cookieStore.get("loggedIn") == "true";
        $scope.authError = null;
        
        $scope.showRecalls = function() {
        	$scope.$state.go('home');
        }
        
        $scope.logIn = function () {
        	if ($("#username").val() != "" && $("#password").val() != "") {
	        	LoginService.logIn($scope.authData.username, $scope.authData.password, function(config){
	        		$scope.isAuthenticated = $cookieStore.get("loggedIn") == "true";
	        		$scope.$state.go('metrics');
	        		$scope.authError = null;
	        	}, function(data){
	        		$scope.authData.username = '';
	        		$scope.authData.password = '';
	        		$scope.authError = true;
	        	});
        	}
        }
        
        angular.element(document).ready(function () {
            $scope.isAuthenticated = $cookieStore.get("loggedIn") == "true";
            
            if($scope.isAuthenticated) {
            	$state.go('metrics');
            }
        });
    });
