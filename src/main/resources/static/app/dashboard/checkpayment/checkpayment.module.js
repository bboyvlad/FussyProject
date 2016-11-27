/**
 * d by bboyvlad on 9/8/16.
 */
'use strict';

var checkpay = angular.module('CheckPay', ['ngRoute', 'ngMessages', 'angularPayments', 'ui.utils.masks']);

checkpay.controller('CheckPayController', ['$rootScope','$scope', '$http', '$location', 'LxDatePickerService', 'myJdMenu', 'helperFunc', 'LxNotificationService', 'LxDialogService', 'bankManageResource', 'userResource', 'userPaymentResource',
    function CheckPayController($rootScope, $scope, $http, $location, LxDatePickerService, myJdMenu, helperFunc, LxNotificationService, LxDialogService, bankManageResource, userResource, userPaymentResource ) {
        $scope.cssClass = 'checkpay';
        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;
        $scope.search = { mysearch:'' };
        $scope.listPaymentsRecieved = bankManageResource.showreceived();

              /********** CheckPay ********/
        $scope.CheckPay = function CheckPay($event, fields) {
            $event.preventDefault();
            var self = this;

                $scope.LinearProgress = helperFunc.toogleStatus($scope.LinearProgress);
                $scope.sendbutton = helperFunc.toogleStatus($scope.sendbutton);

                var toSave = {paymethod: fields.paymethod.payid, bank: fields.bank.bankid, dcreate: helperFunc.dateToDB(fields.dcreate, "YYYY-MM-DD"), relatedref: fields.relatedref, amount: fields.amount };
                bankManageResource.sendpayment({}, toSave).$promise.
                then(
                    function (data) {
                        console.log("Guardado!!" + data.toSource());
                        LxNotificationService.alert('Payment Sent',
                            "Your Payment has been ed successfully,\r\nour customer service will let you know\r\nonce is clear ...",
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
        };
        /********** CheckPay ********/

        /********** editPaymentsRecieved ********/

        $scope.dialogCheckpay = "dialogCheckpay";
        $scope.feditcheckpay = {};

        /***************** TO RESET EDIT FORMS ********************/
/*[{"id":4,"paymethod":65,"bank":2,"relatedref":"9876542","inspaymethod":0,"insbank":"GISBEL L BRANCHI
 A","insacctnum":"4989333873598794","insname":"BANK RIFEL","amount":65.88,"dcreate":1238472000000,"dupdate"
 :1478750400000,"approved":false}]*/

        $scope.editReset = function(id) {
            $scope.editMaster = {
                id: id, paymethod: "", bank: "", relatedref: "", inspaymethod: "", insbank: "", insacctnum: "", insname: "", amount: "", dcreate: "", approved: ""
            };
            $scope.feditcheckpay = angular.copy($scope.editMaster);
        };
        /***************** TO RESET FORMS ********************/
        //$scope.reset();

        $scope.openDialogNotifyPay = function openDialogNotifyPay(ef_cpay)
        {

            console.log(ef_cpay.id );
            /***************** TO RECALL DATA ON EDIT FORMS ********************/
            $scope.editmaster = {
                id: ef_cpay.id, paymethod: ef_cpay.paymethod, bank: ef_cpay.bank, relatedref: ef_cpay.relatedref, inspaymethod: ef_cpay.inspaymethod, insbank: ef_cpay.insbank, insacctnum: ef_cpay.insacctnum, insname: ef_cpay.insname, amount: ef_cpay.amount, dcreate: ef_cpay.dcreate, approved : ef_cpay.approved
            };
            $scope.feditcheckpay = angular.copy($scope.editmaster);
            /***************** TO RECALL DATA ON EDIT FORMS ********************/
            LxDialogService.open(this.dialogCheckpay);

        }

        $scope.$on('lx-dialog__open-start', function(_event, _dialogId)
        {
            //var self = this;
            if ($scope.dialogCheckpay === _dialogId)
            {

            }
        });

        $scope.updatePaymentsRecieved = function updatePaymentsRecieved($event, fields) {
            $event.preventDefault();
            var self = this;

            console.log(fields.id+ ' / ' + fields);
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
            var toSave = {
                payment: fields.id , approved: fields.approved
            }

            bankManageResource.checkpayment({}, toSave).$promise.
            then(
                function (data) {
                    console.log("Actualizado!!" + data);
                    LxNotificationService.success('Actualización realizada!!!');
                    $scope.listPaymentsRecieved = bankManageResource.showreceived();
                    $scope.reset();
                    //$location.path("/dashboard");
                    LxDialogService.close(self.dialogCheckpay);
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                },function (data) {
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    console.log("Error!!" + data);
                }
            );
        }

        /********** editPaymentsRecieved ********/

    }]);