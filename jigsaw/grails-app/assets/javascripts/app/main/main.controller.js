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
        $scope.statesData = statesData;
        $scope.abbreviationsMap = [
            {
                "name": "Alabama",
                "abbreviation": "AL"
            },
            {
                "name": "Alaska",
                "abbreviation": "AK"
            },
            {
                "name": "American Samoa",
                "abbreviation": "AS"
            },
            {
                "name": "Arizona",
                "abbreviation": "AZ"
            },
            {
                "name": "Arkansas",
                "abbreviation": "AR"
            },
            {
                "name": "California",
                "abbreviation": "CA"
            },
            {
                "name": "Colorado",
                "abbreviation": "CO"
            },
            {
                "name": "Connecticut",
                "abbreviation": "CT"
            },
            {
                "name": "Delaware",
                "abbreviation": "DE"
            },
            {
                "name": "District Of Columbia",
                "abbreviation": "DC"
            },
            {
                "name": "Federated States Of Micronesia",
                "abbreviation": "FM"
            },
            {
                "name": "Florida",
                "abbreviation": "FL"
            },
            {
                "name": "Georgia",
                "abbreviation": "GA"
            },
            {
                "name": "Guam",
                "abbreviation": "GU"
            },
            {
                "name": "Hawaii",
                "abbreviation": "HI"
            },
            {
                "name": "Idaho",
                "abbreviation": "ID"
            },
            {
                "name": "Illinois",
                "abbreviation": "IL"
            },
            {
                "name": "Indiana",
                "abbreviation": "IN"
            },
            {
                "name": "Iowa",
                "abbreviation": "IA"
            },
            {
                "name": "Kansas",
                "abbreviation": "KS"
            },
            {
                "name": "Kentucky",
                "abbreviation": "KY"
            },
            {
                "name": "Louisiana",
                "abbreviation": "LA"
            },
            {
                "name": "Maine",
                "abbreviation": "ME"
            },
            {
                "name": "Marshall Islands",
                "abbreviation": "MH"
            },
            {
                "name": "Maryland",
                "abbreviation": "MD"
            },
            {
                "name": "Massachusetts",
                "abbreviation": "MA"
            },
            {
                "name": "Michigan",
                "abbreviation": "MI"
            },
            {
                "name": "Minnesota",
                "abbreviation": "MN"
            },
            {
                "name": "Mississippi",
                "abbreviation": "MS"
            },
            {
                "name": "Missouri",
                "abbreviation": "MO"
            },
            {
                "name": "Montana",
                "abbreviation": "MT"
            },
            {
                "name": "Nebraska",
                "abbreviation": "NE"
            },
            {
                "name": "Nevada",
                "abbreviation": "NV"
            },
            {
                "name": "New Hampshire",
                "abbreviation": "NH"
            },
            {
                "name": "New Jersey",
                "abbreviation": "NJ"
            },
            {
                "name": "New Mexico",
                "abbreviation": "NM"
            },
            {
                "name": "New York",
                "abbreviation": "NY"
            },
            {
                "name": "North Carolina",
                "abbreviation": "NC"
            },
            {
                "name": "North Dakota",
                "abbreviation": "ND"
            },
            {
                "name": "Northern Mariana Islands",
                "abbreviation": "MP"
            },
            {
                "name": "Ohio",
                "abbreviation": "OH"
            },
            {
                "name": "Oklahoma",
                "abbreviation": "OK"
            },
            {
                "name": "Oregon",
                "abbreviation": "OR"
            },
            {
                "name": "Palau",
                "abbreviation": "PW"
            },
            {
                "name": "Pennsylvania",
                "abbreviation": "PA"
            },
            {
                "name": "Puerto Rico",
                "abbreviation": "PR"
            },
            {
                "name": "Rhode Island",
                "abbreviation": "RI"
            },
            {
                "name": "South Carolina",
                "abbreviation": "SC"
            },
            {
                "name": "South Dakota",
                "abbreviation": "SD"
            },
            {
                "name": "Tennessee",
                "abbreviation": "TN"
            },
            {
                "name": "Texas",
                "abbreviation": "TX"
            },
            {
                "name": "Utah",
                "abbreviation": "UT"
            },
            {
                "name": "Vermont",
                "abbreviation": "VT"
            },
            {
                "name": "Virgin Islands",
                "abbreviation": "VI"
            },
            {
                "name": "Virginia",
                "abbreviation": "VA"
            },
            {
                "name": "Washington",
                "abbreviation": "WA"
            },
            {
                "name": "West Virginia",
                "abbreviation": "WV"
            },
            {
                "name": "Wisconsin",
                "abbreviation": "WI"
            },
            {
                "name": "Wyoming",
                "abbreviation": "WY"
            }
        ];
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
    			    highlightBorderColor: '#EAA9A8',
    			    highlightBorderWidth: 2
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

    	   	//gets the latlong position
        $scope.getGeoLocation = function(){
            $scope.userLocation = "";
            if (navigator.geolocation) {
                    $scope.userLocation = navigator.geolocation.getCurrentPosition($scope.processPosition);
                        }
            	}
		$scope.processPosition = function(position){
			var lat = position.coords.latitude;
			var long = position.coords.longitude;
			var parser = new ol.format.GeoJSON();
            var features = parser.readFeatures($scope.statesData);
            var point = new ol.geom.Point([long, lat]);
            var geom;
            var state = ""
            for( var i = 0; i< features.length; i++ ){
            	  geom = features[i].getGeometry();
                  if (point.intersectsExtent(geom.getExtent())){
					state = features[i].getProperties()["NAME"];
                }
            }
            if (state){
            	$scope.setUserState(state);
            }
		}
		$scope.setUserState = function(state){
			for (abbrev in $scope.abbreviationsMap){

				if (state == $scope.abbreviationsMap[abbrev]["name"]){

					if ($scope.selectedState != null && typeof $scope.selectedState != 'undefined') {
                        $scope.mapObject.data[$scope.selectedState]['fillKey'] = "defaultFill";
                    }
                    if ($scope.oldState){
                        $scope.mapObject.data[$scope.oldState]['fillKey'] = "defaultFill";
                    }
					$scope.selectedState = $scope.abbreviationsMap[abbrev]["abbreviation"];
                    $scope.oldState = $scope.selectedState;
					$('#state_select').selectpicker('val', $scope.selectedState);
					$scope.getBriefRecallsByState();
                   	$scope.getStateSeverity($scope.selectedState);

				}
			}
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
    		if ($scope.selectedState != null && typeof $scope.selectedState != 'undefined') {
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
                            if (data.results.length > 0)
                            {
                                data.results.forEach(function(element, index, array)
                                {
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
                             //if no results for that part, set it to no recalls
                        else    {
                            		$scope.mapObject.data[$scope.selectedState]['fillKey'] = "NO_RECALLS";

                            	}
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

        	$(".btn-group > .btn").click(function(){
        	    $(this).addClass("active").parent().siblings().children().removeClass("active");
        	});
            
            if ($scope.startDateValue == $scope.endDateValue) {
            	$scope.setDateRange('month');
            }
    		$scope.getGeoLocation();
        });

    });