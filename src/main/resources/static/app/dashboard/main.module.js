/**
 * Created by bboyvlad on 9/7/16.
 */
'use strict';

var dashboard = angular.module('dashboard', ['ngRoute', 'chart.js']);

dashboard.controller('DashController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'userResource', '$filter', 'helperFunc',
    function DashController($rootScope, $scope, $http, $location, myJdMenu, userResource, $filter, helperFunc) {
        $scope.cssClass = 'dashboard';

        /*$rootScope.mainPieChart = {labels:[], data:[], payavailable: 0, paylocked:0, paybalance: 0 };

        $scope.calculateBalance = function calculateBalance() {
            //console.log("payments: "+ $rootScope.userDetail.toSource());
            var detailUser=userResource.detailUser();
            detailUser.$promise.then( function (data) {
                //console.log("data userDetails"+data);
                $rootScope.userDetail = data;
                $scope.items = $filter('orderBy')($rootScope.userDetail.payments, "payid");
                var payments = $scope.items;
                //console.log("data payments"+payments.toSource());
                angular.forEach(payments, function (value, key) {
                    console.log(value.payavailable);
                    $rootScope.mainPieChart.payavailable +=value.payavailable;
                    $rootScope.mainPieChart.paylocked +=value.paylocked;
                    $rootScope.mainPieChart.paybalance +=value.paybalance;

                })
                $rootScope.mainPieChart.labels = ["Available Balance", "Balance Blocked"];
                $rootScope.mainPieChart.data = [$rootScope.mainPieChart.payavailable, $rootScope.mainPieChart.paylocked];
                /!*console.log("TOTAL" + $rootScope.mainPieChart.payavailable);
                console.log($rootScope.datePicker.toSource());*!/
            });

        };*/
        helperFunc.jdCardBalance(null, true);
        //$rootScope.$emit('rootScope:emit', 'Emit!');

        /*"paybalance":0.0,"paylocked":0.0,"payavailable":0.0*/



    }]);