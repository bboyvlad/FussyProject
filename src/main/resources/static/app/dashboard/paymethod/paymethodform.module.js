/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var paymethod = angular.module('PayMethod', ['ngRoute', 'ngMessages', 'angularPayments']);

paymethod.controller('PayMethodController', ['$rootScope','$scope', '$http', '$location', 'LxDatePickerService', 'myJdMenu', 'helperFunc', 'LxNotificationService', 'LxDialogService', 'userPaymentResource', 'userResource', 'cardPaymentResource','$translate', '$filter',
    function PayMethodController($rootScope, $scope, $http, $location, $LxDatePickerService, myJdMenu, helperFunc, LxNotificationService, LxDialogService, userPaymentResource, userResource, cardPaymentResource, $translate, $filter) {
        $scope.cssClass = 'paymethod';
        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;
        $scope.search = { mysearch:'' };
        $scope.faddpaym = {};
        $scope.icon = '../css/icons/bank.png';


        /***************** COORDINATE CARD ********************/
        $scope.userDetail = userResource.detailUser().$promise
        .then(
            function (data) {
                if(data.coordinates==null || data.coordinates.id==""){
                    //console.log(data.coordinates.id + " / " + data.coordinates.active);
                    $scope.sms1 = $filter('translate')('paymethod.module.sms1');
                    $scope.sms2 = $filter('translate')('paymethod.module.sms2');
                    LxNotificationService.confirm($scope.sms1, $scope.sms2,
                        {
                            cancel: 'Cancel',
                            ok: 'Create'
                        }, function(answer)
                        {
                            if (answer)
                            {
                                window.open('http://jdoilfield.net:8080/users/setcoordinates', '_blank');
                                $scope.sms3 = $filter('translate')('paymethod.module.sms3');
                                LxNotificationService.success($scope.sms3);
                            }
                            else
                            {
                                LxNotificationService.error('Operation Canceled');
                            }
                        });
                }else if(data.coordinates.active==false){
                    $scope.sms4 = $filter('translate')('paymethod.module.sms4');
                    LxNotificationService.info($scope.sms4);
                }
            }
        );

        var randomString = function(type) {
            if(type==true){
                var text = "";
                var possible = "ABCDE";
                for(var i = 0; i < 1; i++) {
                    text += possible.charAt(Math.floor(Math.random() * possible.length));
                }
                return text;
            }else{

                var text = "";
                var possible = "12345";
                for(var i = 0; i < 1; i++) {
                    text += possible.charAt(Math.floor(Math.random() * possible.length));
                }
                return text;
            }
        }
        /*************  ASKING FOR CORDINATES **************/
        $scope.dialogCoordinates = "dialogCoordinates";
        $scope.openDialogCoordinates = function openDialogCoordinates()
        {
            LxDialogService.open(this.dialogCoordinates);

        }

        $scope.$on('lx-dialog__open-start', function(_event, _dialogId)
        {
            //var self = this;
            if ($scope.dialogCoordinates === _dialogId)
            {
                $scope.coord1 = {
                    key: randomString(true) + randomString(false)
                };
                $scope.coord2 = {
                    key: randomString(true) + randomString(false)
                };

            }
        });

        //console.log($scope.userDetail.toSource());
        /***************** COORDINATE CARD ********************/

        /***************** TO RESET FORMS ********************/
        $scope.master = {
            paytype: "", payacctnum: "", paycardname: "", payvalid: null /*, paystatus: ""*/
        };
        $scope.reset = function() {
            $scope.faddpaym = angular.copy($scope.master);
            $scope.feditpaym = angular.copy($scope.master);
        };
        /***************** TO RESET FORMS ********************/
        //$scope.reset();

        $scope.regex = "[A-Z\\s]+";
        $scope.methodOpts = [
            {
                key: "CARD",
                name: "CARD"
            }
        ];

        $scope.datePicker = {
            input:
            {
                date: new Date(),
                dateFormated: moment().locale('es').format('LL')
            }
        };


        /********** addpayMethod ********/
        $scope.payMethodCreate = function payMethodCreate($event, fields, id) {
            $event.preventDefault();
            var self = this;

            var coordinates = '{"crdOne":"'+$scope.coord1.key+'","valOne":"'+$scope.coord1.val+'","crdTwo":"'+$scope.coord2.key+'","valTwo":"'+$scope.coord2.val+'"}';
            userResource.checkCoordinates({}, coordinates).$promise.then(
                function (data) {
                    //console.log(data.toSource());
                    if(data.message=="Autorized"){
                        LxDialogService.close($scope.dialogCoordinates);
                        $scope.LinearProgress = helperFunc.toogleStatus($scope.LinearProgress);
                        $scope.sendbutton = helperFunc.toogleStatus($scope.sendbutton);

                        var toSave = { paytype:fields.paytype.key, payacctnum:fields.payacctnum, paycardname:fields.paycardname, payvalid:helperFunc.dateToDB(fields.payvalid), paystatus:"ACTIVE", paycardtype:fields.type };

                        userPaymentResource.save({id: $rootScope.user.id}, toSave).$promise.
                        then(
                            function (data) {
                                if(angular.isDefined(data.message) && data.message=="Unknown SMTP host: smtp.gmail.com"){
                                    $scope.sms5 = $filter('translate')('paymethod.module.sms5');
                                    LxNotificationService.error($scope.sms5);
                                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                    return;
                                }
                                //console.log("Saved!!" + data.toSource());
                                $scope.sms6 = $filter('translate')('paymethod.module.sms6');
                                $scope.sms7 = $filter('translate')('paymethod.module.sms7');
                                LxNotificationService.alert($scope.sms6,
                                    $scope.sms7,
                                    'Ok',
                                    function(answer)
                                    {
                                        $scope.reset();
                                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                        //$location.path("/dashboard");
                                    });
                            },function (data) {
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                console.log("Error!!" + data.toString());
                            }
                        )

                    }else if(data.message=="Unautorized"){
                        //window.alert(" Invalid Data!!! ");
                        $scope.sms8 = $filter('translate')('paymethod.module.sms8');
                        LxNotificationService.error($scope.sms8);
                    }else if(data.message=="Coordinate card inactive"){
                        $scope.sms9 = $filter('translate')('paymethod.module.sms9');
                        LxNotificationService.error($scope.sms9);
                        //window.alert(" You haven't activated your card yet!!! ");
                    }
                }
            );


            /**/
        }
        /********** addpayMethod ********/

        /********** editpayMethod ********/
        $scope.listPayMethod = userPaymentResource.get();

        $scope.dialogPayMethod = "dialogPayMethod";
        $scope.feditpaym = {};

        /***************** TO RESET EDIT FORMS ********************/


        $scope.editReset = function(payid) {
            $scope.editMaster = {
                payid: payid, paytype: "", payacctnum: "", paycardname: "", payvalid: null/*, paystatus: ""*/
            };
            $scope.feditpaym = angular.copy($scope.editMaster);
        };
        /***************** TO RESET FORMS ********************/
        //$scope.reset();

        $scope.openDialogPayMethod = function openDialogPayMethod(ef_paym)
        {
            LxDialogService.open(this.dialogPayMethod);
            //console.log(ef_paym.payid );
            /***************** TO RECALL DATA ON EDIT FORMS ********************/
            $scope.editmaster = {
                payid: ef_paym.payid, paytype: { key: ef_paym.paytype, name: ef_paym.paytype }, payacctnum: ef_paym.payacctnum, paycardname: ef_paym.paycardname, payvalid: helperFunc.dateFromDB(ef_paym.payvalid), type:ef_paym.type
            };
            $scope.feditpaym = angular.copy($scope.editmaster);
            /***************** TO RECALL DATA ON EDIT FORMS ********************/

        }

        $scope.$on('lx-dialog__open-start', function(_event, _dialogId)
        {
            //var self = this;
            if ($scope.dialogPayMethod === _dialogId)
            {

            }
        });

        $scope.updatePayMethod = function updatepayMethod($event, fields) {
            $event.preventDefault();
            var self = this;

            //console.log(fields.payid+ ' / ' + fields);
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

            var toSave = {
                payid: fields.payid, paytype: fields.paytype.key, payacctnum: fields.payacctnum, paycardname: fields.paycardname, payvalid: helperFunc.dateToDB(fields.payvalid), paycardtype:fields.type, paystatus: 'ACTIVE'
            };

            userPaymentResource.update({}, toSave).$promise.
            then(
                function (data) {
                    //console.log("Updated!!" + data);
                    $scope.sms10 = $filter('translate')('paymethod.module.sms10');
                    LxNotificationService.success($scope.sms10);
                    $scope.listPayMethod = userPaymentResource.get();
                    $scope.reset();
                    //$location.path("/dashboard");
                    LxDialogService.close(self.dialogPayMethod);
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                },function (data) {
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    console.log("Error!!" + data.toString());
                }
            );
        }

        /********** editpayMethod ********/

        /********** deletePayMethod ********/

        $scope.deletePayMethod = function deletePayMethod(data) {
            //$event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
            //console.log(data.toSource());
            $scope.sms11 = $filter('translate')('paymethod.module.sms11');
            $scope.sms12 = $filter('translate')('paymethod.module.sms12');
            $scope.sms13 = $filter('translate')('paymethod.module.sms13');
            $scope.sms14 = $filter('translate')('paymethod.module.sms14');
            LxNotificationService.confirm($scope.sms11, $scope.sms12,
                {
                    cancel: $scope.sms13,
                    ok: $scope.sms14
                }, function(answer)
                {
                    if (answer)
                    {
                        userPaymentResource.delete({paymethod: data.payid}).$promise.
                        then(
                            function (data) {
                                //console.log("Erased!!" + data);
                                $scope.sms15 = $filter('translate')('paymethod.module.sms15');
                                LxNotificationService.success($scope.sms15);
                                $scope.listPayMethod = userPaymentResource.get();
                                //$scope.faircrafts = [];
                                //$location.path("/dashboard");
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                            },function (data) {
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                console.log("Error!!" + data.toString());
                            }
                        );
                    }
                    else
                    {
                        $scope.sms16 = $filter('translate')('paymethod.module.sms16');
                        LxNotificationService.error($scope.sms16);
                        this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
                        this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
                    }
                });

        }
        /********** deletePayMethod ********/


    }]);