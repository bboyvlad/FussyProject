/**
 * Created by bboyvlad on 9/7/16.
 */
'use strict';

var login = angular.module('login', ['ngRoute', 'ngResource', 'ngMessages']);


    login.controller('LoginController', ['$rootScope','$scope', '$http', '$location', 'LxNotificationService', '$interval', 'myJdMenu', 'helperFunc', 'userResource', 'shopcartResource', '$translate', '$filter',
        function LoginController($rootScope, $scope, $http, $location, LxNotificationService, $interval, myJdMenu, helperFunc, userResource, shopcartResource, $translate, $filter) {
            //$translate.use('en');
            $scope.cssClass = 'loginpage';
            var self = this;

            $scope.flogin = {};
            $scope.sendbutton = false;
            $scope.LinearProgress = false;
            $scope.loginFormImg = '../css/img/loginimage.png';

            $scope.datePickerFromRoot = $rootScope.rootdatePicker;

            /***************** TO RESET FORMS ********************/
            $scope.master = {
                email:"", pass:""
            };
            $scope.reset = function() {
                $scope.flogin = angular.copy($scope.master);
            };
            /***************** TO RESET FORMS ********************/
            //$scope.reset();


            //helperFunc.authenticate();
            /*$scope.credentials = {};
            $scope.login = function() {
                console.log(self.credentials.toSource());
                helperFunc.authenticate(self.credentials, function() {
                    if ($rootScope.authenticated) {
                        $location.path("/");
                        self.error = false;
                    } else {
                        $location.path("/loginpage");
                        self.error = true;
                    }
                });
            };*/

            $scope.userLogin = function userLogin($event, fields) {
                $event.preventDefault();

                var self = this;
                this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
                this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

                /*helperFunc.authenticate(fields, function() {
                    if ($rootScope.authenticated) {
                        $location.path("/dashboard");
                        self.error = false;
                    } else {
                        $location.path("/users/log-in");
                        self.error = true;
                    }
                });*/

                var userLogIn = userResource.logIn({}, fields);
                userLogIn.$promise.
                    then(
                        function (data) {
                            //self.helperFuncBar();
                            if (data.message == "Usuario no registrado"){
                                $scope.sms1 = $filter('translate')('login.module.sms1');
                                LxNotificationService.error($scope.sms1);
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                //self.helperFuncBar();
                                return;
                            }
                            var loggedUser=userResource.loggedUser();
                            loggedUser.$promise.then(function(data) {
                                //console.log("in data: "+data.toSource());
                                if( !angular.isDefined(data.name) || data.name==""){
                                    $scope.sms1 = $filter('translate')('login.module.sms1');
                                    LxNotificationService.error($scope.sms1);
                                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                }else {
                                    $rootScope.cart = shopcartResource.getCartUser();
                                    $rootScope.user = data;
                                    var detailUser=userResource.detailUser();
                                    detailUser.$promise.then( function (data) {
                                        $rootScope.userDetail = data;
                                        /*console.log("$rootScope.userDetail from loginController userDetails: "+$rootScope.userDetail.toSource());*/
                                        $location.path("/dashboard");
                                    });

                                }

                            }, function() {
                                //console.log(response.toSource());
                            });

                            /*console.log(data.toSource());
                            $rootScope.user = data;
                            $location.path("/dashboard");*/

                            //self.helperFuncBar();
                        },function (data) {
                            console.log("Error!!:"/*+data.toSource()*/);
                        }
                    );
            }
        }]);