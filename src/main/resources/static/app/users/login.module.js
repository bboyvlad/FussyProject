/**
 * Created by bboyvlad on 9/7/16.
 */
'use strict';

var login = angular.module('login', ['ngRoute', 'ngResource', 'ngMessages']);


    login.controller('LoginController', ['$rootScope','$scope', '$http', '$location', 'LxNotificationService', '$interval', 'myJdMenu', 'helperFunc', 'userResource', 'shopcartResource',
        function LoginController($rootScope, $scope, $http, $location, LxNotificationService, $interval, myJdMenu, helperFunc, userResource, shopcartResource) {
            $scope.cssClass = 'loginpage';
            var self = this;

            $scope.flogin = {};
            $scope.sendbutton = false;
            $scope.LinearProgress = false;
            $scope.loginFormImg = '../css/img/loginimage.png';

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
                                LxNotificationService.error(data.message + ', Verifique su Datos!!!');
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                //self.helperFuncBar();
                                return;
                            }
                            var loggedUser=userResource.loggedUser();
                            loggedUser.$promise.then(function(data) {
                                //console.log("in data: "+data.toSource());
                                if( !angular.isDefined(data.name) || data.name==""){
                                    LxNotificationService.error(' Verifique sus Datos!!!');
                                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                }else {
                                    $rootScope.cart = shopcartResource.getCartUser();
                                    $rootScope.user = data;
                                    $rootScope.userDetail = userResource.detailUser();
                                    $location.path("/dashboard");
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

            $scope.userOpts = {
                "usermenu":[
                    {
                        "link":"/users/sing-up",
                        "text":"Registrate"
                    },
                    {
                        "link":"/loginpage",
                        "text":"Log In"
                    }
                ],
                "useradmin":false,
                "jdcard":false,
                "giftcard": false,
                "payments":false,
                "defgen":false,
                "aircraft":false,
                "captain":false,
                "mainmenu":{
                    "main":[
                        {
                            "link":"/",
                            "text":"Home"
                        },
                        {
                            "link":"/",
                            "text":"Servicios"
                        },
                        {
                            "link":"/",
                            "text":"Productos"
                        },
                        {
                            "link":"/",
                            "text":"Promociones"
                        },
                        {
                            "link":"/",
                            "text":"Contacto"
                        }
                    ]
                }
            };

            $scope.sharedMenu = myJdMenu;

            $scope.updateMenu = function () {
                //alert(this.Opts.item1);
                myJdMenu.userSection(this.userOpts.usermenu);
                myJdMenu.userAdminSection(this.userOpts.useradmin);
                myJdMenu.mainSection(this.userOpts.mainmenu);
                myJdMenu.jdcardSection(this.userOpts.jdcard);
                myJdMenu.giftcardSection(this.userOpts.giftcard);
                myJdMenu.paymentsSection(this.userOpts.payments);
                myJdMenu.defgenSection(this.userOpts.defgen);
                myJdMenu.aircraftSection(this.userOpts.aircraft);
                myJdMenu.captainSection(this.userOpts.captain);
            };

        }]);