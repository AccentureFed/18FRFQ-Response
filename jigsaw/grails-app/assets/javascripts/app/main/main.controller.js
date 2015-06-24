'use strict';

angular.module('jigsawApp')
    .controller('MainController', function ($rootScope, $scope, $state, $timeout, RecallInfo) {
    	var currentDate = new Date();
    	var startDate = new Date();
    	$scope.startDateValue = startDate.toLocaleDateString("en-US");

    	$scope.endDateValue = currentDate.toLocaleDateString("en-US");
    	$scope.selectedStates = null;
    	$scope.recalls = null;
    	$scope.currentRecall = "";
    	$scope.mapActive = true;
    	
    	$scope.showRecallDetail = function(recallInfo){
    		$scope.mapActive = false;
    		$scope.currentRecall = recallInfo;
    	}
    	
    	$scope.showMap = function() {
    		$scope.mapActive = true;
    	}
    	
    	$scope.getBriefRecallsByState = function(stateObj, start){
    		RecallInfo.getRecallDetail(stateObj, 0, 10, function(data){
    			$scope.recalls = data.results;
    		}, function(){
    			
    		});
    	}
    	
    	$scope.setDateRange = function(rangeType){
    		var tempDate = new Date();
    		switch(rangeType){
    		case 'week':
    			tempDate.setDate(currentDate.getDate() - 7);
    			break;
    		case 'month':
    			tempDate.setMonth(currentDate.getMonth() - 1);
    			break;
    		case 'quarter':
    			tempDate.setMonth(currentDate.getMonth() - 3);
    			break;
    		case 'year':
    			tempDate.setFullYear(currentDate.getFullYear() - 1);
    			break;
    		default:
    			tempDate.setMonth(currentDate.getMonth() - 3);
    			break;
    		}
    		$scope.startDateValue = tempDate.toLocaleDateString("en-US");
    	}
    	
    	
    	$scope.getStateSeverity = function(stateObj){
    		var severity = "";
    		RecallInfo.getStateCount(stateObj,
    			function(data){
    				var stateAbr = data.stateCode;
    				data.results.forEach(function(element, index, array)
    				{
    					stateAbr;
    					if (element.severity == "high")
    					{
    						$('#map').usmap({
    				      	    'stateSpecificStyles': {
    				      	    	stateAbr : {fill: 'red'}
    				        	    }
    						});
    					}
    					else if (element.severity == "medium" && severity != "high")
    					{
    						$('#map').usmap({
    				      	    'stateSpecificStyles': {
    				      	    	stateAbr : {fill: 'orange'}
    				        	    }
    						});
    					}
    					else if (element.severity == "low" && severity == null)
    					{
    						$('#map').usmap({
    				      	    'stateSpecificStyles': {
    				      	    	stateAbr : {fill: 'yellow'}
    				        	    }
    						});
    					}
    				});
    			},
    			function(){
    				angular.noop;
    			});
    	};
    	
        angular.element(document).ready(function () {
      	  $('#map').usmap({
        	'stateStyles': {fill: 'white', showLabels: true},
      	    'stateSpecificStyles': {
      	      'AK' : {fill: '#f00'},
      	      'VA' : {fill: 'orange'}
      	    },
      	    'stateSpecificHoverStyles': {
      	      'HI' : {fill: '#ff0'}
      	    },
      	    
      	    'click' : function(event, data) {
      	    	$scope.getBriefRecallsByState(data.name);
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

        $scope.getBriefRecallsByState('VA', $scope.startDateValue);
        
        $scope.errors = {};

    });
