/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var notifypay = angular.module('NotifyPay', ['ngRoute', 'ngMessages', 'angularPayments', 'ui.utils.masks']);

notifypay.controller('NotifyPayController', ['$rootScope','$scope', '$http', '$location', 'LxDatePickerService', 'myJdMenu', 'helperFunc', 'LxNotificationService', 'LxDialogService', 'bankManageResource', 'userResource', 'userPaymentResource',
    function NotifyPayController($rootScope, $scope, $http, $location, LxDatePickerService, myJdMenu, helperFunc, LxNotificationService, LxDialogService, bankManageResource, userResource, userPaymentResource ) {
        $scope.cssClass = 'notifypay';
        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;
        $scope.search = { mysearch:'' };
        $scope.regex = "[0-9]+";

        $scope.jdcardOpts = [];
        $scope.getPayments = function getPayments() {
            var self = this;

            userPaymentResource.get().$promise.
            then(
                function (data) {
                    /*$scope.paymethod = response.data;*/
                    angular.forEach(data, function (key) {
                        //alert(key.paytype +':'+val);
                        if(key.paytype=="JDCARD"){
                            $scope.jdcardOpts.push(key);
                        }/*else{
                         self.paymethod.push(key);
                         }*/
                    });
                }
            )
        };
        $scope.getPayments();

        $scope.bankOpts = bankManageResource.query();

        /****************** DATEPICKER *********************/
        $scope.datePickerId = 'dcreate';
        $scope.faddnotifypay = {
            datePicker: { dateFormated: null, locale: 'es', format: 'YYYY-MM-DD' }
        };
        $scope.icon = '../css/icons/coin.png';

        $scope.datePickerCallback = function datePickerCallback(_newdate, action="add")
        {
            if(action=="add"){
                $scope.faddnotifypay.datePicker.dateFormated = moment(_newdate).locale($scope.faddnotifypay.datePicker.locale).format($scope.faddnotifypay.datePicker.format);
            }else if(action=="update"){
                $scope.feditnotifypay.datePicker.dateFormated = moment(_newdate).locale($scope.feditnotifypay.datePicker.locale).format($scope.feditnotifypay.datePicker.format);
            }
            LxDatePickerService.close($scope.datePickerId);
        };
        /****************** DATEPICKER *********************/


        /***************** COORDINATE CARD ********************/
        $scope.userDetail = userResource.detailUser().$promise
        .then(
            function (data) {
                if(data.coordinates==null || data.coordinates.id==""){
                    //console.log(data.coordinates.id + " / " + data.coordinates.active);
                    LxNotificationService.confirm('Coordinte Card', 'To add an bank account you must generate a coordinate card.',
                        {
                            cancel: 'Cancel',
                            ok: 'Create'
                        }, function(answer)
                        {
                            if (answer)
                            {
                                window.open('http://jdoilfield.net:8080/users/setcoordinates', '_blank');

                                LxNotificationService.success('Card created successfully !!!.. you\'ll find in your email the instrucction for its activation ');
                            }
                            else
                            {
                                LxNotificationService.error('The operation has been canceled');
                            }
                        });
                }else if(data.coordinates.active==false){
                    LxNotificationService.info('You already have a coordinate card, but you need to complete the activation process... check your email and follow the instructions to activated or create a new one from the user menu!...');
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
            bankname: "", bankacctnum: "", dcreate: null, datePicker: { dateFormated: new Date(), locale: 'es', format: 'YYYY-MM-DD' }, notes: "", enabled: ""
        };
        $scope.reset = function() {
        };
        /***************** TO RESET FORMS ********************/
        //$scope.reset();

              /********** addNotifyPay ********/
        $scope.NotifyPayCreate = function NotifyPayCreate($event, fields) {
            $event.preventDefault();
            var self = this;

            var coordinates = '{"crdOne":"'+$scope.coord1.key+'","valOne":"'+$scope.coord1.val+'","crdTwo":"'+$scope.coord2.key+'","valTwo":"'+$scope.coord2.val+'"}';
            userResource.checkCoordinates({}, coordinates).$promise.then(
                function (data) {
                    console.log(data.toSource());
                    if(data.message=="Autorized"){
                        LxDialogService.close($scope.dialogCoordinates);
                        $scope.LinearProgress = helperFunc.toogleStatus($scope.LinearProgress);
                        $scope.sendbutton = helperFunc.toogleStatus($scope.sendbutton);

                        var toSave = {paymethod: fields.paymethod.payid, bank: fields.bank.bankid, insname: fields.bank.bankname, dcreate: helperFunc.dateToDB(fields.dcreate, "YYYY-MM-DD"), relatedref: fields.relatedref, amount: fields.amount };
                        bankManageResource.sendpayment({}, toSave).$promise.
                        then(
                            function (data) {
                                console.log("Guardado!!" + data.toSource());
                                LxNotificationService.alert('Payment Sent',
                                    "Your Payment has been added successfully,\r\nour customer service will let you know\r\nonce is clear ...",
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
                                console.log("Error!!" + data);
                            }
                        )

                    }else if(data.message=="Unautorized"){
                        window.alert(" Invalid Data!!! ");
                    }else if(data.message=="Coordinate card inactive"){
                        window.alert(" Please activate your coordinates card first!!! ");
                    }
                }
            );


            /**/
        }
        /********** addNotifyPay ********/

        /********** editNotifyPay ********/
        /*$scope.listNotifyPay = bankManageResource.query();

        $scope.dialogNotifyPay = "dialogNotifyPay";
        $scope.feditnotifypay = {};

        /!***************** TO RESET EDIT FORMS ********************!/


        $scope.editReset = function(id) {
            $scope.editMaster = {
                id: bankid, bankname: "", bankacctnum: "", dcreate: new Date(), datePicker: { dateFormated: new Date(), locale: 'es', format: 'YYYY-MM-DD' }, notes: "", enabled: ""
            };
            $scope.feditnotifypay = angular.copy($scope.editMaster);
        };
        /!***************** TO RESET FORMS ********************!/
        //$scope.reset();

        $scope.openDialogNotifyPay = function openDialogNotifyPay(ef_notifypay)
        {
            LxDialogService.open(this.dialogNotifyPay);
            //console.log(ef_notifypay.payid );
            /!***************** TO RECALL DATA ON EDIT FORMS ********************!/
            $scope.editmaster = {
                id: ef_notifypay.bankid, bankname: ef_notifypay.bankname, bankacctnum: ef_notifypay.bankacctnum, dcreate: ef_notifypay.dcreate,  datePicker: { dateFormated: helperFunc.dateFromDB(ef_notifypay.dcreate, "YYYY-MM-DD"), locale: 'es', format: 'YYYY-MM-DD' } , notes: ef_notifypay.notes, enabled: ef_notifypay.enabled
            };
            $scope.feditnotifypay = angular.copy($scope.editmaster);
            /!***************** TO RECALL DATA ON EDIT FORMS ********************!/

        }

        $scope.$on('lx-dialog__open-start', function(_event, _dialogId)
        {
            //var self = this;
            if ($scope.dialogNotifyPay === _dialogId)
            {

            }
        });

        $scope.updateNotifyPay = function updateNotifyPay($event, fields) {
            $event.preventDefault();
            var self = this;

            console.log(fields.id+ ' / ' + fields.toSource());
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

            var toSave = {
                id: fields.bankid, bankname: fields.bankname, bankacctnum: fields.bankacctnum, dcreate: fields.dcreate,  datePicker: { dateFormated: helperFunc.dateToDB(fields.dcreate, "YYYY-MM-DD"), locale: 'es', format: 'YYYY-MM-DD' } , notes: fields.notes, enabled: fields.enabled
            };

            bankManageResource.update({id: fields.id}, toSave).$promise.
            then(
                function (data) {
                    console.log("Updated!!" + data);
                    LxNotificationService.success('Update successful!!!');
                    $scope.listNotifyPay = bankManageResource.query();
                    $scope.reset();
                    //$location.path("/dashboard");
                    LxDialogService.close(self.dialogNotifyPay);
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                },function (data) {
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    console.log("Error!!" + data);
                }
            );
        }*/

        /********** editNotifyPay ********/

        /********** deleteNotifyPay ********/

        /*$scope.deleteNotifyPay = function deleteNotifyPay(data) {
            //$event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
            console.log(data.toString());
            LxNotificationService.confirm('Delete bank account', 'Please confirm to delete this bank account .',
                {
                    cancel: 'Cancel',
                    ok: 'Delete'
                }, function(answer)
                {
                    if (answer)
                    {
                        bankManageResource.delete({id: data.bankid}).$promise.
                        then(
                            function (data) {
                                console.log("Deleted!!" + data);
                                LxNotificationService.success('Bank account, Deleted successfully!!!');
                                $scope.listNotifyPay = bankManageResource.query();
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
                        LxNotificationService.error('Disagree');
                        this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
                        this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
                    }
                });

        }*/
        /********** deleteNotifyPay ********/

    }]);