'use strict';

angular.module('jigsawApp')
    .controller('NavbarController', function ($scope, $location, $state, NavInfo) {
        $scope.$state = $state;
        $scope.viewUserSettings = $state.is('home');
        $scope.viewSettings = $state.is('metrics');
        $scope.homePageAlert = "";

        $scope.severity = ['low', 'medium', 'high'];

        $scope.severitySelection = ['high'];

        $scope.toggleSeveritySelection = function toggleSeveritySelection(severityName) {
          var idx = $scope.severitySelection.indexOf(severityName);
          // is currently selected
          if (idx > -1) {
            $scope.severitySelection.splice(idx, 1);
          }
          // is newly selected
          else {
            $scope.severitySelection.push(severityName);
          }
          alert($scope.severitySelection);
        };
        
        $('#stateSelect').multiselect({
            maxHeight: 200,
            checkboxName: 'multiselect[]',
            onChange: function(option, checked, select) {
                $scope.toggleStateSelection(option, checked, select);
            },
            nonSelectedText: 'Select State(s)',
            buttonWidth: '200px'
        });
        
        $scope.loadAppSettings = function(){
        	NavInfo.getAppSettings(function(data, status){
        		if (data.appAlert) {
        			$scope.homePageAlert = data.appAlert;
        		} else {
        			$scope.homePageAlert = "";
        		}
        		$scope.tempAppAlert = $scope.homePageAlert;
        	}, function(){
        		$scope.homePageAlert = "";
        	});
        }
        
        $scope.updateAppSettings = function(){
        	NavInfo.updateAppSettings($scope.tempAppAlert, function(status){
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
