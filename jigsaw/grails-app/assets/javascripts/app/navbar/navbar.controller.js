'use strict';

angular.module('jigsawApp')
    .controller('NavbarController', function ($scope, $location, $state, NavInfo, $cookieStore) {
        $scope.$state = $state;
        $scope.viewSettings = $state.is('metrics');
        $scope.homePageAlert = {};
        $scope.tempAppAlert = {};
        $scope.isAuthenticated = $cookieStore.get("loggedIn") == "true";
        
        $scope.loadAppSettings = function(){
        	NavInfo.getAppSettings(function(data, status){
        		if (data.appAlert) {
        			$scope.homePageAlert.data = data.appAlert;
        		} else {
        			$scope.homePageAlert = {};
        		}
        		$scope.tempAppAlert.data = $scope.homePageAlert.data;
        	}, function(){
        		$scope.homePageAlert = {};
        		$scope.tempAppAlert = {};
        	});
        }
        
        $scope.updateAppSettings = function(){
        	NavInfo.updateAppSettings($scope.tempAppAlert.data, function(status){
        		$scope.hideAppSettings();
        		$scope.loadAppSettings();
        	}, function(){
        		$scope.loadAppSettings();
        	});
        }
        
        $scope.showAppSettings = function(){
        	$scope.loadAppSettings();
    		$("#mySettingsModal").modal({
    			backdrop: 'static',
    			keyboard: false,
    			show: true
    			});
        }
        
        $scope.hideAppSettings = function(){
    		$("#mySettingsModal").modal('toggle');
        }
        
    });
