/**
 * Created by bboyvlad on 9/7/16.
 */
'use strict';

var singup = angular.module('singup', ['ngRoute', 'jdmenu', 'ngMessages']);

    singup.controller('singupController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'LxNotificationService', 'helperFunc', 'userResource', '$translate', '$filter',
        function singupController($rootScope, $scope, $http, $location, myJdMenu, LxNotificationService, helperFunc, userResource, $translate, $filter) {
            $scope.cssClass = 'singup';
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
                                $scope.sms1 = $filter('translate')('singup.module.sms1');
                                LxNotificationService.error($scope.sms1);
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                //self.helperFuncBar();
                                return;
                            }
                            $scope.reset();

                            //$rootScope.userInfo = data;
                            $scope.sms2 = $filter('translate')('singup.module.sms2');
                            $scope.sms3 = $filter('translate')('singup.module.sms3',data);
                            LxNotificationService.alert($scope.sms2,
                                $scope.sms3,
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
