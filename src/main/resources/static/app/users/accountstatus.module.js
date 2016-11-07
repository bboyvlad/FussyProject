/**
 * Created by bboyvlad on 9/7/16.
 */
'use strict';

var accountstatus = angular.module('accountStatus', ['ngRoute']);

    accountstatus.controller('AccountStatusController', ['$rootScope', '$scope', '$http', '$location',
        function smsController($rootScope, $scope, $http, $location) {

        $scope.resendToken = function resendEmail() {
            alert("Confirmation Email has been Resend!");
        }
}]);