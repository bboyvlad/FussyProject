/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var refilljdcard = angular.module('reFillJdCard', ['ngRoute', 'ui.utils.masks', 'angularPayments']);

refilljdcard.controller('RefillJdCardController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'LxNotificationService', 'helperFunc','jdCardResource', 'userPaymentResource', 'cardPaymentResource', '$translate', '$filter',
    function AddJdCardController($rootScope, $scope, $http, $location, myJdMenu, LxNotificationService, helperFunc, jdCardResource, userPaymentResource, cardPaymentResource, $translate, $filter ) {
        $scope.cssClass = 'refilljdcard';
        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;
        $scope.icon = '../css/icons/two-credit-cards.png';

        $scope.frfilljdcard = {};
        /***************** TO RESET FORMS ********************/
        $scope.master = {
            jdcardSelected:"", idpaymethod:"", pay_ccsec:"", amount:"",
        };
        $scope.reset = function() {
            $scope.frfilljdcard = angular.copy($scope.master);
        };
        /***************** TO RESET FORMS ********************/
        //$scope.reset();

        $scope.paymethod = [];
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
                        }else{
                            self.paymethod.push(key);
                        }
                    });
                }
            )
        };

        $scope.getPayments($rootScope.user.id);

        $scope.refilljdCard = function refilljdCard($event, fields) {
            $event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

            //console.log(fields);
            var toSave = '{"idpaymethod":"'+fields.idpaymethod.payid+'","pay_ccsec":"'+fields.pay_ccsec+'","amount":"'+fields.amount+'"}';

            cardPaymentResource.refill({card_id: fields.jdcardSelected.payid}, toSave).$promise.
            then(
                function (data) {
                    console.log("Saved!!" + data);
                    if (data.message == "conexionError"){
                        $scope.sms1 = $filter('translate')('jdcard.refill.module.sms1');
                        LxNotificationService.error($scope.sms1);
                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                        //self.helperFuncBar();
                        return;
                    }

                    $scope.sms2 = $filter('translate')('jdcard.refill.module.sms2');
                    $scope.sms3 = $filter('translate')('jdcard.refill.module.sms3', fields);
                    LxNotificationService.alert("J&D Card "+$scope.sms2,
                        $scope.sms3+" J&D Card",
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
                    console.log("error!!" + data);
                }
            )
        }


    }]);