/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var addjdcard = angular.module('AddJdCard', ['ngRoute', 'ngMessages', 'ui.utils.masks', 'angularPayments']);

addjdcard.controller('AddJdCardController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'helperFunc', 'LxNotificationService', 'jdCardResource', 'userPaymentResource', '$translate', '$filter',
    function AddJdCardController($rootScope, $scope, $http, $location, myJdMenu, helperFunc, LxNotificationService, jdCardResource, userPaymentResource, $translate, $filter) {
        $scope.cssClass = 'addjdcard';
        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;
        $scope.icon = '../css/icons/two-credit-cards.png';



        /***************** TO RESET FORMS ********************/
        $scope.master = {
            idpaymethod: "", pay_ccsec: "", cardname: "", amount: ""
        };
        $scope.reset = function() {
            $scope.fjdcard = angular.copy($scope.master);
        };
        /***************** TO RESET FORMS ********************/
        $scope.reset();

        $scope.regex = "[A-Z\\s]+";

        $scope.paymethod = [];
        $scope.getPayments = function getPayments(id) {
            var self = this;

            userPaymentResource.get({id: id}).$promise.
                then(
                    function (data) {
                        /*$scope.paymethod = response.data;*/
                        angular.forEach(data, function (key) {
                            //alert(key.pay_type +':'+val);
                            if(key.paytype=="JDCARD"){
                                //self.jdcardOpts.push(key);
                            }else{
                                self.paymethod.push(key);
                            }
                        });
                    }
            )
        };

        $scope.getPayments($rootScope.user.id);

        $scope.addjdCard = function addjdCard($event, fields) {
            $event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

            var toSave = '{"idpaymethod":"'+fields.idpaymethod.payid+'","pay_ccsec":"'+fields.pay_ccsec+'","cardname":"'+fields.cardname+'","amount":"'+fields.amount+'"}';

            var buyJdCard = jdCardResource.save({id: $rootScope.user.id}, toSave);
            buyJdCard.$promise.
            then(
                function (data) {
                    console.log("Saved!!" + data.toSource());
                    if (data.message == "conexionError"){
                        $scope.sms1 = $filter('translate')('jdcard.add.module.sms1');
                        LxNotificationService.error($scope.sms1);
                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                        //self.helperFuncBar();
                        return;
                    }
                    if (data.message == "Invalid Card"){
                        $scope.sms2 = $filter('translate')('jdcard.add.module.sms2');
                        LxNotificationService.error($scope.sms2);
                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                        //self.helperFuncBar();
                        return;
                    }

                    $scope.sms3 = $filter('translate')('jdcard.add.module.sms3');
                    $scope.sms4 = $filter('translate')('jdcard.add.module.sms4');
                    LxNotificationService.alert("J&D Card "+$scope.sms3,
                        $scope.sms4+" J&D Card!",
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