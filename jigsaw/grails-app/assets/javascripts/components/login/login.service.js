'use strict';

angular.module('jigsawApp')
    .factory('LoginService', function ($rootScope, $http, authService, $cookieStore, $state) {

        return {
    		logIn: function(username, password, callback, error) {
                callback = callback || angular.noop;
                error = error || angular.noop;
                
                $http.post('api/login', { username: username.toLowerCase(), password: password }, getAuthModuleHttpConfig()).
                success(function(data) {
                    $cookieStore.put("loggedIn", "true");
                    $cookieStore.put("currentUser", data.username);
                    setLocalBrowserToken(data.access_token);
                    authService.loginConfirmed({}, function(config) {
                        var localBrowserToken = getLocalBrowserToken();
                        var headerToken = config.headers["X-Auth-Token"];
                        if(!headerToken || (headerToken != localBrowserToken)) {
                            config.headers["X-Auth-Token"] = getLocalBrowserToken();
                        }
                    });
                    $state.go('metrics');
                }).
                error(function(data) {
                    $rootScope.$broadcast('event:auth-loginFailed', data);
                    error(data);
                });
            },
            logOut: function() {
                $http.post('api/logout', {}, getHttpAuthConfig()).
                    success(function() {
                        $cookieStore.put("loggedIn", null);
                        $cookieStore.put("currentUser", null);
                        sessionStorage.clear();
                        $http.post('/jigsaw/logout/', {value:"logout"}, getHttpAuthConfig())
                        .success(function(){
                        	angular.noop;
                        })
                        .error(function(){
                        	angular.noop;
                        });
                        $state.go('login');
                    }).
                    error(function(data) {
                    	$state.go('home');
                    });
            }
        }
    });