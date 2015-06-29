'use strict';

angular.module('jigsawApp')
    .controller('NavbarController', function ($scope, $location, $state, NavInfo) {
        $scope.$state = $state;

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
        
        $scope.toggleStateSelection = function toggleStateSelection(option, checked, select) {
        	alert($scope.selectedStates);
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
        
    });
