'use strict';

angular.module('jigsawApp')
    .controller('MainController', function ($rootScope, $scope, $state, $timeout, RecallInfo) {
    	var perRequest = 10;
    	var currentDate = new Date();
    	var startDate = new Date();
    	$scope.startDateValue = startDate.toJSON().substring(0, 10);
    	$scope.endDateValue = currentDate.toJSON().substring(0, 10);
        $scope.errors = {};
    	$scope.oldState = null;
    	$scope.selectedState = null;
    	$scope.userSavedStates = null;
    	$scope.recalls = null;
    	$scope.currentRecall = "";
    	$scope.mapActive = true;
    	$scope.numPages = 0;
    	$scope.page = 1;
    	$scope.totalRecalls = 0;
    	$scope.upcCode = null;
    	$scope.states = ['Alaska',
		   'Arizona',
		   'Arkansas',
		   'California',
		   'Colorado',
		   'Connecticut',
		   'Delaware',
		   'Washington D.C.',
		   'Florida',
		   'Georgia',
		   'Hawaii',
		   'Idaho',
		   'Illinois',
		   'Indiana',
		   'Iowa',
		   'Kansas',
		   'Kentucky',
		   'Louisiana',
		   'Maine',
		   'Maryland',
		   'Massachusetts',
		   'Michigan',
		   'Minnesota',
		   'Mississippi',
		   'Missouri',
		   'Montana',
		   'Nebraska',
		   'Nevada',
		   'New Hampshire',
		   'New Jersey',
		   'New Mexico',
		   'New York',
		   'North Carolina',
		   'North Dakota',
		   'Ohio',
		   'Oklahoma',
		   'Oregon',
		   'Pennsylvania',
		   'Rhode Island',
		   'South Carolina',
		   'South Dakota',
		   'Tennessee',
		   'Texas',
		   'Utah',
		   'Vermont',
		   'Virginia',
		   'Washington',
		   'West Virginia',
		   'Wisconsin',
		   'Wyoming'];
    	
    	
    	$scope.mapObject = {
    			  scope: 'usa',
    			  options: {
    			    width: 700,
    			    labels: true
    			  },
    			  geographyConfig: {
    			    highlighBorderColor: '#EAA9A8',
    			    highlighBorderWidth: 2
    			  },
    			  fills: {
    			    'HIGH': '#FF0000',
    			    'MEDIUM': '#FF6600',
    			    'LOW': '#FFFF00',
    			    'NO_RECALLS': '#80CCFF',
    			    'defaultFill': '#DDDDDD'
    			  },
    			  done: function(datamap) {
    				  datamap.svg.selectAll('.datamaps-subunit').on('click', function(geography) {
    					if ($scope.selectedState != null && typeof $scope.selectedState != 'undefined') {
    						$scope.mapObject.data[$scope.selectedState]['fillKey'] = "defaultFill";
    					}
		                $scope.selectedState = geography.id;
		                $scope.oldState = geography.id;
		                $('#state_select').selectpicker('val', $scope.selectedState);
		                $scope.getBriefRecallsByState();
		                $scope.getStateSeverity($scope.selectedState);
		            })},
		            data: {
		            	'AZ': {'fillKey': "defaultFill"},
			            'AL': {'fillKey': "defaultFill"},
			            'AK': {'fillKey': "defaultFill"},
			            'AZ': {'fillKey': "defaultFill"},
			            'AR': {'fillKey': "defaultFill"},
			            'CA': {'fillKey': "defaultFill"},
			            'CO': {'fillKey': "defaultFill"},
			            'CT': {'fillKey': "defaultFill"},
			            'DC': {'fillKey': "defaultFill"},
			            'DE': {'fillKey': "defaultFill"},
			            'FL': {'fillKey': "defaultFill"},
			            'GA': {'fillKey': "defaultFill"},
			            'HI': {'fillKey': "defaultFill"},
			            'ID': {'fillKey': "defaultFill"},
			            'IL': {'fillKey': "defaultFill"},
			            'IN': {'fillKey': "defaultFill"},
			            'IA': {'fillKey': "defaultFill"},
			            'KS': {'fillKey': "defaultFill"},
			            'KY': {'fillKey': "defaultFill"},
			            'LA': {'fillKey': "defaultFill"},
			            'ME': {'fillKey': "defaultFill"},
			            'MD': {'fillKey': "defaultFill"},
			            'MA': {'fillKey': "defaultFill"},
			            'MI': {'fillKey': "defaultFill"},
			            'MN': {'fillKey': "defaultFill"},
			            'MS': {'fillKey': "defaultFill"},
			            'MO': {'fillKey': "defaultFill"},
			            'MT': {'fillKey': "defaultFill"},
			            'NE': {'fillKey': "defaultFill"},
			            'NV': {'fillKey': "defaultFill"},
			            'NH': {'fillKey': "defaultFill"},
			            'NJ': {'fillKey': "defaultFill"},
			            'NM': {'fillKey': "defaultFill"},
			            'NY': {'fillKey': "defaultFill"},
			            'NC': {'fillKey': "defaultFill"},
			            'ND': {'fillKey': "defaultFill"},
			            'OH': {'fillKey': "defaultFill"},
			            'OK': {'fillKey': "defaultFill"},
			            'OR': {'fillKey': "defaultFill"},
			            'PA': {'fillKey': "defaultFill"},
			            'RI': {'fillKey': "defaultFill"},
			            'SC': {'fillKey': "defaultFill"},
			            'SD': {'fillKey': "defaultFill"},
			            'TN': {'fillKey': "defaultFill"},
			            'TX': {'fillKey': "defaultFill"},
			            'UT': {'fillKey': "defaultFill"},
			            'VT': {'fillKey': "defaultFill"},
			            'VA': {'fillKey': "defaultFill"},
			            'WA': {'fillKey': "defaultFill"},
			            'WV': {'fillKey': "defaultFill"},
			            'WI': {'fillKey': "defaultFill"},
			            'WY': {'fillKey': "defaultFill"}
		            }
    			}

    	
    	$scope.showRecallDetail = function(recallInfo){
    		$scope.mapActive = false;
    		$scope.currentRecall = recallInfo;
    	}
    	
    	$scope.stateChanged = function(){
			if ($scope.oldState != null && typeof $scope.oldState  != 'undefined') {
				$scope.mapObject.data[$scope.oldState]['fillKey'] = "defaultFill";
			}
			if (typeof $scope.selectedState != 'undefined' && $scope.selectedState != null) {
	            $scope.oldState = $scope.selectedState;
	            $scope.getBriefRecallsByState();
	            $scope.getStateSeverity($scope.selectedState);
			}
			else {
				$scope.oldState = null;
				$scope.getAllRecalls();
			}
				
    	}
    	
    	$scope.showMap = function() {
    		$scope.mapActive = true;
    	}
    	
    	$scope.loadPage = function(pg2Load){
    		RecallInfo.getRecallDetail($scope.selectedState, $scope.startDateValue.replace(/-/g,''), $scope.endDateValue.replace(/-/g,''), $scope.upcCode, pg2Load - 1, perRequest, function(data){
    			if (data != null && data.numResults != null && data.numResults > 0) {
        			$scope.recalls = data.results;
        			$scope.page = pg2Load;
    			}
    			else
    			{
    				$scope.numPages = 0;
    				$scope.page = 0;
    				$scope.recalls = null;
    			}
    		}, function(){
    			
    		});
    	}
    	
    	$scope.getBriefRecallsByState = function(){
    		if ($scope.selectStates != null && typeof $scope.selectedStates != 'undefined') {
	    		RecallInfo.getRecallDetail($scope.selectedState, $scope.startDateValue.replace(/-/g,''), $scope.endDateValue.replace(/-/g,''), $scope.upcCode, 0, perRequest, function(data){
	    			if (data != null && data.numResults != null && data.numResults > 0) {
	    				$scope.totalRecalls = data.numResults;
	        			$scope.recalls = data.results;
	        			if (perRequest > 0)
	        			{
	        				$scope.numPages = Math.ceil(data.numResults / perRequest)
	        				$scope.page = 1;
	        			}
	        			else
	        			{
	        				$scope.numPages = 0;
	        				$scope.page = 0;
	        			}
	    			}
	    			else
	    			{
	    				$scope.totalRecalls = 0;
	    				$scope.numPages = 0;
	    				$scope.page = 0;
	    				$scope.recalls = null;
	    			}
	    		}, function(){
	    			$scope.totalRecalls = 0;
					$scope.numPages = 0;
					$scope.page = 0;
					$scope.recalls = null;
	    		});
    		}
    		else
    		{
    			$scope.getAllRecalls();
    		}
    	}
    	
    	$scope.getAllRecalls = function(){
    		RecallInfo.getAllRecallDetail(function(data, headers){
    			if (data != null && data.meta != null && data.meta.results != null && data.meta.results.total > 0) {
    				$scope.totalRecalls = data.meta.results.total;
        			$scope.recalls = data.results;
        			if (perRequest > 0)
        			{
        				$scope.numPages = Math.ceil(data.numResults / perRequest)
        				$scope.page = 1;
        			}
        			else
        			{
        				$scope.numPages = 0;
        				$scope.page = 0;
        			}
    			}
    			else
    			{
    				$scope.totalRecalls = 0;
    				$scope.numPages = 0;
    				$scope.page = 0;
    				$scope.recalls = null;
    			}
    		}, function(){
    			$scope.totalRecalls = 0;
				$scope.numPages = 0;
				$scope.page = 0;
				$scope.recalls = null;
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
    		case 'forever':
    			tempDate.setFullYear(currentDate.getFullYear() - 5);
    			break;
    		default:
    			tempDate.setMonth(currentDate.getMonth() - 1);
    			break;
    		}
    		$scope.startDateValue = tempDate.toJSON().substring(0, 10);
    		
    		if ($scope.selectedState != null)
    		{
    			$scope.getBriefRecallsByState();
    			$scope.getStateSeverity($scope.selectedState);
    		}
    		else
    		{
    			$scope.getAllRecalls();
    		}
    	}
    	
    	
    	$scope.getStateSeverity = function(stateObj){
    		if (typeof stateObj != 'undefined' && stateObj != null) {
    		var severity = "";
    		RecallInfo.getStateCount(stateObj, $scope.startDateValue.replace(/-/g,''), $scope.endDateValue.replace(/-/g,''),
    			function(data, status){
    				if (!data.error && status != null && status == 200) {
    					if (typeof data.results != 'undefined') {
		    				var stateAbr = data.stateCode;
		    				data.results.forEach(function(element, index, array)
		    				{
		    					stateAbr;
		    					if (element.severity == "high" && severity != "high")
		    					{
		    						$scope.mapObject.data[stateAbr]['fillKey'] = "HIGH";
		    						severity = element.severity;
		    						return;
		    					}
		    					else if (element.severity == "medium" && severity != "high")
		    					{
		    						$scope.mapObject.data[stateAbr]['fillKey'] = "MEDIUM";
		    						severity = element.severity;
		    					}
		    					else if (element.severity == "low" && severity == "")
		    					{
		    						$scope.mapObject.data[stateAbr]['fillKey'] = "LOW";
		    						severity = element.severity;
		    					}
		    				});
    					}
    				}
    				else
    				{	
    					if (typeof $scope.selectedState != 'undefined' && $scope.selectedState != null)
    					{
    						$scope.mapObject.data[stateObj]['fillKey'] = "NO_RECALLS";
    					}
    					else
    					{
    						$scope.mapObject.data[stateObj]['fillKey'] = "defaultFill";
    					}
    				}
    			},
    			function(){
					if (typeof $scope.selectedState != 'undefined' && $scope.selectedState != null)
					{
						$scope.mapObject.data[stateObj]['fillKey'] = "NO_RECALLS";
					}
					else
					{
						$scope.mapObject.data[stateObj]['fillKey'] = "defaultFill";
					}
    			});
    		}
    	}
    	
    	$scope.updateHeatMap = function(){
    		if (typeof $scope.states != 'undefined')
    		{
	    		$scope.states.forEach(function(element, index, array){
	    			$scope.getStateSeverity(element);
	    		});
    		}
    	}
        
        $('.selectpicker').selectpicker({
        	style:'btn-default',
        	size: 8
        });
        
        angular.element(document).ready(function () {
            
        	$scope.mapObject.responsive = true;
        	$(window).on('resize', function(){
        		$scope.mapObject.resize();
        	});
        	$(".btn-group > .btn").click(function(){
        	    $(this).addClass("active").parent().siblings().children().removeClass("active");
        	});
            
            if ($scope.startDateValue == $scope.endDateValue) {
            	$scope.setDateRange('month');
            }
        	
        });

    });
