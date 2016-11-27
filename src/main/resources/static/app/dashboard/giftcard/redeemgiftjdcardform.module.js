/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var redeemgiftcard = angular.module('RedeemGiftJdCard', ['ngRoute', 'ngMessages', 'ui.utils.masks']);

redeemgiftcard.controller('RedeemGiftJdCardController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'helperFunc', 'LxNotificationService', 'giftCardResource', 'userPaymentResource', '$translate', '$filter',
    function AddJdCardController($rootScope, $scope, $http, $location, myJdMenu, helperFunc, LxNotificationService, giftCardResource, userPaymentResource, $translate, $filter) {
        $scope.cssClass = 'redeemgiftcard';
        $scope.icon = '../css/icons/wallet-giftcard.png';

        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;
        $scope.fredeemcard = {};
        /***************** TO RESET FORMS ********************/
        $scope.master = {
            jdcard: "", claimcode: ""
        };
        $scope.reset = function() {
            $scope.fredeemcard = angular.copy($scope.master);
        };
        /***************** TO RESET FORMS ********************/
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

        $scope.redeemGiftCard = function redeemGiftCard($event, fields) {
            $event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
            console.log(fields.toSource());
            var toSave = '{ ' +
                '"jdcard":"'+fields.jdcard.payid+'", ' +
                '"claimcode":"'+fields.claimcode+'" ' +
                '}';

            console.log(toSave.toSource());
            var redeemGiftCard = giftCardResource.redeem({}, toSave);
            redeemGiftCard.$promise.
            then(
                function (data) {
                    console.log("Saved!!" + data);
                    if (data.message == "conexionError"){
                        $scope.sms1 = $filter('translate')('giftcard.redeem.module.sms1');
                        LxNotificationService.error($scope.sms1);
                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                        //self.helperFuncBar();
                        return;
                    }
                    $scope.sms2 = $filter('translate')('giftcard.redeem.module.sms2');
                    $scope.sms3 = $filter('translate')('giftcard.redeem.module.sms3');
                    LxNotificationService.alert($scope.sms2,
                        $scope.sms3,
                        'Ok',
                        function(answer)
                        {
                            $scope.reset();
                            self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                            self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                            $location.path("/dashboard");
                        });

                },function (data) {
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    console.log("Saved!!" + data);
                }
            )
        };

    }]);