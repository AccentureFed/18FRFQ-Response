'use strict';

angular.module('jigsawApp')
    .controller('MetricsController', function ($scope, $location, $state, MetricsService, $cookieStore) {
        $scope.$state = $state;
        $scope.viewSettings = $state.is('metrics');
        
        $scope.isAuthenticated = $cookieStore.get("loggedIn") == "true";

        $scope.updatingMetrics = false;
        $scope.metrics = null;
        $scope.health = null;
        $scope.servicesStats = null;
        $scope.cachesStats = null;
        $scope.threadDump = null;
        
        $scope.getMetrics = function() {
        	if ($scope.isAuthenticated){
	        	$scope.updatingMetrics = true;
	        	MetricsService.getMetrics(function(data, status){
	        		$scope.metrics = data;
	        	}, function(){
	        		$scope.metrics = null;
	        	});
	        	
	        	MetricsService.getHealth(function(data, status){
	        		if (status == 200 && data && data.filestorage && data.filestorage.message && data.filestorage.healthy) {
		        		$scope.health = data;
		                $scope.free_space_bytes = $scope.health.filestorage.message.split(',')[0]; 
		                $scope.total_space_bytes = $scope.health.filestorage.message.split(',')[1]; 
		                $scope.free_space_gigs = $scope.free_space_bytes / 1073741824; 
		                $scope.free_space_ratio = $scope.free_space_bytes / $scope.total_space_bytes;
		                $scope.healthyDiskClass = $scope.health.filestorage.healthy;
		                $scope.healthyDBClass = $scope.health.database.healthy;
	        		}
	        	}, function(){
	        		$scope.health = null;
	        	});
	        	
	        	MetricsService.getThreads(function(data, status){
	        		$scope.threadDump = data;
	        	}, function(){
	        		$scope.threadDump = null;
	        	});
	        	
	        	$scope.updatingMetrics = false;
        	}
        }
        
        $scope.showThreads = function(){
    		$("#threadBtn").disabled = true;
    		$("#threadDumpModal").modal({
    			backdrop: 'static',
    			keyboard: false,
    			show: true
    			});
    		$("#threadBtn").disabled = false;
        }
        
        $scope.showRecalls = function() {
        	$scope.$state.go('home');
        }
        
        
        angular.element(document).ready(function () {
            $scope.isAuthenticated = $cookieStore.get("loggedIn") == "true";
            
            if (!$scope.isAuthenticated) {
            	$scope.$state.go('login');
            } else {
                $scope.getMetrics();	
            }
        });
        
    });