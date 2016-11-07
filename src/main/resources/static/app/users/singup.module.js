/**
 * Created by bboyvlad on 9/7/16.
 */
'use strict';

var singup = angular.module('singup', ['ngRoute', 'jdmenu', 'ngMessages']);

    singup.controller('singupController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'LxNotificationService', 'helperFunc', 'userResource',
        function singupController($rootScope, $scope, $http, $location, myJdMenu, LxNotificationService, helperFunc, userResource) {
            helperFunc.checkauth(true, [ 'ROLE_ADMIN', 'ROLE_SYSADMIN']);
            var self = this;

            $scope.fsingup = {};
            $scope.sendbutton = false;
            $scope.LinearProgress = false;
            $scope.emailExistance = true;
            $scope.icon = '../css/icons/id-card.png';
            $scope.passRegex = "/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/";


            /***************** TO RESET FORMS ********************/
            $scope.master = {
                firstname:"", lastname:"", email:"", pass:"", chkpass:""
            };
            $scope.reset = function() {
                $scope.fsingup = angular.copy($scope.master);
            };
            /***************** TO RESET FORMS ********************/

            /*$scope.emailCheck = function (email) {
                console.log("Check Email "+email);
                var chkemail = {
                    email: email
                };
                console.log("Check Email "+chkemail.toSource());

                userResource.checkEmail(chkemail).$promise.then(
                    function (data) {
                        if (data.message == "validEmail") {
                            $scope.emailExistance = false;
                            return;
                        }
                        if (data.message == "invalidEmail") {
                            $scope.emailExistance = true;
                            return;
                        }
                    },
                    function (data) {
                        console.log(data);
                    }
                );
            };*/

            $scope.userCreate = function userCreate($event, fields) {
                $event.preventDefault();
                var self = this;
                this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
                this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

                var userSave = userResource.singUp({}, fields);
                userSave.$promise.
                    then(
                        function (data) {
                            //console.log("Guardado!!" + data.toSource());
                            if (data.message == "Este email ya se encuentra registrado"){
                                LxNotificationService.error(data.message + '...');
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                //self.helperFuncBar();
                                return;
                            }
                            $scope.reset();

                            //$rootScope.userInfo = data;

                            LxNotificationService.alert('Su cuenta ha sido creada',
                                "Bienvenido "+data.firstname +" "+ data.lastname+",\r\nRevise su correo ("+data.email+") para activar su cuenta...\r\nA continuación sera redirigido al Login",
                                'Ok',
                                function(answer)
                            {
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                $location.path("/loginpage");
                            });

                        }
                );

            };

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
/*

/!*TO COMPARE FIELDS VALUES*!/
var compareTo = function() {
    return {
        require: "ngModel",
        scope: {
            otherModelValue: "=compareTo"
        },
        link: function(scope, element, attributes, ngModel) {

            ngModel.$validators.compareTo = function(modelValue) {
                return modelValue == scope.otherModelValue;
            };

            scope.$watch("otherModelValue", function() {
                ngModel.$validate();
            });
        }
    };
};

singup.directive("compareTo", compareTo);*/
