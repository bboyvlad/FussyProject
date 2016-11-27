/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var balancedetails = angular.module('BalanceDetails', ['ngRoute', 'ngMessages', 'ui.utils.masks', 'ngAnimate']);

balancedetails.controller('BalanceDetailsController', ['$rootScope','$scope', '$filter', '$http', '$location', 'myJdMenu', 'helperFunc', 'LxNotificationService', 'userPaymentResource', 'LxDatePickerService',
    function AddJdCardController($rootScope, $scope, $filter, $http, $location, myJdMenu, helperFunc, LxNotificationService, userPaymentResource, LxDatePickerService) {
        $scope.cssClass = 'balancedetails';
        $scope.icon = '../css/icons/chart-areaspline.png';

        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;
        $scope.search = { mysearch:'' };
        /***************** TO RESET FORMS ********************/
        /*$scope.master = {
            jdcard: "", dateFrom: null, dateFromPicker: { dateFormated: new Date(), locale: $rootScope.datePicker.locale, format: $rootScope.datePicker.format }, dateToPicker: { dateFormated: new Date(), locale: $rootScope.datePicker.locale, format: $rootScope.datePicker.format }
        };*/
        $scope.listTransactions = "";
        $scope.master = {
            jdcard: "", dateFrom: null, dateFromPicker: { dateFormated: new Date(), locale: 'es', format: 'YYYY-MM-DD' }, dateToPicker: { dateFormated: new Date(), locale: 'es', format: 'YYYY-MM-DD' }
        };
        $scope.reset = function() {
            $scope.fredeemcard = angular.copy($scope.master);
            $scope.listTransactions = "";
        };
        /***************** TO RESET FORMS ********************/

        /****************** DATEPICKER *********************/
        $scope.dateFromPickerId = 'dateFrom';
        $scope.dateToPickerId = 'dateTo';
        $scope.balance = {
            dateFromPicker: { dateFormated: null, locale: 'es', format: 'YYYY-MM-DD' },
            dateToPicker: { dateFormated: null, locale: 'es', format: 'YYYY-MM-DD' }
        };

        $scope.datePickerCallback = function datePickerCallback(_newdate, option)
        {
            if(option=='from'){
                $scope.balance.dateFromPicker.dateFormated = moment(_newdate).locale('es').format('YYYY-MM-DD');
                LxDatePickerService.close($scope.dateFromPickerId);
            }else if(option=='to'){
                $scope.balance.dateToPicker.dateFormated = moment(_newdate).locale('es').format('YYYY-MM-DD');
                LxDatePickerService.close($scope.dateToPickerId);
            }
        };
        /****************** DATEPICKER *********************/


        //$scope.reset();

        //$scope.paymethod = [];
        $scope.jdcardOpts = [];
        $scope.getPayments = function getPayments(id) {
            var self = this;

            userPaymentResource.get({id: id}).$promise.
                then(
                    function (data) {
                        /*$scope.paymethod = response.data;*/
                        angular.forEach(data, function (key) {
                            //alert(key.paytype +':'+val);
                            if(key.paytype=="JDCARD"){
                                self.jdcardOpts.push(key);
                            }/*else{
                                self.paymethod.push(key);
                            }*/
                        });
                    }
            )
        };

        //alert($rootScope.user.id);
        $scope.getPayments($rootScope.user.id);

        $scope.GetBalance = function GetBalance($event, fields) {
            $event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
            //console.log(fields.toSource());
            var toSave = { paymethod: fields.jdcard.payid, fromdate: fields.dateFrom, todate: fields.dateTo };

            console.log(toSave.toSource());
            var GetBalance = userPaymentResource.datetransactionpay({}, toSave);
            GetBalance.$promise.
            then(
                function (data) {
                    console.log("Transactions: " + data.toSource());
                    $scope.items = $filter('orderBy')(data, "tranid");
                    $scope.listTransactions = $scope.items;
                    console.log("Ordered Transactions: " + $scope.listTransactions.toSource());
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);

                },function (data) {
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    console.log("Error!!" + data.toSource());
                }
            )
        };

    }]);