/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var addgiftcard = angular.module('AddGiftJdCard', ['ngRoute', 'ngMessages', 'ui.utils.masks', 'angularPayments']);

addgiftcard.controller('AddGiftJdCardController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'helperFunc', 'LxNotificationService', 'giftCardResource', 'userPaymentResource',
    function AddJdCardController($rootScope, $scope, $http, $location, myJdMenu, helperFunc, LxNotificationService, giftCardResource, userPaymentResource) {
        $scope.cssClass = 'addgiftcard';
        $scope.icon = '../css/icons/wallet-giftcard.png';

        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;

        $scope.fgiftcard = {};
        $scope.regex = "[A-Z\\s]+";

        $scope.paymethod = [];
        $scope.jdcardOpts = [];

        /***************** TO RESET FORMS ********************/
        $scope.master = {
            switches: "false", idpaymethod: "", paycardsec: "", tdcamount: "", jdcardmethod: "", jdcamount: "", recipient_email: "", recipient_name: "", recipient_message: "" };
            $scope.reset = function() {
            $scope.fgiftcard = angular.copy($scope.master);
        };
        $scope.reset();
        /***************** TO RESET FORMS ********************/

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
                            }else{
                                self.paymethod.push(key);
                            }
                        });
                    }
            )
        };

        //alert($rootScope.user.id);
        $scope.getPayments($rootScope.userDetail.id);

        $scope.addGiftCard = function addGiftCard($event, fields) {
            $event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
            console.log(fields.toSource());
            if(fields.switches=="false"){

                var toSave = '{ ' +
                '"recipient_email":"'+fields.recipient_email+'", ' +
                '"recipient_message":"'+fields.recipient_message+'", ' +
                '"recipient_name":"'+fields.recipient_name+'", ' +
                '"paymethod":"'+ fields.idpaymethod.payid +'", ' +
                '"amount":'+fields.tdcamount +', ' +
                '"paycardsec":"'+fields.paycardsec +'" ' +
                '}';
            }else{
                var toSave = '{ ' +
                '"recipient_email":"'+fields.recipient_email+'", ' +
                '"recipient_message":"'+fields.recipient_message+'", ' +
                '"recipient_name":"'+fields.recipient_name+'", ' +
                '"paymethod":"'+ fields.jdcardmethod.payid+'", ' +
                '"amount":'+fields.jdcamount+
                '}';
            }

            console.log(toSave.toSource());
            var buyGiftCard = giftCardResource.save({}, toSave);
            buyGiftCard.$promise.
            then(
                function (data) {
                    console.log("Saved!!" + data);
                    if (data.message == "conexionError"){
                        $scope.sms1 = $filter('translate')('giftcard.add.module.sms1');
                        LxNotificationService.error($scope.sms1);
                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                        //self.helperFuncBar();
                        return;
                    }else if(data.message == "Saldo insuficiente"){
                        $scope.sms2 = $filter('translate')('giftcard.add.module.sms2');
                        LxNotificationService.error($scope.sms2);
                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                        //self.helperFuncBar();
                        return;
                    }

                    $scope.sms3 = $filter('translate')('giftcard.add.module.sms3');
                    $scope.sms4 = $filter('translate')('giftcard.add.module.sms4');
                    LxNotificationService.alert($scope.sms3,
                        $scope.sms4,
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
        }


    }]);