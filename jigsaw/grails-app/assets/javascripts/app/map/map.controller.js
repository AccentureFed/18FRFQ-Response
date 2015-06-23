'use strict';

angular.module('jigsawApp')
    .controller('MapController', function ($scope, $location, $state) {
        $scope.$state = $state;
        angular.element(document).ready(function () {
      	  $('#map').usmap({
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
        });
    });