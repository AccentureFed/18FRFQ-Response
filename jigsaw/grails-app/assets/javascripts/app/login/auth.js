'use strict';

angular.module('jigsawApp')
    .config(['$stateProvider', function ($stateProvider, $state, $scope) {
        $stateProvider
            .state('login', {
                url: '/login/',
                views: {
                    'navbar@': {
                        templateUrl: 'app/navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content@': {
                        templateUrl: 'app/login/auth.html',
                        controller: 'AuthController'
                    },
                    'footer@': {
                        templateUrl: 'app/footer/footer.html',
                        controller: 'FooterController'
                    }
                }

            });
    }])
    .directive('showLogin', function() {
	    return {
	        restrict: 'C',
	        link: function(scope, element) {
	            var username = element.find('#username');
	            var password = element.find('#password');
	
	            loginBlock.hide();
	            loginError.hide();
	
	            scope.$on('event:auth-loginRequired', function() {
	                username.val('');
	                password.val('');
	                $state.go('login');
	            });
	            scope.$on('event:auth-loginFailed', function() {
	                username.val('');
	                password.val('');
	                $scope.authError = true;
	            });
	            scope.$on('event:auth-loginConfirmed', function() {
	                $state.go('metrics');
	                username.val('');
	                password.val('');
	            });
	        }
	    }
	});

