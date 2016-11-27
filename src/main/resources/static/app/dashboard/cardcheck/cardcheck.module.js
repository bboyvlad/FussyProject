/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var cardcheck = angular.module('CardCheck', ['ngRoute', 'ngMessages', 'ui.utils.masks', 'angularPayments']);

cardcheck.controller('CardCheckController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'helperFunc', 'LxNotificationService', 'LxDialogService',  'cardValidateResource',
    function AddJdCardController($rootScope, $scope, $http, $location, myJdMenu, helperFunc, LxNotificationService, LxDialogService, cardValidateResource) {
        $scope.cssClass = 'cardcheck';
        $scope.icon = '../css/icons/cardcheck.png';
        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;
        $scope.cardCheckImg = '../css/img/finance-graphic.png';
        $scope.fcardcheck = {};
        $scope.dialogValidate = 'dialogValidate';

        /***************** TO RESET FORMS ********************/
        $scope.master = {
            switches: "false", jdcardcode: ""
        };
            $scope.reset = function() {
            $scope.fcardcheck = angular.copy($scope.master);
        };
        $scope.reset();
        /***************** TO RESET FORMS ********************/

        $scope.CheckCard = function CheckCard($event, validate)
        {
            $event.preventDefault();
            //console.log(validate.toSource() );
            if(validate.switches=="false"){
                cardValidateResource.cardStatus({fuelCardCode: validate.jdcardcode}).
                $promise.then(
                    function (data) {
                        $scope.cardBalance = false;
                        $scope.cardStatus = data;
                    }
                );
            }else if(validate.switches=="true"){
                cardValidateResource.cardBalance({fuelCardCode: validate.jdcardcode}).
                $promise.then(
                    function (data) {
                        $scope.cardStatus = false;
                        $scope.cardBalance = data;
                    }
                );
            }
            LxDialogService.open(this.dialogValidate);
        }

        $scope.$on('lx-dialog__open-start', function(_event, _dialogId)
        {
            //var self = this;
            if ($scope.dialogCaptain === _dialogId)
            {

            }
        });


    }]);