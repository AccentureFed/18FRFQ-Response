'use strict';

angular.module('jigsawApp')
    .controller('FooterController', function ($scope, $location, $state, FooterInfo) {
        $scope.$state = $state;
        
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
        	$scope.$state.go('metrics');
        }
        
        $scope.getVersion();
    });
