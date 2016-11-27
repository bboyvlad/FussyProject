/**
 * Created by bboyvlad on 9/7/16.
 */
'use strict';

var mycaptain = angular.module('MyCaptain', ['ngRoute', 'jdmenu', 'ngMessages']);

mycaptain.controller('MyCaptainController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'LxNotificationService', 'helperFunc', 'mycaptainResource', 'LxDialogService', 'LxDatePickerService', '$translate', '$filter',
    function MyCaptainController($rootScope, $scope, $http, $location, myJdMenu, LxNotificationService, helperFunc, mycaptainResource, LxDialogService, LxDatePickerService, $translate, $filter) {
        $scope.cssClass = 'captain';
        $scope.icon = '../css/icons/pilot-hat.png';
        var self = this;

        $scope.fmycaptain = {datePicker: { dateFormated: null, locale: 'es', format: 'YYYY-MM-DD' }};
        $scope.sendbutton = false;
        $scope.LinearProgress = false;
        $scope.listCaptain = mycaptainResource.query();
        $scope.search = { mysearch:'' };

        /***************** TO RESET FORMS ********************/
        $scope.master = {
            id: "", name: "", license: "", dateofbirth: "", address: "", city: "", country: "", phone: "", email: "", active: "", datePicker: { dateFormated: new Date(), locale: 'es', format: 'YYYY-MM-DD' }
        };
        $scope.reset = function() {
            $scope.fmycaptain = angular.copy($scope.master);
            $scope.feditcaptain = angular.copy($scope.master);
        };
        /***************** TO RESET FORMS ********************/

        /****************** DATEPICKER *********************/
        $scope.datePickerId = 'dateofbirth';

        $scope.datePickerCallback = function datePickerCallback(_newdate, action="add")
        {
            if(action=="add"){
                $scope.fmycaptain.datePicker.dateFormated = moment(_newdate).locale($scope.fmycaptain.datePicker.locale).format($scope.fmycaptain.datePicker.format);
            }else if(action=="update"){
                $scope.feditcaptain.datePicker.dateFormated = moment(_newdate).locale($scope.feditcaptain.datePicker.locale).format($scope.feditcaptain.datePicker.format);
            }
            LxDatePickerService.close($scope.datePickerId);
        };
        /****************** DATEPICKER *********************/

        /********** saveMyCaptain ********/

        $scope.myCaptainCreate = function myCaptainCreate($event, fields) {
            $event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

            var myCaptainSave = mycaptainResource.save({}, fields);
            myCaptainSave.$promise.
            then(
                function (data) {
                    console.log("Saved!!" + data.toSource());
                    $scope.listCaptain = mycaptainResource.query();
                    $scope.reset();

                    //$rootScope.userInfo = data;
                    $scope.sms1 = $filter('translate')('captain.module.sms1');
                    LxNotificationService.success($scope.sms1);
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);

                }
            );

        };

        /********** saveMyCaptain ********/


        /********** editCaptain ********/
        //$scope.listCaptain = userResource.myaircraft({principal_id: $rootScope.user.id});

        $scope.dialogCaptain = "dialogCaptain";
        $scope.feditcaptain = {};

        /***************** TO RESET EDIT FORMS ********************/


        $scope.editReset = function(id) {
            $scope.editMaster = {
                id: id, name: "", license: "", dateofbirth: "", address: "", city: "", country: "", phone: "", email: "", active: "", datePicker: { dateFormated: new Date(), locale: 'es', format: 'YYYY-MM-DD' }
            };
            $scope.feditcaptain = angular.copy($scope.editMaster);
        };
        /***************** TO RESET FORMS ********************/
        //$scope.reset();

        $scope.openDialogCaptain = function openDialogCaptain(ef_cap)
        {
            LxDialogService.open(this.dialogCaptain);
            console.log(ef_cap.id );
            /***************** TO RECALL DATA ON EDIT FORMS ********************/
            $scope.editmaster = {
                id: ef_cap.id, name: ef_cap.name, license: ef_cap.license, dateofbirth: ef_cap.dateofbirth, address: ef_cap.address, city: ef_cap.city, country: ef_cap.country, phone: ef_cap.phone, email: ef_cap.email, active: ef_cap.active ? 'true' : 'false', datePicker: { dateFormated: helperFunc.dateFromDB(ef_cap.dateofbirth, "YYYY-MM-DD"), locale: 'es', format: 'YYYY-MM-DD' }
            };
            $scope.feditcaptain = angular.copy($scope.editmaster);
            /***************** TO RECALL DATA ON EDIT FORMS ********************/

        }

        $scope.$on('lx-dialog__open-start', function(_event, _dialogId)
        {
            //var self = this;
            if ($scope.dialogCaptain === _dialogId)
            {

            }
        });

        $scope.updateCaptain = function updateCaptain($event, fields) {
            $event.preventDefault();
            var self = this;

            console.log(fields.id+ ' / ' + fields);
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

            mycaptainResource.update({}, fields).$promise.
            then(
                function (data) {
                    console.log("Updated!!" + data);
                    $scope.sms2 = $filter('translate')('captain.module.sms2');
                    LxNotificationService.success($scope.sms2);
                    $scope.listCaptain = mycaptainResource.query();
                    $scope.reset();
                    //$location.path("/dashboard");
                    LxDialogService.close(self.dialogCaptain);
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                },function (data) {
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    console.log("Error!!" + data);
                }
            );
        }

        /********** editCaptain ********/


        /********** deleteCaptain ********/

        $scope.deleteCaptain = function deleteCaptain(data) {
            //$event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
            console.log(data.toSource());
            $scope.sms3 = $filter('translate')('captain.module.sms3');
            $scope.sms4 = $filter('translate')('captain.module.sms4');
            LxNotificationService.confirm($scope.sms3, $scope.sms4,
                {
                    cancel: 'Cancel',
                    ok: 'Delete'
                }, function(answer)
                {
                    if (answer)
                    {
                        mycaptainResource.deleteMycaptain({captainId: data.id}).$promise.
                        then(
                            function (data) {
                                console.log("Erased!!" + data);
                                $scope.sms5 = $filter('translate')('captain.module.sms5');
                                LxNotificationService.success($scope.sms5);
                                $scope.listCaptain = mycaptainResource.query();
                                //$scope.faircrafts = [];
                                //$location.path("/dashboard");
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                            },function (data) {
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                console.log("Error!!" + data.toSource());
                            }
                        );
                    }
                    else
                    {
                        $scope.sms6 = $filter('translate')('captain.module.sms6');
                        LxNotificationService.error($scope.sms6);
                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    }
                });

        }
        /********** deleteCaptain ********/

    }]);

/*TO COMPARE FIELDS VALUES*/
/*
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
