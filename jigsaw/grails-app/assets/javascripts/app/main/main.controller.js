'use strict';

angular.module('jigsawApp')
    .controller('MainController', function ($rootScope, $scope, $state, $timeout) {
    	$scope.startDateValue = null;
    	$scope.endDateValue = null;
    	$scope.selectedStates = null;
    	
    	$scope.updateFilter = function (option, checked, select){
    		
    	}
    	
    	$scope.clearFilters = function (){
        	$scope.startDateValue = null;
        	$scope.endDateValue = null;
        	$scope.selectedStates = null;
    	}
    	
    	$scope.getAllStateAlerts = function (startDate, endDate, stateSelection) {
    		
    	}
    	
    	$scope.getStateAlerts = function(startDate, endDate, stateObj){
    		
    	}
    	
        angular.element(document).ready(function () {
      	  $('#map').usmap({
        	'showLables': true,
      	    'stateSpecificStyles': {
      	      'AK' : {fill: '#f00'}
      	    },
      	    'stateSpecificHoverStyles': {
      	      'HI' : {fill: '#ff0'}
      	    },
      	    
      	    'mouseoverState': {
      	      'HI' : function(event, data) {
      	        //return false;
      	      }
      	    },
      	    'click' : function(event, data) {
      	      $('#alert')
      	        .text('Click '+data.name+' on map 1')
      	        .stop()
      	        .css('backgroundColor', '#ff0')
      	        .animate({backgroundColor: '#ddd'}, 1000);
      	    }
      	  });
          $('#stateSelect').multiselect({
              maxHeight: 200,
              checkboxName: 'multiselect[]',
              onChange: function(option, checked, select) {
                  $scope.updateFilter(option, checked, select);
              },
              nonSelectedText: 'Select State(s)',
              buttonWidth: '200px'
          });
        });
        
        $scope.errors = {};
        
    	$('.date-picker').datepicker();

    });
